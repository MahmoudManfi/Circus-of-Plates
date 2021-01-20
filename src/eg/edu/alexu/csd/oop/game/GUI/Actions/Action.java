package eg.edu.alexu.csd.oop.game.GUI.Actions;

import javax.swing.*;
import java.io.IOException;

public interface Action {

    void choose(String typeAction, JFrame jFrame) throws IOException;

}
