package io.github.vampirestudios.tdg.jsonparsers;

import coffeecatteam.coffeecatutils.io.FileUtils;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONLoader;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONSaver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SavedGamesJSONParser implements IJSONLoader, IJSONSaver {

    private String path = "./data/saves/saved_games.json";
    protected TutEngine tutEngine;

    public static int SAVE_CAPACITY = 3;
    public static final String DEFAULT_NAME = "UNSAVED";
    public static List<String> GAMES = new ArrayList<>();

    static {
        for (int i = 0; i < SAVE_CAPACITY; i++)
            GAMES.add("false:" + DEFAULT_NAME);
    }

    public SavedGamesJSONParser(TutEngine tutEngine) {
        this.tutEngine = tutEngine;
    }

    @Override
    public void load() throws IOException, ParseException {
        Path savesPath = Paths.get("./data/saves/");
        if(!Files.exists(savesPath)) {
            Files.createDirectories(savesPath);
            Files.createFile(Paths.get(path));
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(FileUtils.loadFileOutSideJar(path));

        for (int i = 0; i < 3; i++) {
            String tag = "save_" + i;
            if (jsonObject.containsKey(tag)) {
                JsonObject save = (JsonObject) jsonObject.get(tag);
                String data = save.get("saved") + ":" + save.get("name");
                GAMES.set(i, data);
            }
        }

        TutLauncher.LOGGER.info("Games loaded!");
    }

    @Override
    public void save() throws IOException {
        JSONObject jsonObject = new JSONObject();

        try {
            for (int i = 0; i < 3; i++) {
                String tag = "save_" + i;
                JSONObject save = new JSONObject();
                String[] data = GAMES.get(i).split(":");
                save.put("saved", Boolean.valueOf(data[0]));
                save.put("name", data[1]);
                jsonObject.put(tag, save);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            TutLauncher.LOGGER.error(e.getMessage());
        }

        FileWriter file = new FileWriter(path);
        file.write(jsonObject.toString());
        file.flush();

        TutLauncher.LOGGER.info("Games saved!");
    }
}
