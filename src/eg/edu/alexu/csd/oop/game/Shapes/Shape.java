package eg.edu.alexu.csd.oop.game.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Shape extends GameObject {
    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getWidth();

    int getHeight();

    boolean isVisible();

    java.awt.image.BufferedImage[] getSpriteImages();

    BufferedImage draw(int width, int height, Color color);

    Color getColor();

}
