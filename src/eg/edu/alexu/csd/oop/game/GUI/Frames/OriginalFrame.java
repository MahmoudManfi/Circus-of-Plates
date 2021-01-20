package eg.edu.alexu.csd.oop.game.GUI.Frames;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GUI.World.MyWorld;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.storage.CareTaker;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OriginalFrame {
    private World world;
    private static JFrame jFrame;
    private CareTaker Xml = new CareTaker() ; // Momento
    private JButton jButton;
    private JTextField jTextField;

    public OriginalFrame(String title){

        if (jFrame != null && jFrame.isVisible()) jFrame.dispose();

        jFrame = new JFrame(title);
        jTextField = new JTextField(20);
        JLabel jLabel = new JLabel("Type the name of the file");
        jButton = new JButton(title);
        JPanel jPanel = new JPanel();

        jPanel.add(jTextField);
        jPanel.add(jLabel);
        jPanel.add(Box.createHorizontalStrut(100));
        jButton.setPreferredSize(new Dimension(100,30));
        jPanel.add(jButton);

        jFrame.add(jPanel);
        jFrame.setSize(500,200);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(jFrame.getDefaultCloseOperation());
        jFrame.setVisible(true);
        jTextField.setFont(new Font("Serif",Font.BOLD, 25));
        jLabel.setFont(new Font("Serif", Font.ITALIC, 20));
        jButton.setFont(new Font("Serif", Font.ITALIC, 20));
        jButton.setSelected(true);
        jTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    performAction(title);

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                performAction(title);

            }
        });

    }

    private void performAction(String title){
        if (title.equals("Load")) {
            try {
                Game.world = Xml.loadWorld(jTextField.getText());
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
            if (Game.world == null) {

                JOptionPane.showConfirmDialog(null, "You Entered wrong name", Game.TITLE, JOptionPane.WARNING_MESSAGE);

            } else {
                Main.getFrame().dispose();
                jFrame.dispose();
                MyWorld myWorld = new MyWorld(Game.difficulty, Game.WIDTH, Game.HEIGHT, Game.color);
                myWorld.Make();


            }

        } else {
            try {
                jFrame.dispose();
                Xml.saveWorld(world , jTextField.getText());
            } catch (ParserConfigurationException | TransformerConfigurationException ex) {
                ex.printStackTrace();
            } catch (RuntimeException re){
                System.out.println("Invalid name for the file -> the name should contain only letters and digits and less than 50 characters  :)");
            }


        }
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
