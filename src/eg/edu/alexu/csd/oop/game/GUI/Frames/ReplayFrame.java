package eg.edu.alexu.csd.oop.game.GUI.Frames;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GUI.World.MyWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class ReplayFrame {

    private static JFrame frame;
    private Timer timer;
    private JLabel jLabel;
    private java.util.List<BufferedImage> spriteImages;

    public ReplayFrame() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                spriteImages = MyWorld.bufferedImages();
                final Iterator[] iterator = {spriteImages.iterator()};
                jLabel = new JLabel();
                jLabel.setBackground(Color.black);
                jLabel.setBounds(60,0, Game.WIDTH+50,Game.HEIGHT+100);

                if (iterator[0].hasNext()) setImageSize((BufferedImage) iterator[0].next());

                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!frame.isVisible()) timer.stop();
                        else if (iterator[0].hasNext()) setImageSize((BufferedImage) iterator[0].next());
                        else {
                            iterator[0] = spriteImages.iterator();
                        }
                    }
                });

                timer.start();

                frame = new JFrame("Replay");
                frame.setDefaultCloseOperation(frame.getDefaultCloseOperation());
                JPanel jPanel = new JPanel();
                jPanel.setSize(Game.WIDTH+100,Game.HEIGHT+100);
                jPanel.setBackground(Color.BLACK);
                jPanel.add(jLabel);
                frame.add(jPanel);
                frame.setBackground(Color.BLACK);
                frame.setLayout(null);
                frame.pack();
                frame.setSize(Game.WIDTH+80,Game.HEIGHT+135);
                frame.setResizable(false);
                frame.setVisible(true);
                frame.setLocationRelativeTo((Component)null);

            }
        });
    }

    public void setImageSize(BufferedImage i) {
        Image image = i;
        Image newImage = image.getScaledInstance(jLabel.getWidth(), jLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);
        jLabel.setIcon(newImageIcon);
    }

    public static JFrame getFrame(){
        return frame;
    }

}
