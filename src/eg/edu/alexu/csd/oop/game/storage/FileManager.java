package eg.edu.alexu.csd.oop.game.storage;

import eg.edu.alexu.csd.oop.game.Constants.Game;

import java.io.File;

public class FileManager {
    static String fileSeparator = System.getProperty("file.separator");

    public static boolean createFile(String fileName) {

        if (fileName.length() > 50) {
            fileName = fileName.substring(0, 50);
        }


        File xmlFile = new File(Game.databaseDirectory + fileSeparator + fileName + ".xml");
        return xmlFile.mkdir();
    }
}
