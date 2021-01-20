package eg.edu.alexu.csd.oop.game.storage;

import eg.edu.alexu.csd.oop.game.World;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;

public class CareTaker {
    /**
     *
     * @param momento the world we want to save
     * @param name the name we want to give to our world
     * @throws ParserConfigurationException Indicates a serious configuration error.
     * @throws TransformerConfigurationException Indicates a serious configuration error.
     */
    public void saveWorld(World momento,String name  ) throws ParserConfigurationException, TransformerConfigurationException {
         Xml.saveData(momento , name );
    }

    /**
     *
     * @param name the file we gave to our saved game
     * @return the world if it exists
     * @throws IOException if the file is not found
     * @throws SAXException indicates an error in the xml parser
     * @throws ParserConfigurationException Indicates a serious configuration error.
     */
    public World loadWorld(String name) throws IOException, SAXException, ParserConfigurationException {
       return Xml.loadData(name) ;
    }

}
