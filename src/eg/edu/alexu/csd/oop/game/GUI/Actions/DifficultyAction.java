package eg.edu.alexu.csd.oop.game.GUI.Actions;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GUI.Helpers.MakeDifficulty;
import eg.edu.alexu.csd.oop.game.GUI.Panels.StartScreen;
import eg.edu.alexu.csd.oop.game.GUI.Sounds.LowSound;
import eg.edu.alexu.csd.oop.game.GUI.World.MyWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DifficultyAction implements Action {

    private MyWorld myWorld;
    private JFrame jFrame;

    @Override
    public void choose(String typeAction, JFrame jFrame) {
        this.jFrame = jFrame;

        new LowSound("/audio/click.mp3");

        if (typeAction.equals("Back"))
           backAction();
        else if(typeAction.equals("Clown")){

            changeClown();

        } else if (typeAction.equals("Background")){

            changeColor();

        }
        else {
            myWorld = new MyWorld(MakeDifficulty.instance(typeAction),Game.WIDTH,Game.HEIGHT, Game.color);
            jFrame.dispose();
            myWorld.Make();
        }

    }

    private void backAction(){

        jFrame.setContentPane(new StartScreen("start",jFrame));
        jFrame.repaint();
        jFrame.revalidate();

    }

    private void changeClown(){

        String[] messageStrings = {"Green","Blue"};
        JComboBox jComboBox = new JComboBox(messageStrings);
        JLabel jLabel = new JLabel("Green clown");
        JButton jButton = new JButton("Ok");
        jButton.setFont(new Font("Serif",Font.ITALIC, 25));
        jComboBox.setSelectedIndex(0);
        JFrame jFrame = new JFrame("Choose clown color");
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setSize(500,150);
        jFrame.setVisible(true);
        JPanel jPanel = new JPanel();
        jPanel.add(jComboBox);
        jPanel.add(Box.createHorizontalStrut(10));
        jPanel.add(jLabel);
        jPanel.add(Box.createHorizontalStrut(300));
        jPanel.add(jButton);
        jFrame.add(jPanel);
        jComboBox.setPreferredSize(new Dimension(100,50));
        jComboBox.setFont(new Font("Serif",Font.ITALIC, 20));
        jLabel.setFont(new Font("Serif",Font.ITALIC, 25));
        jPanel.setBackground(Color.GREEN);
        jComboBox.setBackground(Color.GREEN);
        jButton.setSelected(true);

        jComboBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    jFrame.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.clown = jComboBox.getSelectedIndex() + 1;
                Game.continueVisible = false;
                if (Game.clown == 1) {
                    jLabel.setText("Green clown");
                    jPanel.setBackground(Color.GREEN);
                    jComboBox.setBackground(Color.GREEN);
                }
                else {
                    jLabel.setText("Blue clown");
                    jPanel.setBackground(Color.BLUE);
                    jComboBox.setBackground(Color.BLUE);
                }
            }
        });

        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jFrame.dispose();
            }
        });

    }

    private void changeColor(){

        Color color = JColorChooser.showDialog(null, "Change background color", Color.WHITE);
        if (color == null) {
            color = Color.WHITE;
        }
        Game.color = color;
        Game.continueVisible = false;
    }

}
