package coffeecatteam.theultimatetile.jsonparsers;

import coffeecatteam.coffeecatutils.io.FileUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.utils.iinterface.IJSONLoader;
import coffeecatteam.theultimatetile.utils.iinterface.IJSONSaver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SavedGamesJSONParser implements IJSONLoader, IJSONSaver {

    private String path = "./saves/saved_games.json";
    protected Engine engine;

    public static int SAVE_CAPACITY = 3;
    public static final String DEFAULT_NAME = "UNSAVED";
    public static List<String> GAMES = new ArrayList<>();

    static {
        for (int i = 0; i < SAVE_CAPACITY; i++)
            GAMES.add("false:" + DEFAULT_NAME);
    }

    public SavedGamesJSONParser(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void load() throws IOException, ParseException {
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

        engine.getLogger().print("Games loaded!");
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
            engine.getLogger().print(e.getMessage());
        }

        FileWriter file = new FileWriter(path);
        file.write(jsonObject.toJSONString());
        file.flush();

        engine.getLogger().print("Games saved!");
    }
}
