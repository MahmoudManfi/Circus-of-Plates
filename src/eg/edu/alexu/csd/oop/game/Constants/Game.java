package eg.edu.alexu.csd.oop.game.Constants;

import eg.edu.alexu.csd.oop.game.Difficulties.Difficulty;
import eg.edu.alexu.csd.oop.game.Difficulties.Pro;
import eg.edu.alexu.csd.oop.game.World;

import java.awt.*;
import java.util.Random;

public class Game {
    private static String fileSeparator = System.getProperty("file.separator");


    public static String databaseDirectory = "Databases" ;

    private static final int width = 950;
    public static final int WIDTH = width ;

    private static final int heigth = 700;
    public static final int HEIGHT = heigth ;

    private static final String  title = "Circus Of Plates";
    public static final String TITLE = title ;

    public static boolean continueVisible = false;
    public static Color color = Color.white;
    public static int clown = 1;
    public static String clownPath = "res"+fileSeparator+"clown"+clown + ".png";

    public static World world = null;
    public final static Color[] RANDOM_COLORS = {Color.BLUE, Color.CYAN, Color.GREEN};
    public final static Random random = new Random();
    public static final String JAR_PATH = "\\Jars";
    public static final int DIRECTION_NONE = 0;
    public static final int DIRECTION_EAST = 1;
    public static final int DIRECTION_WEST = 2;
    public static final int OBJECT_WIDTH = 40;
    public static final int OBJECT_HEIGHT = 10;
    public static Difficulty difficulty = new Pro();
    public static String separator = System.getProperty("file.separator") ;

    public static long wastedTime = 0;
}
