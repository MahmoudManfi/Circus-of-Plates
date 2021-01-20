package eg.edu.alexu.csd.oop.game.GUI.Sounds;

import jaco.mp3.player.MP3Player;


public class Sound {

    private MP3Player mp3Player;

    public Sound(String path){

        try{

            mp3Player = new MP3Player(getClass().getResource(path));

        }catch (Exception e) {
            e.getStackTrace();
        }

    }

    public MP3Player getMp3Player() {
        return mp3Player;
    }
}
