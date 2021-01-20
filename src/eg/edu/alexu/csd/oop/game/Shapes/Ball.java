package eg.edu.alexu.csd.oop.game.Shapes;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Ball implements Shape , Colorable{
    private FlyWeight ballFly;
    private int xCoordinates, yCoordinates;



    private Boolean visible;


    public Ball(int posX, int posY, FlyWeight fly) {
        this.xCoordinates = posX;
        this.yCoordinates = posY;
        this.visible = true;
        this.ballFly = fly;
    }
    public Ball(){}

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
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
        return ballFly.getSPRITE_WIDTH();
    }

    @Override
    public int getHeight() {
        return ballFly.getSPRITE_HEIGHT();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return ballFly.getSpriteImages();
    }

    @Override
    public Color getColor() {
        return ballFly.getColor();
    }

    public BufferedImage draw(int width, int height, Color color) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(color);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double x = width / 2;
        double y = width / 2;
        int x1 = (int) ((width / 2.0) - x);
        int y1 = (int) ((width / 2.0) - y);
        int x2 = (int) ((width / 2.0) + x);
        int y2 = (int) ((width / 2.0) + y);
        g2.setStroke(new BasicStroke(3));
        g2.setColor(color);
        g2.fillOval(x1, y1, x2, y2);
        g2.setColor(Color.WHITE);
        g2.drawArc(x1 + 12, y1 + 8, x2 - 22, y2 - 22, 0, 80);
        g2.dispose();
        return image;
    }
}
