package eg.edu.alexu.csd.oop.game.GUI.Helpers;

import eg.edu.alexu.csd.oop.game.Difficulties.Amateur;
import eg.edu.alexu.csd.oop.game.Difficulties.Difficulty;
import eg.edu.alexu.csd.oop.game.Difficulties.Intermediate;
import eg.edu.alexu.csd.oop.game.Difficulties.Pro;

public class MakeDifficulty {

    private static Difficulty pro;
    private static Difficulty intermediate;
    private static Difficulty amateur = new Amateur();
    private MakeDifficulty(){}

    public static Difficulty instance(String type) {

        if (type.equals("Pro")) {

            if (pro == null) {
                pro = new Pro();
            } return pro;

        } else if (type.equals("Intermediate")) {

            if (intermediate == null) {
                intermediate = new Intermediate();
            } return intermediate;

        } else {

            if (amateur == null) {
                amateur = new Amateur();
            } return amateur;

        }

    }
}
