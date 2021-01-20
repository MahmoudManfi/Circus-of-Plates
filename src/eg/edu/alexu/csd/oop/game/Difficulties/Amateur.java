package eg.edu.alexu.csd.oop.game.Difficulties;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.Shapes.Bar;
import eg.edu.alexu.csd.oop.game.Shapes.Shape;
import eg.edu.alexu.csd.oop.game.Shapes.ShapesPool;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Amateur implements Difficulty {
    private final int speed = 30;
    private final int controlSpeed = 20;
    private int wind = 0;
    private int windRange = 0;

    public int getSpeed() {
        return speed;
    }

    public int getControlSpeed() {
        return controlSpeed;
    }

    public void move(GameObject object) {
        object.setY(object.getY() + 2);
    }

    public void setWind() {
        wind = 0;
    }

    public List<Bar> setBars(List<GameObject> constantObjects, List<GameObject> movableObjects, ShapesPool shapesPool,
                             List<Class<? extends Shape>> supportedShapes) {
        List<Bar> Bars = new LinkedList<>();
        for (int posY = 200; posY <= 200; posY += 20) {
            int curObjects = 4;
            Bar bar = new Bar(0, posY, Game.OBJECT_WIDTH * curObjects + 3 * curObjects, 5, Color.black, curObjects, Game.DIRECTION_EAST);
            constantObjects.add(bar);
            Bars.add(bar);
        }
        for (int posY = 200; posY <= 200; posY += 20) {
            int curObjects = 4;
            int width = Game.OBJECT_WIDTH * curObjects + 3 * curObjects;
            Bar bar = new Bar(Game.WIDTH - width, posY, width, 5, Color.black, curObjects, Game.DIRECTION_WEST);
            constantObjects.add(bar);
            Bars.add(bar);
        }
        for (Bar bar : Bars) {
            bar.initLists(constantObjects, movableObjects, shapesPool, supportedShapes);
            bar.fillBar();
            bar.dropShape();
        }
        return Bars;
    }

}
