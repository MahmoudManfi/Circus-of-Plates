package eg.edu.alexu.csd.oop.game.WorldDesign;

import eg.edu.alexu.csd.oop.game.Difficulties.Difficulty;
import eg.edu.alexu.csd.oop.game.DynamicLinkage.DynamicLinkage;
import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.Shapes.Shape;
import eg.edu.alexu.csd.oop.game.Shapes.*;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.controller.ShapesHandler;

import java.awt.*;
import java.util.List;
import java.util.*;

public class CircusWorld implements World {
    private List<GameObject> constantObjects = new LinkedList<>();
    private List<GameObject> movableObjects = new LinkedList<>();
    private List<GameObject> controllableObjects = new LinkedList<>();
    private List<Bar> gameBars;
    private int width, height;
    private Difficulty difficulty;
    private int score = 0;
    private long playTime = 0;
    private  long startTime = System.currentTimeMillis();
    private IClown clown;
    private DynamicLinkage dynamicLinkage = DynamicLinkage.getInstance();
    private final List<Class<? extends Shape>> supportedShapes = dynamicLinkage.loadJars();
    private int counter = 0;
    private boolean flag;
    private ShapesPool shapesPool;

    public CircusWorld(int width, int height, Difficulty difficulty) {
        this.width = width;
        this.height = height;
        flag = true;
        this.difficulty = difficulty;
        this.shapesPool = new ShapesPool();
        constantObjects.add(new Background());
        clown = new Clown(width / 3, (int) (height * 0.75), "/clown" + Game.clown + ".png", Color.GREEN);
        controllableObjects.add(clown);
        gameBars = this.difficulty.setBars(constantObjects, movableObjects, shapesPool, supportedShapes);
    }


    public CircusWorld(int width, int height, Difficulty difficulty, List<GameObject> constantObjects,List<GameObject> movableObjects,List<GameObject> controllableObjects,
                       List<Bar> gameBars,ShapesPool shapesPool,IClown clown) {
        setConstantObjects(constantObjects);
        setControllableObjects(controllableObjects);
        setMovableObjects(movableObjects);
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.shapesPool = shapesPool;
        this.gameBars = gameBars;
        this.clown = clown;
        flag = true;

    }

    /*Setters for load purposes */
    public void setConstantObjects(List<GameObject> constantObjects) {
        this.constantObjects = constantObjects;
    }

    public void setMovableObjects(List<GameObject> movableObjects) {
        this.movableObjects = movableObjects;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
    public List<Bar> getGameBars() {return gameBars;}
    public void setControllableObjects(List<GameObject> controllableObjects) {
        this.controllableObjects = controllableObjects;
    }


    public List<GameObject> getConstantObjects() {
        return constantObjects;
    }

    public List<GameObject> getMovableObjects() {
        return movableObjects;
    }

    public List<GameObject> getControlableObjects() {
        return controllableObjects;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void removeBarObject() {
        Bar bar = gameBars.get(Game.random.nextInt(gameBars.size()));
        bar.dropShape();
    }

    public boolean refresh() {
        if(!flag) return false;
        if (counter % 200 == 0) {
            difficulty.setWind();
        }
        counter++;
        if (flag) playTime = System.currentTimeMillis() - startTime - Game.wastedTime;
        ArrayList<GameObject> objectsDeleted = new ArrayList<>();
        ArrayList<GameObject> objectsCatched = new ArrayList<>();
        Stack<Shape> left = clown.getStackLeft(), right = clown.getStackRight();
        for (GameObject object : movableObjects) {
            difficulty.move(object);
            if (object.getY() > 600) {
                objectsDeleted.add(object);
                continue;
            }
            if (ShapesHandler.validIntersection(left.peek(), object)) {
                object.setX(left.peek().getX());
                object.setY(left.peek().getY() - 10);
                left.push((Shape) object);
                objectsCatched.add(object);
                constantObjects.add(object);
            } else if (ShapesHandler.validIntersection(right.peek(), object)) {
                object.setX(right.peek().getX());
                object.setY(right.peek().getY() - 10);
                right.push((Shape) object);
                objectsCatched.add(object);
                constantObjects.add(object);
            }
            if ((checkStack(left) || checkStack(right)) && flag) {
                score++;
            }
        }
        if (left.size() > 21 || right.size() > 21){
            flag = false;
            return flag;
        }

        for (GameObject gameObject : objectsDeleted) {
            movableObjects.remove(gameObject);
            shapesPool.returnShape((Shape) gameObject);
            removeBarObject();
        }
        for (GameObject gameObject : objectsCatched) {
            movableObjects.remove(gameObject);
            removeBarObject();
        }
        return flag;
    }

    private boolean checkStack(Stack<Shape> stack) {

        Color col = stack.peek().getColor();
        ArrayDeque<Shape> helperQueue = new ArrayDeque<>();

        while (!stack.empty() && stack.peek().getColor().equals(col)) helperQueue.add(stack.pop());

        if (helperQueue.size() == 3) {
            while (!helperQueue.isEmpty()) {
                Shape shape = helperQueue.removeFirst();
                constantObjects.remove(shape);
                shapesPool.returnShape(shape);
            }
            return true;
        }

        while (!helperQueue.isEmpty()) stack.push(helperQueue.removeLast()); // edited by hamza
        return false;
    }

    public String getStatus() {
        return "Score: " + score + "   time: " + playTime / 1000 + '.' + playTime % 1000;
    }

    public int getSpeed() {
        return difficulty.getSpeed();
    }

    public int getControlSpeed() {
        return difficulty.getControlSpeed();
    }

    public long getPlayTime(){
        return playTime;
    }

    public long getPlayScore(){
        return score;
    }

    public void setPlayTime(long time){
        this.startTime = System.currentTimeMillis() - time;
    }

    public void setPlayScore(int score){
        this.score = score;
    }

}
