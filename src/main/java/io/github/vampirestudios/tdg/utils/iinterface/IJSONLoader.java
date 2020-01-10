package io.github.vampirestudios.tdg.utils.iinterface;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface IJSONLoader {

    void load() throws IOException, ParseException;
}
