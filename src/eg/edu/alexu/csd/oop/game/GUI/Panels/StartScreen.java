package eg.edu.alexu.csd.oop.game.GUI.Panels;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GUI.Actions.DifficultyAction;
import eg.edu.alexu.csd.oop.game.GUI.Actions.StartAction;
import eg.edu.alexu.csd.oop.game.GUI.Helpers.MenuItems;
import eg.edu.alexu.csd.oop.game.GUI.Helpers.SimpleMenuItemPainter;
import eg.edu.alexu.csd.oop.game.GUI.Sounds.LowSound;
import eg.edu.alexu.csd.oop.game.GUI.Actions.Action;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartScreen extends JPanel {

    private List<String> menuItems;
    private String selectMenuItem;
    private String focusedItem;
    private Map<String, Rectangle> menuBounds;
    private SimpleMenuItemPainter painter;

    private String name; // continue the name of the state screen
    private MenuItems menuItemsClass = MenuItems.getInstance();
    private JFrame jFrame;

    private Image imageStart;
    private Image imageDifficulty;

    private Action action;

    public StartScreen(String name,JFrame jFrame) {
        setBackground(Color.WHITE);
        painter = new SimpleMenuItemPainter();
        menuItems = menuItemsClass.getMenuItems(name);
        this.name = name;
        selectMenuItem = menuItems.get(0);
        this.jFrame = jFrame;
        building();
        try {
            imageStart = ImageIO.read(getClass().getResource("/start.jpg"));
            imageDifficulty = ImageIO.read(getClass().getResource("/difficulty.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        MouseAdapter mouseAdapter = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String newItem = null;
                if (menuBounds != null)
                for (String text : menuItems) {
                    Rectangle bounds = menuBounds.get(text);
                    if (bounds.contains(e.getPoint())) {
                        newItem = text;
                        break;
                    }
                }
                if (newItem != null) {
                    selectMenuItem = newItem;
                    try {
                        action.choose(newItem,jFrame);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                boolean flag = false;
                if (menuBounds != null)
                for (String text : menuItems) {
                    Rectangle bounds = menuBounds.get(text);
                    if (bounds.contains(e.getPoint())) {
                        if (text != focusedItem){
                            new LowSound("/audio/moving.mp3");
                            focusedItem = text;
                            flag = true;
                            repaint();
                            break;
                        }
                        flag = true; break;
                    }
                }
                if (!flag) focusedItem = null;
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "arrowDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "arrowUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");

        actionMap.put("arrowDown", new MenuAction(1));
        actionMap.put("arrowUp", new MenuAction(-1));
        actionMap.put("Enter", new MenuAction(0));

    }

    @Override
    public void invalidate() {
        menuBounds = null;
        super.invalidate();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Game.WIDTH, Game.HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(new Font("Consolas", Font.ITALIC, 30));
        super.paintComponent(g);
        if (name.equals("start")) {
            g.drawImage(imageStart,0,0,this);
            action = new StartAction();
        } else {
            g.drawImage(imageDifficulty,0,0,this);
            action = new DifficultyAction();
        }
        Graphics2D g2d = (Graphics2D) g.create();
        building();
        for (String text : menuItems) {
            Rectangle bounds = menuBounds.get(text);
            boolean isSelected = text.equals(selectMenuItem);
            boolean isFocused = text.equals(focusedItem);
            painter.paint(g2d, text, bounds, isSelected, isFocused);
        }
        g2d.dispose();
    }

    private class MenuAction extends AbstractAction {

        private final int delta;

        public MenuAction(int delta) {
            this.delta = delta;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new LowSound("/audio/moving.mp3");
            int index = menuItems.indexOf(selectMenuItem);
            if (index < 0) {
                selectMenuItem = menuItems.get(0);
            }
            index += delta;
            if (index < 0) {
                selectMenuItem = menuItems.get(menuItems.size() - 1);
            } else if (index >= menuItems.size()) {
                selectMenuItem = menuItems.get(0);
            } else if (delta == 0) {

                try {
                    action.choose(selectMenuItem,jFrame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } else {
                selectMenuItem = menuItems.get(index);
            }
            repaint();
        }

    }

    private void building(){
        if (menuBounds == null) {
            menuBounds = new HashMap<>(menuItems.size());
            int width = 200;
            int height = 60;

            int x = (getWidth() - (width + 10)) / 2;

            int totalHeight = (height + 10) * menuItems.size();
            totalHeight += 5 * (menuItems.size() - 1);

            int y = (getHeight() - totalHeight) / 2;

            for (String text : menuItems) {
                menuBounds.put(text, new Rectangle(x, y, width + 10, height + 10));
                y += height + 10 + 5;
            }

        }
    }

}

