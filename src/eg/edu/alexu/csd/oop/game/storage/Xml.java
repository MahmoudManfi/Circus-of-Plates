package eg.edu.alexu.csd.oop.game.storage;

import eg.edu.alexu.csd.oop.game.DynamicLinkage.DynamicLinkage;
import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.Shapes.*;
import eg.edu.alexu.csd.oop.game.Shapes.Shape;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.WorldDesign.CircusWorld;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

 class Xml {
    private static String fileSeparator = System.getProperty("file.separator");
    private static final Logger logger = Logger.getLogger(Xml.class);
    private static final List<Class<? extends Shape>> supportedShapes = DynamicLinkage.getInstance().loadJars();
    private static final GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();
    private static void saveBar(Element element, Bar bar)
    {
        Map<String, String> barValues = bar.getBarValues();
        for (Map.Entry<String, String> entry : barValues.entrySet())
            element.setAttribute(entry.getKey(), entry.getValue());
    }
    private static String colorToHex(Color color)
    {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }
    private static Color hexToColor(String colorStr)
    {
        return Color.decode(colorStr);
    }
    private static void saveShape(Element element, Shape shape) {
        if(!shape.getClass().getSimpleName().equals("Bar")) {
            element.setAttribute("x", String.valueOf(shape.getX()));
            element.setAttribute("y", String.valueOf(shape.getY()));
            element.setAttribute("color", colorToHex(shape.getColor()));
        } else saveBar(element,(Bar)shape);
    }

    private static Stack<Shape> reverseStack(Stack<Shape> stack) {
        Stack<Shape> reversedStack = new Stack<>();
        while (!stack.empty()) {
            reversedStack.push(stack.peek());
            stack.pop();
        }
        return reversedStack;
    }
    private static String loadNodeValue(NamedNodeMap nodeMap,String key)
    {
        Node node = nodeMap.getNamedItem(key);
        if(node == null)
        {
            logger.error("Failed to load XML!!");
            logger.error("Failed to find " + key + " in XML!");
            throw new RuntimeException("");
        }
        return node.getNodeValue();
    }
    private static Shape loadBarNode(Node node)
    {
        NamedNodeMap nodeMap = node.getAttributes();
        int xCoordinates = Integer.parseInt(loadNodeValue(nodeMap,"x"));
        int yCoordinates = Integer.parseInt(loadNodeValue(nodeMap,"y"));
        int width = Integer.parseInt(loadNodeValue(nodeMap,"width"));
        int height = Integer.parseInt(loadNodeValue(nodeMap,"height"));
        int objectsLimit = Integer.parseInt(loadNodeValue(nodeMap,"objectsLimit"));
        int direction = Integer.parseInt(loadNodeValue(nodeMap,"direction"));
        return new Bar(xCoordinates,yCoordinates,width,height,Color.BLACK,objectsLimit,direction);
    }
    private static Shape loadShapeNode(Node node)
    {
        NamedNodeMap nodeMap = node.getAttributes();
        String className = node.getNodeName();
        if(className.equals("Bar"))
            return loadBarNode(node);
        int xCoordinates = Integer.parseInt(loadNodeValue(nodeMap,"x"));
        int yCoordinates = Integer.parseInt(loadNodeValue(nodeMap,"y"));
        Color color = hexToColor(loadNodeValue(nodeMap,"color"));
        Class<? extends Shape> shapeClass = null;
        for(Class<? extends Shape> possibleClass : supportedShapes)
        {
            if(possibleClass.getSimpleName().equals(className))
            {
                shapeClass = possibleClass;
                break;
            }
        }
        if(shapeClass == null)
        {
            logger.error("Invalid shapes are in XML file");
            throw new RuntimeException();
        }
        Shape currentShape = gameObjectFactory.createObject(shapeClass,color);
        currentShape.setX(xCoordinates);
        currentShape.setY(yCoordinates);
        return currentShape;
    }
    /**
     * @param world the current game state that we want to save
     * @param name  the name chosen by the user to save his game
     */
    public static void saveData(World world, String name) throws ParserConfigurationException, TransformerConfigurationException,RuntimeException {
        if(!checkValidName(name)){
            logger.fatal("File name is too long or contains forbidden character ");
            throw new RuntimeException();
        }
        String xmlStoragePlace = Game.databaseDirectory + fileSeparator + name + ".xml";
        FileManager.createFile(xmlStoragePlace);


        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element root = doc.createElement(name);
        /**1-General*/
        Element general = doc.createElement("general");
        general.setAttribute("difficulty", ((CircusWorld)world).getDifficulty().getClass().getSimpleName());  // First tag
        general.setAttribute("status", world.getStatus());
        general.setAttribute("playTime", String.valueOf(((CircusWorld) world).getPlayTime()));
        general.setAttribute("playScore", String.valueOf(((CircusWorld) world).getPlayScore()));

        /**2-Movable Objects */
        Element movable = doc.createElement("movable");
        List<GameObject> shapes = world.getMovableObjects();
        for (GameObject gameObjects : shapes) {
            Shape ourShape = (Shape) gameObjects;
            Element shapeName = doc.createElement(ourShape.getClass().getSimpleName());
            saveShape(shapeName, ourShape);
            movable.appendChild(shapeName); //Second
        }
        IClown clown = (IClown) world.getControlableObjects().get(0);
        Stack<Shape> leftStack = reverseStack((Stack<Shape>) clown.getStackLeft().clone()); // we make a shallow copy in order not the edit the original data
        Stack<Shape> rightStack = reverseStack((Stack<Shape>) clown.getStackRight().clone());
        /**3-Clown */
        Element clownRoot = doc.createElement("clown");
        clownRoot.setAttribute("path", "/" + "clown" + Game.clown + ".png");
        clownRoot.setAttribute("x",String.valueOf(clown.getX()));
        clownRoot.setAttribute("y",String.valueOf(clown.getY()));
        /**3.1 save left stack */
        Element leftStackElements = doc.createElement("leftStack");
        while (leftStack.size() > 0) {
            Shape currentShape = leftStack.peek();
            leftStack.pop();
            Element shapeElement = doc.createElement(currentShape.getClass().getSimpleName());
            saveShape(shapeElement, currentShape);
            leftStackElements.appendChild(shapeElement);
        }
        /**3.2 save left stack */
        Element rightStackElements = doc.createElement("rightStack");
        while (rightStack.size() > 0) {
            Shape currentShape = rightStack.peek();
            rightStack.pop();
            Element shapeElement = doc.createElement(currentShape.getClass().getSimpleName());
            saveShape(shapeElement, currentShape);
            rightStackElements.appendChild(shapeElement);
        }
        clownRoot.appendChild(leftStackElements);
        clownRoot.appendChild(rightStackElements);
        /** end of clown*/
        /**4-Bars */
        Element barsElement = doc.createElement("bars");
        List<Bar> bars = ((CircusWorld) world).getGameBars();
        for (Bar bar : bars) {
            Element currentBar = doc.createElement("Bar");
            saveShape(currentBar,bar);
            barsElement.appendChild(currentBar);
            Element currentBarShelf = doc.createElement("shelfObjects");
            List<Shape> shelfObjects = bar.getShelfObjects();
            for (Shape shape : shelfObjects) {
                Element shapeElement = doc.createElement(shape.getClass().getSimpleName());
                saveShape(shapeElement, shape);
                currentBarShelf.appendChild(shapeElement);
            }
            currentBar.appendChild(currentBarShelf);
        }
        /**-- end of bars*/

        /**
         * Do not edit the following lines
         */
        root.appendChild(general);
        root.appendChild(movable);
        root.appendChild(clownRoot);
        root.appendChild(barsElement);
        doc.appendChild(root);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File(Game.databaseDirectory + fileSeparator + name + ".xml"));
        try {
            transformer.transform(source, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
            logger.fatal("Error occurred during saving xml file!!");
        }

    }

    public static World loadData(String name) throws ParserConfigurationException, IOException, SAXException {
        String xmlStoragePlace = Game.databaseDirectory + fileSeparator + name + ".xml";
        File fileToLoad = new File(xmlStoragePlace);
        if(!fileToLoad.exists())
        {
            logger.fatal("File not found!!");
            throw new FileNotFoundException("");
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(fileToLoad);
        List<GameObject> constantObjects = new LinkedList<>();
        List<GameObject> movableObjects = new LinkedList<>();
        List<GameObject> controllableObject = new LinkedList<>();
        constantObjects.add(new Background());
        ShapesPool shapesPool = new ShapesPool();
        /** Load general */
        NodeList generalInfo = document.getElementsByTagName("general");
        NamedNodeMap generalInfoAttributes = generalInfo.item(0).getAttributes();
        String difficulty = loadNodeValue(generalInfoAttributes,"difficulty");
        String timePlayed = loadNodeValue(generalInfoAttributes,"playTime");
        String score = loadNodeValue(generalInfoAttributes,"playScore");
        /**-- finish load general */
        /**-- load movable */
        Node movableInfo = document.getElementsByTagName("movable").item(0);
        NodeList movableObjectsInfo = movableInfo.getChildNodes();
        for(int i = 0;i < movableObjectsInfo.getLength();i++)
            movableObjects.add(loadShapeNode(movableObjectsInfo.item(i)));
        /**--finish load movable*/
        /**-- load clown */
        Node clownInfo =  document.getElementsByTagName("clown").item(0);
        String clownPath = loadNodeValue(clownInfo.getAttributes(),"path");
        int clownXCoordinates = Integer.parseInt(loadNodeValue(clownInfo.getAttributes(),"x"));
        int clownYCoordinates = Integer.parseInt(loadNodeValue(clownInfo.getAttributes(),"y"));
        NodeList leftClownStack = clownInfo.getFirstChild().getChildNodes();
        NodeList rightClownStack = clownInfo.getLastChild().getChildNodes();
        Stack<Shape> leftStack = new Stack<>();
        Stack<Shape> rightStack = new Stack<>();
        for(int i = 0;i < leftClownStack.getLength();i++)
        {
            leftStack.push(loadShapeNode(leftClownStack.item(i)));
            if(i > 0)
                constantObjects.add(leftStack.peek());
        }
        for(int i = 0;i < rightClownStack.getLength();i++)
        {
            rightStack.push(loadShapeNode(rightClownStack.item(i)));
            if(i > 0)
                constantObjects.add(rightStack.peek());
        }
        IClown clown = new Clown(clownXCoordinates,clownYCoordinates,clownPath,Color.BLACK,leftStack,rightStack);
        controllableObject.add(clown);
        /**-- finish load clown */
        /**-- load bars :(( */
        NodeList barsInfo = document.getElementsByTagName("bars").item(0).getChildNodes();
        List<Bar> bars = new LinkedList<>();
        for(int i = 0;i < barsInfo.getLength();i++)
        {
            Shape currentBar = loadShapeNode(barsInfo.item(i));
            bars.add((Bar)currentBar);
            bars.get(i).initLists(constantObjects,movableObjects,shapesPool,supportedShapes);
            NodeList barShelfObjects = barsInfo.item(i).getFirstChild().getChildNodes();
            for(int j = 0;j < barShelfObjects.getLength();j++)
            {
                Shape currentShape = loadShapeNode(barShelfObjects.item(j));
                bars.get(i).addGameObject(currentShape);
            }
            constantObjects.add(currentBar);
        }
        /** -- finish load bars :)) */
        CircusWorld world = new CircusWorld(Game.WIDTH,Game.HEIGHT,(new DifficultyFactory(difficulty)).getDifficulty(),
                constantObjects,movableObjects,controllableObject,bars,shapesPool,clown);

        world.setPlayScore(Integer.parseInt(score));
        world.setPlayTime(Long.parseLong(timePlayed));
        return world ;
//        return new CircusWorld(Game.WIDTH,Game.HEIGHT,(new DifficultyFactory(difficulty)).getDifficulty(),
//                constantObjects,movableObjects,controllableObject,bars,shapesPool,clown);
//
    }

    private static boolean checkValidName(String name) {
        if (name.length() > 50)
            return false;
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetterOrDigit(name.charAt(i)))
                return false;
        }
        return true;
    }
}
