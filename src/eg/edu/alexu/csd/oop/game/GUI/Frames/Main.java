package eg.edu.alexu.csd.oop.game.GUI.Frames;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GUI.Panels.StartScreen;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
            new Loading();
    }
    private static JFrame frame;
    public Main() {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                frame = new JFrame(Game.TITLE);
                frame.setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);
                frame.add(new StartScreen("start",frame));
                frame.pack();
                frame.setResizable(false);
                frame.setVisible(true);
                frame.setLocationRelativeTo((Component)null);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        int num = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?",Game.TITLE,JOptionPane.YES_NO_OPTION);
                        if (num == 0) {
                            frame.dispose();
                            System.exit(0);
                        }
                    }
                });
            }
        });
    }

    public static JFrame getFrame() {
        return frame;
    }
}