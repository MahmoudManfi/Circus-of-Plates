package eg.edu.alexu.csd.oop.game.storage;

import eg.edu.alexu.csd.oop.game.Difficulties.Amateur;
import eg.edu.alexu.csd.oop.game.Difficulties.Difficulty;
import eg.edu.alexu.csd.oop.game.Difficulties.Intermediate;
import eg.edu.alexu.csd.oop.game.Difficulties.Pro;
import org.apache.log4j.Logger;

public class DifficultyFactory {
    private String difficultyLevel;
    private static final Logger logger = Logger.getLogger(DifficultyFactory.class);
    public DifficultyFactory(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel.toString();
    }

    public Difficulty getDifficulty() {
        switch (difficultyLevel) {
            case "Pro":
                return new Pro();
            case "Intermediate":
                return new Intermediate();
            case "Amateur":
                return new Amateur();
            default:
            {
                logger.error("Error occurred while making difficulty");
                throw new RuntimeException();
            }
        }
    }
}
