package coffeecatteam.theultimatetile.utils.iinterface;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface JsonLoader {

    void loadJson() throws IOException, ParseException;

}