package coffeecatteam.theultimatetile.jsonparsers;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.jsonparsers.iinterface.IJSONLoader;
import coffeecatteam.theultimatetile.jsonparsers.iinterface.IJSONSaver;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SavedGamesJSONParser implements IJSONLoader, IJSONSaver {

    private String path = "./saves/saved_games.json";
    protected TheUltimateTile theUltimateTile;

    public static List<String> GAMES = new ArrayList<>();
    static {
        GAMES.add("false");
        GAMES.add("false");
        GAMES.add("false");
    }

    public SavedGamesJSONParser(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
    }

    @Override
    public void load() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path));

        JSONArray games = (JSONArray) jsonObject.get("games");
        GAMES.set(0, String.valueOf(games.get(0)));
        GAMES.set(1, String.valueOf(games.get(1)));
        GAMES.set(2, String.valueOf(games.get(2)));

        Logger.print("Games loaded!");
    }

    @Override
    public void save() throws IOException {
        JSONObject jsonObject = new JSONObject();

        JSONArray games = new JSONArray();
        games.add(0, GAMES.get(0));
        games.add(1, GAMES.get(1));
        games.add(2, GAMES.get(2));
        jsonObject.put("games", games);

        FileWriter file = new FileWriter(path);
        file.write(jsonObject.toJSONString());
        file.flush();

        Logger.print("Games saved!");
    }

    public List<String> getGames() {
        return GAMES;
    }
}
