package eg.edu.alexu.csd.oop.game.GUI.Actions;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GUI.Frames.OriginalFrame;
import eg.edu.alexu.csd.oop.game.GUI.Panels.StartScreen;
import eg.edu.alexu.csd.oop.game.GUI.Sounds.LowSound;
import eg.edu.alexu.csd.oop.game.GUI.World.MyWorld;

import javax.swing.*;

public class StartAction implements Action {

    private JFrame jFrame;
    @Override
    public void choose(String typeAction, JFrame jFrame) {
        this.jFrame = jFrame;
        new LowSound("/audio/click.mp3");
        if (typeAction.equals("Continue"))
            continueAction();
        else if (typeAction.equals("Start game"))
            startAction();
        else if (typeAction.equals("Load"))
            loadAction();
        else if (typeAction.equals("Options"))
            optionsAction();
        else
            exitAction();

    }

    private void continueAction(){
        MyWorld.visible();
        jFrame.dispose();
    }

    private void startAction(){
        jFrame.setContentPane(new StartScreen("difficulty",jFrame));
        Game.continueVisible = false;
        jFrame.repaint();
        jFrame.revalidate();
    }

    private void loadAction(){

        new OriginalFrame("Load");

    }

    private void optionsAction(){
        jFrame.setContentPane(new StartScreen("Options",jFrame));
        jFrame.repaint();
        jFrame.revalidate();
    }

    private void exitAction(){
        int num = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?",Game.TITLE,JOptionPane.YES_NO_OPTION);
        if (num == 0) {
            jFrame.dispose();
            System.exit(0);
        }

    }

}
