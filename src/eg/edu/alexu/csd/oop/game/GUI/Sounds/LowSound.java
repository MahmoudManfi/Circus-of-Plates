package eg.edu.alexu.csd.oop.game.GUI.Sounds;

import jaco.mp3.player.MP3Player;

import javax.swing.*;
import java.awt.*;

public class LowSound {

    public LowSound(String path){

            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        ex.printStackTrace();
                    }

                    MP3Player mp3Player = new MP3Player(getClass().getResource(path));
                    mp3Player.play();

                }
            });

    }

}
