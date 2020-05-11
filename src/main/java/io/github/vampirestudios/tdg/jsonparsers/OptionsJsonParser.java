package io.github.vampirestudios.tdg.jsonparsers;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.io.FileUtils;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
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
    private int skinVariant;

    public OptionsJsonParser(String path) {
        this.path = path;
    }

    @Override
    public void load() throws IOException, ParseException {
        MaewilLauncher.LOGGER.info("Loading options!");
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
        MaewilLauncher.LOGGER.info("Control options loaded!");

        DEBUG_MODE = Boolean.parseBoolean(jsonObject.get("debugMode").toString());
        FPS_COUNTER = Boolean.parseBoolean(jsonObject.get("fpsCounter").toString());
        VSYNC = Boolean.parseBoolean(jsonObject.get("vSync").toString());
        MaewilLauncher.LOGGER.info("True/False options loaded!");

        JSONObject sounds = (JSONObject) jsonObject.get("sounds");
        volumeMusic = NumberUtils.parseFloat(sounds.get("volumeMusic"));
        volumePassive = NumberUtils.parseFloat(sounds.get("volumePassive"));
        volumeHostile = NumberUtils.parseFloat(sounds.get("volumeHostile"));
        volumePlayer = NumberUtils.parseFloat(sounds.get("volumePlayer"));
        volumeOther = NumberUtils.parseFloat(sounds.get("volumeOther"));
        MaewilLauncher.LOGGER.info("Sound options loaded!");

        JSONObject character = (JSONObject) jsonObject.get("character");
        skinVariant = NumberUtils.parseInt(character.get("skinVariant"));
        MaewilLauncher.LOGGER.info("Character options loaded!");

        MaewilLauncher.LOGGER.info("All options loaded!");
    }

    @Override
    public void save() throws IOException {
        MaewilLauncher.LOGGER.info("Saving options!");
        JSONObject jsonObject = new JSONObject();

        JSONObject controls = new JSONObject();
        for (String jsonId : CONTROLS.keySet()) {
            JSONObject key = new JSONObject();
            key.put("id", CONTROLS.get(jsonId).getKeyCode());
            key.put("char", CONTROLS.get(jsonId).getId());
            key.put("description", CONTROLS.get(jsonId).getDescription());
            controls.put(jsonId, key);
        }
        jsonObject.put("controls", controls);
        MaewilLauncher.LOGGER.info("Control options saved!");

        jsonObject.put("debugMode", DEBUG_MODE);
        jsonObject.put("fpsCounter", FPS_COUNTER);
        jsonObject.put("vSync", VSYNC);
        MaewilLauncher.LOGGER.info("True/False options saved!");

        JSONObject sounds = new JSONObject();
        sounds.put("volumeMusic", String.valueOf(volumeMusic));
        sounds.put("volumePassive", String.valueOf(volumePassive));
        sounds.put("volumeHostile", String.valueOf(volumeHostile));
        sounds.put("volumePlayer", String.valueOf(volumePlayer));
        sounds.put("volumeOther", String.valueOf(volumeOther));
        jsonObject.put("sounds", sounds);
        MaewilLauncher.LOGGER.info("Sound options saved!");

        JSONObject character = new JSONObject();
        character.put("skinVariant", String.valueOf(skinVariant));
        jsonObject.put("character", character);
        MaewilLauncher.LOGGER.info("Character options saved!");

        FileWriter file = new FileWriter(path);
        file.write(jsonObject.toJSONString());
        file.flush();

        MaewilLauncher.LOGGER.info("All options saved!");
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

    public void setSkinVariant(int skinVariant) {
        this.skinVariant = skinVariant;
    }

    public int getSkinVariant() {
        return skinVariant;
    }

}