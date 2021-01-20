package eg.edu.alexu.csd.oop.game.Shapes;

import eg.edu.alexu.csd.oop.game.Constants.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class GameObjectFactory {
    private static HashMap<Class<? extends Shape>, HashMap<Color, FlyWeight>> cache = new HashMap<>();
    private static GameObjectFactory instance;


    private GameObjectFactory() {
    }

    public static synchronized GameObjectFactory getInstance() {
        synchronized (GameObjectFactory.class) {
            if (instance == null) {
                return instance = new GameObjectFactory();
            }
            return instance;
        }
    }

    public Shape createObject(Class<? extends Shape> c, Color color) {
        try {
            Constructor<?>[] constructor = c.getConstructors();
            FlyWeight current;
            Shape shape = null;
            if (constructor[0].getParameterTypes().length == 3)
                shape = (Shape) constructor[0].newInstance(0, 0, null);
            else
                shape = (Shape) constructor[1].newInstance(0, 0, null);
            HashMap<Color, FlyWeight> hashMap = cache.computeIfAbsent(c, k -> new HashMap<>());
            if (!hashMap.containsKey(color)) {
                BufferedImage image = shape.draw(Game.OBJECT_WIDTH, Game.OBJECT_HEIGHT, color);
                current = new FlyWeight(image, color);
            } else {
                current = hashMap.get(color);
            }
            if (constructor[0].getParameterTypes().length == 3)
                return (Shape) constructor[0].newInstance(0, 0, current);
            return (Shape) constructor[1].newInstance(0, 0, current);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
