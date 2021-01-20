package eg.edu.alexu.csd.oop.game.GUI.Panels;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GUI.Frames.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingJPanel extends JPanel {
    private Timer timer;
    private int counter;
    private JFrame jFrame;
    private long score = 0;
    private long time = 0;
    private final String text1;
    private final String text2;

    public LoadingJPanel(JFrame jFrame, long score, long time){
        this.jFrame = jFrame;
        this.score = score;
        this.time = time;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                if (counter == 10){
                    jFrame.dispose();
                    new Main();
                    timer.stop();
                } else{
                    repaint();
                }
            }
        });
        timer.start();
        text1 = "The largest score you reached is " + score;
        text2 = "in "+ time/1000 +" seconds";
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Game.WIDTH, Game.HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setFont(new Font("Consolas", Font.BOLD, 30));
        super.paintComponent(g);
        Image image = null;
        try{
            image = ImageIO.read(getClass().getResource("/Loading/"+counter+".jpg"));
        } catch (Exception e){
            e.getStackTrace();
        }

        g.drawImage(image,0,0,this);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.white);
        g2d.drawString(text1,230,300);
        g2d.drawString(text2,300,340);
        g2d.dispose();

    }
}
