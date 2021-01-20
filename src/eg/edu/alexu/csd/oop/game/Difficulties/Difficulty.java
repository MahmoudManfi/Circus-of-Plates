package eg.edu.alexu.csd.oop.game.Difficulties;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.Shapes.Bar;
import eg.edu.alexu.csd.oop.game.Shapes.Shape;
import eg.edu.alexu.csd.oop.game.Shapes.ShapesPool;

import java.util.List;

public interface Difficulty {

    int getSpeed();

    int getControlSpeed();

    void move(GameObject object);

    void setWind();
    List<Bar> setBars(List<GameObject> constantObjects, List<GameObject> movableObjects, ShapesPool shapesPool,
                      List<Class<? extends Shape>> supportedShapes);

}
