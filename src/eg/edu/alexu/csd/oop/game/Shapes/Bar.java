package eg.edu.alexu.csd.oop.game.Shapes;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Bar implements Shape {
    private int height;
    private static final int DEFAULT_SPRITE_HEIGHT = 5;
    private BufferedImage[] spriteImages;
    private int xCoordinates;
    private int yCoordinates;
    private int width;
    private boolean visible;
    private Color color;
    private int objectsLimit = 0;
    private LinkedList<Shape> shelfObjects;
    private List<GameObject> constantObject, movableObject;
    private List<Class<? extends Shape>> supportShapes;
    private ShapesPool shapesPool;
    private int direction;
    private static final int DISTANCE_BETWEEN_OBJECTS = 3;
    public Bar(int posX, int posY, int width, int height, Color color, int objectsLimit, int direction) {
        this.xCoordinates = posX;
        this.yCoordinates = posY;
        this.width = width;
        this.visible = true;
        this.height = height;
        this.shelfObjects = new LinkedList<>();
        this.color = color;
        this.objectsLimit = objectsLimit;
        this.direction = direction;
        spriteImages = new BufferedImage[]{new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB)};
        draw(0, 0, Color.WHITE);
    }

    public Bar() {

    }
    public Bar(int posX, int posY, int width, Color color, int objectsLimit) {
        this(posX, posY, width, DEFAULT_SPRITE_HEIGHT, color, objectsLimit, Game.DIRECTION_NONE);
    }


    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public int getX() {
        return xCoordinates;
    }

    @Override
    public void setX(int x) {
        xCoordinates = x;
    }

    @Override
    public int getY() {
        return yCoordinates;
    }

    @Override
    public void setY(int y) {
        yCoordinates = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }

    public Color getColor() {
        return color;
    }

    public BufferedImage draw(int width, int height, Color c) {
        Graphics2D g2 = spriteImages[0].createGraphics();
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(color);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.drawLine(0, 0, getWidth(), 0);
        g2.dispose();
        return spriteImages[0];
    }

    public void initLists(List<GameObject> constantObject, List<GameObject> movableObject,ShapesPool shapesPool,
                          List<Class<? extends Shape>> supportedShapes) {
        this.constantObject = constantObject;
        this.movableObject = movableObject;
        this.shapesPool = shapesPool;
        this.supportShapes = supportedShapes;
    }

    public void addGameObject(GameObject gameObject) {
        if (direction == Game.DIRECTION_EAST)
            shelfObjects.addFirst((Shape)gameObject);
        else if (direction == Game.DIRECTION_WEST)
            shelfObjects.addLast((Shape)gameObject);
        gameObject.setY(getY() - gameObject.getHeight());
        assert constantObject != null;
        constantObject.add(gameObject);
        drawShelfObjects();
    }

    private GameObject getLastGameObject() throws NullPointerException {
        if (shelfObjects.isEmpty()) throw new NullPointerException("The Shelf is currently empty");
        if(direction == Game.DIRECTION_EAST)
            return shelfObjects.getLast();
        return shelfObjects.getFirst();
    }

    private GameObject removeLastGameObject() throws NullPointerException {
        if (shelfObjects.isEmpty()) throw new NullPointerException("The Shelf is currently empty");
        GameObject lastObject = getLastGameObject();
        if(direction == Game.DIRECTION_EAST)
            shelfObjects.removeLast();
        else if(direction == Game.DIRECTION_WEST)
            shelfObjects.removeFirst();
        return lastObject;
    }

    public void dropShape() throws NullPointerException {
        if (shelfObjects.isEmpty()) throw new NullPointerException("The Shelf is currently empty");
        GameObject droppedObject = removeLastGameObject();
        assert constantObject != null;
        constantObject.remove(droppedObject);
        assert movableObject != null;
        movableObject.add(droppedObject);
        if (direction == Game.DIRECTION_EAST)
            droppedObject.setX(getX() + getWidth() + (droppedObject.getWidth() / 2));
        else if (direction == Game.DIRECTION_WEST)
            droppedObject.setX(getX() - (droppedObject.getWidth() / 2));
        fillBar();
    }

    private void drawShelfObjects() {

        if (direction == Game.DIRECTION_EAST) {
            int previousSpace = 0;
            for (GameObject currentObject : shelfObjects) {
                currentObject.setX(getX() + previousSpace + DISTANCE_BETWEEN_OBJECTS);
                previousSpace += currentObject.getWidth() + DISTANCE_BETWEEN_OBJECTS;
            }
        } else if (direction == Game.DIRECTION_WEST) {
            int previousSpace = -3;
            for (GameObject currentObject : shelfObjects) {
                currentObject.setX(getX() + previousSpace + DISTANCE_BETWEEN_OBJECTS);
                previousSpace += currentObject.getWidth() + DISTANCE_BETWEEN_OBJECTS;
            }
        }
    }

    public void fillBar() {
        while (shelfObjects.size() < objectsLimit) {
            Shape shape = shapesPool.retrieveShape(supportShapes.get(Game.random.nextInt(supportShapes.size())), Game.RANDOM_COLORS[Game.random.nextInt(Game.RANDOM_COLORS.length)]);
            addGameObject(shape);
        }
    }
    public Map<String,String> getBarValues()
    {
        Map<String,String> elements = new HashMap<>();
        elements.put("x",String.valueOf(getX()));
        elements.put("y",String.valueOf(getY()));
        elements.put("width",String.valueOf(getWidth()));
        elements.put("height",String.valueOf(getHeight()));
        elements.put("objectsLimit",String.valueOf(objectsLimit));
        elements.put("direction",String.valueOf(direction));
        return elements;
    }

    public List<Shape> getShelfObjects() {
        return shelfObjects;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
