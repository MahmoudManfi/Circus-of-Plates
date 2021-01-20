package eg.edu.alexu.csd.oop.game.Shapes;

import org.apache.log4j.Logger;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

public class ShapesPool {
    private HashMap<Class<? extends Shape>, LinkedList<Shape>> poolMap;
    private Logger logger = Logger.getLogger(ShapesPool.class);

    public ShapesPool() {
        poolMap = new HashMap<>();
    }

    public Shape retrieveShape(Class<? extends Shape> currentShapeClass, Color color) {
        if (poolMap.get(currentShapeClass) == null) {
            logger.debug("New " + currentShapeClass.getSimpleName() + " Has been created");
            return GameObjectFactory.getInstance().createObject(currentShapeClass, color);
        }
        if (poolMap.get(currentShapeClass).isEmpty()) {
            logger.debug("New " + currentShapeClass.getSimpleName() + " Has been created");
            return GameObjectFactory.getInstance().createObject(currentShapeClass, color);
        }
        logger.debug(currentShapeClass.getSimpleName() + " Has been retrieved from Shapes Pool");
        return poolMap.get(currentShapeClass).removeFirst();
    }

    public void returnShape(Shape shape) {
        Class<? extends Shape> shapeClass = shape.getClass();
        LinkedList<Shape> shapeLinkedList = poolMap.computeIfAbsent(shapeClass, k -> new LinkedList<>());
        shapeLinkedList.add(shape);
    }
}
