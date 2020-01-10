package io.github.vampirestudios.tdg.jsonparsers;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.io.FileUtils;
import io.github.vampirestudios.tdg.start.TutLauncher;
import io.github.vampirestudios.tdg.screen.options.controls.Keybind;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONLoader;
import io.github.vampirestudios.tdg.utils.iinterface.IJSONSaver;
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

    private Map<String, Keybind> CONTROLS = new HashMap<>();
    private boolean DEBUG_MODE, FPS_COUNTER, VSYNC;

    private DecimalFormat volumeFormat = new DecimalFormat("#.#");
    private float volumeMusic, volumePassive, volumeHostile, volumePlayer, volumeOther;

    public OptionsJsonParser(String path) {
        this.path = path;
    }

    @Override
    public void load() throws IOException, ParseException {
        TutLauncher.LOGGER.info("Loading options!");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path));

        JSONObject controls = (JSONObject) jsonObject.get("controls");
        for (Object jsonId : controls.keySet()) {
            JSONObject k = (JSONObject) controls.get(jsonId.toString());
            int key_id = NumberUtils.parseInt(k.get("id").toString());
            String key_char = k.get("char").toString();
            String description = k.get("description").toString();
            CONTROLS.put(jsonId.toString(), new Keybind(key_char, key_id, description));
        }
        TutLauncher.LOGGER.info("Control options loaded!");

        DEBUG_MODE = Boolean.parseBoolean(jsonObject.get("debugMode").toString());
        FPS_COUNTER = Boolean.parseBoolean(jsonObject.get("fpsCounter").toString());
        VSYNC = Boolean.parseBoolean(jsonObject.get("vSync").toString());
        TutLauncher.LOGGER.info("True/False options loaded!");

        JSONObject sounds = (JSONObject) jsonObject.get("sounds");
        volumeMusic = NumberUtils.parseFloat(sounds.get("volumeMusic"));
        volumePassive = NumberUtils.parseFloat(sounds.get("volumePassive"));
        volumeHostile = NumberUtils.parseFloat(sounds.get("volumeHostile"));
        volumePlayer = NumberUtils.parseFloat(sounds.get("volumePlayer"));
        volumeOther = NumberUtils.parseFloat(sounds.get("volumeOther"));
        TutLauncher.LOGGER.info("Sound options loaded!");

        TutLauncher.LOGGER.info("All options loaded!");
    }

    @Override
    public void save() throws IOException {
        TutLauncher.LOGGER.info("Saving options!");
        JSONObject jsonObject = new JSONObject();

        JSONObject controls = new JSONObject();
        for (String jsonId : CONTROLS.keySet()) {
            JSONObject key = new JSONObject();
            key.put("key_id", String.valueOf(CONTROLS.get(jsonId).getKeyCode()));
            key.put("key_char", CONTROLS.get(jsonId).getId());
            key.put("description", CONTROLS.get(jsonId).getDescription());
            controls.put(jsonId, key);
        }
        jsonObject.put("controls", controls);
        TutLauncher.LOGGER.info("Control options loaded!");

        jsonObject.put("DEBUG_MODE", DEBUG_MODE);
        jsonObject.put("FPS_COUNTER", FPS_COUNTER);
        jsonObject.put("VSYNC", VSYNC);
        TutLauncher.LOGGER.info("True/False options loaded!");

        JSONObject sounds = new JSONObject();
        sounds.put("volumeMusic", String.valueOf(volumeMusic));
        sounds.put("volumePassive", String.valueOf(volumePassive));
        sounds.put("volumeHostile", String.valueOf(volumeHostile));
        sounds.put("volumePlayer", String.valueOf(volumePlayer));
        sounds.put("volumeOther", String.valueOf(volumeOther));
        jsonObject.put("sounds", sounds);
        TutLauncher.LOGGER.info("Sound options loaded!");

        FileWriter file = new FileWriter(path);
        file.write(jsonObject.toJSONString());
        file.flush();

        TutLauncher.LOGGER.info("All options saved!");
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

    public boolean vSync() {
        return VSYNC;
    }

    public void setVSync(boolean VSYNC) {
        this.VSYNC = VSYNC;
    }

    public float getVolumeMusic() {
        return volumeMusic;
    }

    public void setVolumeMusic(double volumeMusic) {
        this.volumeMusic = NumberUtils.parseFloat(volumeFormat.format(volumeMusic));
    }

    public float getVolumePassive() {
        return volumePassive;
    }

    public void setVolumePassive(double volumePassive) {
        this.volumePassive = NumberUtils.parseFloat(volumeFormat.format(volumePassive));
    }

    public float getVolumeHostile() {
        return volumeHostile;
    }

    public void setVolumeHostile(double volumeHostile) {
        this.volumeHostile = NumberUtils.parseFloat(volumeFormat.format(volumeHostile));
    }

    public float getVolumeOther() {
        return volumeOther;
    }

    public void setVolumeOther(double volumeOther) {
        this.volumeOther = NumberUtils.parseFloat(volumeFormat.format(volumeOther));
    }

    public float getVolumePlayer() {
        return volumePlayer;
    }

    public void setVolumePlayer(float volumePlayer) {
        this.volumePlayer = NumberUtils.parseFloat(volumeFormat.format(volumePlayer));
    }
}
