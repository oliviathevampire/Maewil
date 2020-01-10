package io.github.vampirestudios.tdg.jsonparsers;

import coffeecatteam.coffeecatutils.io.FileUtils;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;
import io.github.vampirestudios.tdg.utils.JsonUtils;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONLoader;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONSaver;

import java.io.FileWriter;
import java.io.IOException;
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
    public void load() throws IOException {
        JsonObject jsonObject = JsonUtils.GSON.fromJson(FileUtils.loadFileOutSideJar(path), JsonObject.class);

        for (int i = 0; i < 3; i++) {
            String tag = "save_" + i;
            if (jsonObject.has(tag)) {
                JsonObject save = (JsonObject) jsonObject.get(tag);
                String data = save.get("saved") + ":" + save.get("name");
                GAMES.set(i, data);
            }
        }

        TutLauncher.LOGGER.info("Games loaded!");
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
            TutLauncher.LOGGER.error(e.getMessage());
        }

        FileWriter file = new FileWriter(path);
        file.write(jsonObject.toString());
        file.flush();

        TutLauncher.LOGGER.info("Games saved!");
    }
}
