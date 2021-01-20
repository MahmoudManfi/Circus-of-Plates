package eg.edu.alexu.csd.oop.game.GUI.World;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.Difficulties.Difficulty;
import eg.edu.alexu.csd.oop.game.GUI.Frames.Main;
import eg.edu.alexu.csd.oop.game.GUI.Frames.OriginalFrame;
import eg.edu.alexu.csd.oop.game.GUI.Frames.ReplayFrame;
import eg.edu.alexu.csd.oop.game.GUI.Helpers.Boreder;
import eg.edu.alexu.csd.oop.game.GUI.Helpers.MakeDifficulty;
import eg.edu.alexu.csd.oop.game.GUI.Sounds.OffSoundState;
import eg.edu.alexu.csd.oop.game.GUI.Sounds.OnSoundState;
import eg.edu.alexu.csd.oop.game.GUI.Sounds.Sound;
import eg.edu.alexu.csd.oop.game.GUI.Sounds.SoundState;
import eg.edu.alexu.csd.oop.game.GameEngine;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.WorldDesign.CircusWorld;
import eg.edu.alexu.csd.oop.game.GUI.Frames.Loading;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * this class to send the world instance to the game engine
 */

public class MyWorld {
    private JButton OnButton;
    private JButton OffButton;
    private Difficulty difficulty;
    private int width;
    private int height;
    private Color color;
    private static JFrame jFrame;
    private static JPanel jPanel;
    private static GameEngine.GameController gameController;
    private static Sound sound;
    private static List<BufferedImage> spriteImages;
    private static Timer timer;
    private long start;
    private World[] world;
    private SoundState soundState;
    private SoundState soundOnState;
    private SoundState soundOffState;

