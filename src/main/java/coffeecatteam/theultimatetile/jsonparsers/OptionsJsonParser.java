package coffeecatteam.theultimatetile.jsonparsers;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.utils.iinterface.IJSONLoader;
import coffeecatteam.theultimatetile.utils.iinterface.IJSONSaver;
import coffeecatteam.theultimatetile.game.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class OptionsJsonParser implements IJSONLoader, IJSONSaver {

    private String path;
    private GameEngine gameEngine;

    private Map<String, Keybind> CONTROLS = new HashMap<>();
    private boolean DEBUG_MODE, FPS_COUNTER;

    private DecimalFormat volumeFormat = new DecimalFormat("#.#");
    private float volumeMusic, volumePassive, volumeHostile, volumePlayer, volumeOther;

    public OptionsJsonParser(String path, GameEngine gameEngine) {
        this.path = path;
        this.gameEngine = gameEngine;
    }

    @Override
    public void load() throws IOException, ParseException {
        Logger.print("\nLoading options!");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path));

        JSONObject controls = (JSONObject) jsonObject.get("controls");
        for (Object jsonId : controls.keySet()) {
            JSONObject k = (JSONObject) controls.get(jsonId.toString());
            int key_id = Utils.parseInt(k.get("key_id").toString());
            String key_char = k.get("key_char").toString();
            CONTROLS.put(jsonId.toString(), new Keybind(key_char, key_id));
        }
        Logger.print("Options [controls] loaded!");

        DEBUG_MODE = Boolean.valueOf(jsonObject.get("DEBUG_MODE").toString());
        FPS_COUNTER = Boolean.valueOf(jsonObject.get("FPS_COUNTER").toString());
        Logger.print("Options [FPS_COUNTER & DEBUG_MODE] loaded!");

        JSONObject sounds = (JSONObject) jsonObject.get("sounds");
        volumeMusic = Utils.parseFloat((String) sounds.get("volumeMusic"));
        volumePassive = Utils.parseFloat((String) sounds.get("volumePassive"));
        volumeHostile = Utils.parseFloat((String) sounds.get("volumeHostile"));
        volumePlayer = Utils.parseFloat((String) sounds.get("volumePlayer"));
        volumeOther = Utils.parseFloat((String) sounds.get("volumeOther"));
        Logger.print("Options [sounds] loaded!");

        Logger.print("Options loaded!\n");
    }

    @Override
    public void save() throws IOException {
        Logger.print("\nSaving options!");
        JSONObject jsonObject = new JSONObject();

        JSONObject controls = new JSONObject();
        for (String jsonId : CONTROLS.keySet()) {
            JSONObject key = new JSONObject();
            key.put("key_id", String.valueOf(CONTROLS.get(jsonId).getKeyCode()));
            key.put("key_char", CONTROLS.get(jsonId).getId());
            controls.put(jsonId, key);
        }
        jsonObject.put("controls", controls);
        Logger.print("Options [controls] saved!");

        jsonObject.put("DEBUG_MODE", DEBUG_MODE);
        jsonObject.put("FPS_COUNTER", FPS_COUNTER);
        Logger.print("Options [FPS_COUNTER & DEBUG_MODE] saved!");

        JSONObject sounds = new JSONObject();
        sounds.put("volumeMusic", String.valueOf(volumeMusic));
        sounds.put("volumePassive", String.valueOf(volumePassive));
        sounds.put("volumeHostile", String.valueOf(volumeHostile));
        sounds.put("volumePlayer", String.valueOf(volumePlayer));
        sounds.put("volumeOther", String.valueOf(volumeOther));
        jsonObject.put("sounds", sounds);
        Logger.print("Options [sounds] saved!");

        FileWriter file = new FileWriter(path);
        file.write(jsonObject.toJSONString());
        file.flush();

        Logger.print("Options saved!");
    }

    public Map<String, Keybind> controls() {
        return CONTROLS;
    }

    public boolean debugMode() {
        return DEBUG_MODE;
    }

    public void setDebugMode(boolean DEBUG_MODE) {
        this.DEBUG_MODE = DEBUG_MODE;
    }

    public boolean fpsCounter() {
        return FPS_COUNTER;
    }

    public void setFpsCounter(boolean FPS_COUNTER) {
        this.FPS_COUNTER = FPS_COUNTER;
    }

    public float getVolumeMusic() {
        return volumeMusic;
    }

    public void setVolumeMusic(double volumeMusic) {
        this.volumeMusic = Utils.parseFloat(volumeFormat.format(volumeMusic));
    }

    public float getVolumePassive() {
        return volumePassive;
    }

    public void setVolumePassive(double volumePassive) {
        this.volumePassive = Utils.parseFloat(volumeFormat.format(volumePassive));
    }

    public float getVolumeHostile() {
        return volumeHostile;
    }

    public void setVolumeHostile(double volumeHostile) {
        this.volumeHostile = Utils.parseFloat(volumeFormat.format(volumeHostile));
    }

    public float getVolumeOther() {
        return volumeOther;
    }

    public void setVolumeOther(double volumeOther) {
        this.volumeOther = Utils.parseFloat(volumeFormat.format(volumeOther));
    }

    public float getVolumePlayer() {
        return volumePlayer;
    }

    public void setVolumePlayer(float volumePlayer) {
        this.volumePlayer = Utils.parseFloat(volumeFormat.format(volumePlayer));
    }
}
