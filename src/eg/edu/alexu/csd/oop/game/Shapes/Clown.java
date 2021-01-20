package eg.edu.alexu.csd.oop.game.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class Clown implements IClown {


    private int x, y;
    private BufferedImage[] spriteImages;
    private boolean visible;
    private Stack<Shape> stackLeft;
    private Stack<Shape> stackRight;
    private Color color;
    private EventListener listener;

    public Clown(int x, int y, String path, Color color) {

        this.x = x;
        this.y = y;
        this.color = color;
        visible = true;
        stackLeft = new Stack<>();
        stackRight = new Stack<>();
        spriteImages = new BufferedImage[1];
        try {
            spriteImages[0] = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stackLeft.add(new Bar(getX() + 6, getY(), getWidth() / 4 - 5, Color.RED, 0)); // added by  Hamza
        stackRight.add(new Bar(getX() + getWidth() - getWidth() / 4 - 2, getY(), getWidth() / 4 - 5, Color.RED, 0));// added by  Hamza
        this.listener = new EventListener(stackLeft, stackRight);
    }

    public Clown(int x, int y, String path, Color color, Stack<Shape> stackLeft, Stack<Shape> stackRight) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.visible = true;
        setStackLeft(stackLeft);
        setStackRight(stackRight);
        spriteImages = new BufferedImage[1];
        try {
            spriteImages[0] = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.listener = new EventListener(stackLeft, stackRight);
    }

    public Clown() {

    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setStackLeft(Stack<Shape> stackLeft) {
        this.stackLeft = stackLeft;
    }

    public void setStackRight(Stack<Shape> stackRight) {
        this.stackRight = stackRight;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        int diff = this.x - x;
        this.x = x;
        listener.notify(diff);
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
    }

    @Override
    public int getWidth() {
        return spriteImages[0].getWidth();
    }

    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Stack<Shape> getStackLeft() {
        return stackLeft;
    }

    @Override
    public Stack<Shape> getStackRight() {
        return stackRight;
    }

}