    public MyWorld(Difficulty difficulty, int width, int height, Color color) {

        this.difficulty = difficulty;
        this.color = color;
        this.width = width;
        this.height = height;
        spriteImages = new LinkedList<>();
        sound = new Sound("/audio/circus.mp3");
        sound.getMp3Player().setRepeat(true);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage img = new BufferedImage(Game.WIDTH + 50, Game.HEIGHT + 100, BufferedImage.TYPE_INT_RGB);
                jFrame.paint(img.getGraphics());
                spriteImages.add(img);
                if (spriteImages.size() == 60) spriteImages.remove(spriteImages.get(0));
                if (world[0].refresh() == false){
                    boolean flag = false;
                    long score = ((CircusWorld)world[0]).getPlayScore();
                    long time = ((CircusWorld)world[0]).getPlayTime();
                    String message = "you got score:    " + score + "\nin time:    " +time/1000;
                    try{
                        flag = Loading.writeLargest(score,time);
                    } catch (Exception ex){
                        ex.getStackTrace();
                    }
                    if (flag) message = "Congratulation! \n"+message;
                    JOptionPane.showConfirmDialog(null, message, Game.TITLE, JOptionPane.WARNING_MESSAGE);
                    timer.stop();

                }

            }
        });
        timer.start();

    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            difficulty = MakeDifficulty.instance(e.getActionCommand());
            newGame();
        }
    };

    public void Make() {

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        menu.add(newMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menu.addSeparator();
        menu.add(saveMenuItem);
        menuBar.add(menu);

        JMenu levels = new JMenu("Levels");
        JMenuItem amateurMenu = new JMenuItem("Amateur");
        amateurMenu.setActionCommand("Amateur");
        amateurMenu.addActionListener(actionListener);
        JMenuItem intermediateMenu = new JMenuItem("Intermediate");
        intermediateMenu.setActionCommand("Intermediate");
        intermediateMenu.addActionListener(actionListener);
        JMenuItem proMenu = new JMenuItem("Pro");
        proMenu.setActionCommand("Pro");
        proMenu.addActionListener(actionListener);
        levels.add(amateurMenu);
        levels.add(intermediateMenu);
        levels.add(proMenu);
        menuBar.add(levels);

        world = new World[1];
        if (Game.world == null){
            world[0] = new eg.edu.alexu.csd.oop.game.WorldDesign.CircusWorld(width, height, difficulty); // edit hamza
        } else {
            world[0] = Game.world; difficulty = Game.difficulty;
            Game.world = null;
        }
        gameController = GameEngine.start("Circus Of Plates", world[0], menuBar, 0, color); // edit hamza
        jFrame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
        jPanel = (JPanel) jFrame.getContentPane().getComponent(0);
        makeBar();    frameAction(); Game.wastedTime = 0;

        newMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        saveMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                saveAction();
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                stopTimer();
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                resumeAction();
            }
        });
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int num = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?",Game.TITLE,JOptionPane.YES_NO_OPTION);
                if (num == 0) {
                    jFrame.dispose();
                    System.exit(0);
                }
            }
        });
    }

    private void makeBar(){

        Dimension dimension= new Dimension(50,50);

        ImageIcon replay = new ImageIcon("res/replayButton.jpg");
        replay = new ImageIcon(replay.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
        ImageIcon mainMenu = new ImageIcon("res/mainMenu.png");
        mainMenu = new ImageIcon(mainMenu.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
        ImageIcon soundIconOn = new ImageIcon("res/on.png");
        soundIconOn = new ImageIcon(soundIconOn.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
        ImageIcon soundIconOff = new ImageIcon("res/off.png");
        soundIconOff = new ImageIcon(soundIconOff.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));

        JButton replayButton = new JButton(replay);
        replayButton.setBackground(Color.WHITE);
        replayButton.setPreferredSize(dimension);
        replayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.pause();
                if (ReplayFrame.getFrame() != null && ReplayFrame.getFrame().isVisible()) ReplayFrame.getFrame().dispose();
                if (timer.isRunning()) start = System.currentTimeMillis();
                timer.stop();
                sound.getMp3Player().pause();
                new ReplayFrame();
            }
        });
        OnButton = new JButton(soundIconOn);
        OnButton.setBorder(new Boreder(30));
        OnButton.setBackground(Color.CYAN);
        OffButton = new JButton(soundIconOff);
        OffButton.setBorder(new Boreder(30));
        OffButton.setBackground(Color.CYAN);
        OnButton.setPreferredSize(new Dimension(30,30));
        OffButton.setPreferredSize(new Dimension(30,30));
        OnButton.setVisible(false);
        OnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                soundState.onSound();
            }
        });

        OffButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                soundState.offSound();
            }
        });

        JButton mainMenuButton = new JButton(mainMenu);
        mainMenuButton.setBackground(Color.WHITE);
        mainMenuButton.setPreferredSize(dimension);
        mainMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (world[0].refresh() == true) main("main");
                else main ("Loading");
            }
        });

        jPanel.add(mainMenuButton);
        jPanel.add(Box.createHorizontalStrut(380));

        jPanel.add(replayButton);
        jPanel.add(Box.createHorizontalStrut(400));
        jPanel.add(OffButton);
        jPanel.setPreferredSize(new Dimension(Game.WIDTH,50));

        soundOnState = new OnSoundState(OnButton,OffButton,this,sound);
        soundOffState = new OffSoundState(OnButton,OffButton,this,sound);
        soundState = soundOffState;

    }

    public void frameAction(){

        InputMap im = jFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = jFrame.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "menu");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "resume");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK), "save");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "sound");
        am.put("menu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (world[0].refresh() == true) main("main");
                else main ("Loading");
            }
        });
        am.put("resume", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning())stopTimer();
                else resumeAction();
            }
        });
        am.put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAction();
            }
        });
        am.put("sound", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (soundState.getClass().equals( soundOnState.getClass())){
                    soundState.onSound();
                } else soundState.offSound();
            }
        });
    }

    public static void visible(){
        jFrame.setVisible(true);
    }

    public static List<BufferedImage> bufferedImages(){
        return spriteImages;
    }

    private void OnOffButton(){

        if (OnButton.isVisible()) sound.getMp3Player().play();

    }

    private void restartReplay(){
        gameController.resume();
        spriteImages.clear();
        timer.start();
        OnOffButton();
        Game.wastedTime=0;
    }

    private void stopTimer(){
        gameController.pause();
        sound.getMp3Player().pause();
        if (timer.isRunning()) start = System.currentTimeMillis();
        timer.stop();
    }
    private void resumeAction(){
        if (ReplayFrame.getFrame() != null && ReplayFrame.getFrame().isVisible()){
            ReplayFrame.getFrame().dispose();
        }
        OnOffButton();
        if (!timer.isRunning()) Game.wastedTime += System.currentTimeMillis() - start;
        gameController.resume(); timer.start();
    }

    private void saveAction(){
        new OriginalFrame("Save").setWorld(world[0]);
        stopTimer();
    }

    private void newGame(){
        if (world[0].refresh() == true){
            gameController.changeWorld(world[0] = new eg.edu.alexu.csd.oop.game.WorldDesign.CircusWorld(width, height,difficulty));
            if (ReplayFrame.getFrame() != null && ReplayFrame.getFrame().isVisible()) ReplayFrame.getFrame().dispose();
            restartReplay();
        } else {

            main("Loading");

        }

    }

    private void main(String str){
        stopTimer();
        jFrame.setVisible(false);
        if (ReplayFrame.getFrame() != null && ReplayFrame.getFrame().isVisible()) ReplayFrame.getFrame().dispose();
        if (world[0].refresh() == true)  Game.continueVisible = true;
        else Game.continueVisible = false;
        if (str.equals("Loading")) {
            new Loading();
        }
        else new Main();
    }

    public void setSoundState(SoundState soundState) {
        this.soundState = soundState;
    }

    public SoundState getSoundOnState(){
        return soundOnState;
    }

    public SoundState getSoundOffState(){
        return soundOffState;
    }

}
