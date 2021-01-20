package eg.edu.alexu.csd.oop.game.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background implements GameObject {

    private BufferedImage[] spriteImages;

    public Background(){

        spriteImages = new BufferedImage[1];
        try {
            spriteImages[0] = ImageIO.read(getClass().getResource("/Background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public void setX(int x) { }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setY(int y) { }

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
        return true;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }
}
