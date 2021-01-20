package eg.edu.alexu.csd.oop.game.GUI.Sounds;

import eg.edu.alexu.csd.oop.game.GUI.World.MyWorld;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class OffSoundState implements SoundState {

    private JButton OnButton;
    private JButton OffButton;
    private MyWorld myWorld;
    private Sound sound;
    private Logger logger = Logger.getLogger(this.getClass());

    public OffSoundState(JButton OnButton, JButton OffButton, MyWorld myWorld, Sound sound){

        this.OnButton = OnButton;
        this.OffButton = OffButton;
        this.myWorld = myWorld;
        this.sound = sound;

    }

    @Override
    public void onSound() {
        logger.fatal("the sound is already on");
    }

    @Override
    public void offSound() {
        Container parent = OffButton.getParent();
        parent.remove(OffButton);
        OffButton.setVisible(false);
        OnButton.setVisible(true);
        parent.add(OnButton);
        parent.revalidate();
        parent.repaint();
        sound.getMp3Player().setRepeat(true);
        sound.getMp3Player().stop();
        sound.getMp3Player().play();
        myWorld.setSoundState(myWorld.getSoundOnState());
    }
}
