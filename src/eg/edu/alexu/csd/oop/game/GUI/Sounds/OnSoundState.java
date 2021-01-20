package eg.edu.alexu.csd.oop.game.GUI.Sounds;

import eg.edu.alexu.csd.oop.game.GUI.World.MyWorld;
import org.apache.log4j.Logger;
import javax.swing.*;
import java.awt.*;

public class OnSoundState implements SoundState {

    private JButton OnButton;
    private JButton OffButton;
    private MyWorld myWorld;
    private Sound sound;
    private Logger logger = Logger.getLogger(this.getClass());

    public OnSoundState(JButton OnButton, JButton OffButton, MyWorld myWorld, Sound sound){

        this.OnButton = OnButton;
        this.OffButton = OffButton;
        this.myWorld = myWorld;
        this.sound = sound;

    }

    @Override
    public void onSound() {
        Container parent = OnButton.getParent();
        parent.remove(OnButton);
        OnButton.setVisible(false);
        parent.add(OffButton);
        OffButton.setVisible(true);
        parent.revalidate();
        parent.repaint();
        sound.getMp3Player().pause();
        myWorld.setSoundState(myWorld.getSoundOffState());
    }

    @Override
    public void offSound() {
        logger.fatal("the sound is already off");
    }
}
