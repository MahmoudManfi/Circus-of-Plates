package eg.edu.alexu.csd.oop.game.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.*;
import java.util.Stack;

public interface IClown extends GameObject {

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getWidth();

    int getHeight();

    boolean isVisible();

    java.awt.image.BufferedImage[] getSpriteImages();

    public Color getColor();

    Stack<Shape> getStackLeft();

    Stack<Shape> getStackRight();

}
