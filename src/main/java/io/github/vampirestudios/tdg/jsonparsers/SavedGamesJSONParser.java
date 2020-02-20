package io.github.vampirestudios.tdg.jsonparsers;

import coffeecatteam.coffeecatutils.io.FileUtils;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.JsonUtils;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONLoader;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONSaver;
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
    protected MaewilEngine maewilEngine;

    public static int SAVE_CAPACITY = 3;
    public static final String DEFAULT_NAME = "UNSAVED";
    public static List<String> GAMES = new ArrayList<>();

    static {
        for (int i = 0; i < SAVE_CAPACITY; i++)
            GAMES.add("false:" + DEFAULT_NAME);
    }

    public SavedGamesJSONParser(MaewilEngine maewilEngine) {
        this.maewilEngine = maewilEngine;
    }

    @Override
    public void load() throws IOException, ParseException {
        Path savesPath = Paths.get("./data/saves/");
        if(!Files.exists(savesPath)) {
            Files.createDirectories(savesPath);
        }
        Path savedGamesPath = Paths.get(path);
        if(!Files.exists(savedGamesPath)) {
            Files.createFile(savedGamesPath);
            save();
        } else {
            JSONParser jsonParser = new JSONParser();
            JsonObject jsonObject = JsonUtils.GSON.fromJson(FileUtils.loadFileOutSideJar(path), JsonObject.class);

            for (int i = 0; i < 3; i++) {
                String tag = "save_" + i;
                if (jsonObject.has(tag)) {
                    JsonObject save = (JsonObject) jsonObject.get(tag);
                    String data = save.get("saved") + ":" + save.get("name");
                    GAMES.set(i, data);
                }
            }
        }

        MaewilLauncher.LOGGER.info("Games loaded!");
    }

    @Override
    public void save() throws IOException {
        JsonObject jsonObject = new JsonObject();

        try {
            for (int i = 0; i < 3; i++) {
                String tag = "save_" + i;
                JsonObject save = new JsonObject();
                String[] data = GAMES.get(i).split(":");
                save.addProperty("saved", Boolean.valueOf(data[0]));
                save.addProperty("name", data[1]);
                jsonObject.add(tag, save);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            MaewilLauncher.LOGGER.error(e.getMessage());
        }

        FileWriter file = new FileWriter(path);
        file.write(JsonUtils.prettyPrintJSON(jsonObject.getAsString()));
        file.flush();

        MaewilLauncher.LOGGER.info("Games saved!");
    }
}
