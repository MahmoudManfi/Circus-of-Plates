package eg.edu.alexu.csd.oop.game.GUI.Helpers;

import eg.edu.alexu.csd.oop.game.Constants.Game;

import java.util.ArrayList;
import java.util.List;

public class MenuItems {

    private static MenuItems menuItems;
    private MenuItems(){}
    private static List<String> difficultyItems;
    private static List<String> startItems;
    private static List<String> OptionItems;

    public static MenuItems getInstance(){

        if (menuItems == null) {
            menuItems = new MenuItems();
        }
        difficultyItems = new ArrayList<>(25);
        difficultyItems.add("Amateur");
        difficultyItems.add("Intermediate");
        difficultyItems.add("Pro");
        difficultyItems.add("Back");

        startItems = new ArrayList<>(25);
        if(Game.continueVisible) startItems.add("Continue");
        startItems.add("Start game");
        startItems.add("Load");
        startItems.add("Options");
        startItems.add("Exit");

        OptionItems = new ArrayList<>(25);
        OptionItems.add("Clown");
        OptionItems.add("Background");
        OptionItems.add("Back");

        return menuItems;
    }

    public List<String> getMenuItems(String name){

        if (name.equals("start")) {
            return startItems;
        } else if (name.equals("difficulty")) {
            return difficultyItems;
        }
        return OptionItems;
    }

}
