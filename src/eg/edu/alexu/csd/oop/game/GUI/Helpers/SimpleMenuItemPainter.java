package eg.edu.alexu.csd.oop.game.GUI.Helpers;

import java.awt.*;

public class SimpleMenuItemPainter {

    public void paint(Graphics2D g2d, String text, Rectangle bounds, boolean isSelected, boolean isFocused) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        if (isSelected) {
            paintBackground(g2d, bounds, Color.BLUE, Color.WHITE);
        } else if (isFocused) {
            paintBackground(g2d, bounds, Color.CYAN, Color.BLACK);
        } else {
            paintBackground(g2d, bounds, Color.DARK_GRAY, Color.LIGHT_GRAY);
        }
        int x = bounds.x + ((bounds.width - fontMetrics.stringWidth(text)) / 2);
        int y = bounds.y + ((bounds.height - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();
        g2d.setColor(isSelected ? Color.WHITE : Color.LIGHT_GRAY);
        g2d.drawString(text, x, y);
    }

    private void paintBackground(Graphics2D g2d, Rectangle bounds, Color background, Color foreground) {
        g2d.setColor(background);
        g2d.fill(bounds);
        g2d.setColor(foreground);
        g2d.draw(bounds);
    }

}