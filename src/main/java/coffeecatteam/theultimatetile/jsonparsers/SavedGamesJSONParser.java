package coffeecatteam.theultimatetile.jsonparsers;

import coffeecatteam.coffeecatutils.io.FileUtils;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.utils.iinterface.JsonLoader;
import coffeecatteam.theultimatetile.utils.iinterface.JsonSaver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SavedGamesJSONParser implements JsonLoader, JsonSaver {

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
    public void loadJson() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path));

        for (int i = 0; i < 3; i++) {
            String tag = "save_" + i;
            if (jsonObject.containsKey(tag)) {
                JSONObject save = (JSONObject) jsonObject.get(tag);
                String data = String.valueOf(save.get("saved")) + ":" + save.get("name");
                GAMES.set(i, data);
            }
        }

        TutLauncher.LOGGER.info("Games loaded!");
    }

    @Override
    public void saveJson() throws IOException {
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
        file.write(jsonObject.toJSONString());
        file.flush();

        TutLauncher.LOGGER.info("Games saved!");
    }
}
