package eg.edu.alexu.csd.oop.game.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlyWeight {
    private final int SPRITE_WIDTH = 40;
    private final int SPRITE_HEIGHT = 10;
    private BufferedImage[] spriteImages = new BufferedImage[1];
    private Color color;

    public FlyWeight(BufferedImage image, Color color) {
        this.spriteImages[0] = image;
        this.color = color;
    }


    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }

    public int getSPRITE_HEIGHT() {
        return SPRITE_HEIGHT;
    }

    public int getSPRITE_WIDTH() {
        return SPRITE_WIDTH;
    }

    public Color getColor() {
        return color;
    }
}
