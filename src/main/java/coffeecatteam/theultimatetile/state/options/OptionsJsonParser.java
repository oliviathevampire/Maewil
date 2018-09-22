package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OptionsJsonParser {

    private String path;
    private TheUltimateTile theUltimateTile;

    private Map<String, Keybind> CONTROLS = new HashMap<>();
    private boolean DEBUG_MODE, FPS_COUNTER;

    public OptionsJsonParser(String path, TheUltimateTile theUltimateTile) {
        this.path = path;
        this.theUltimateTile = theUltimateTile;
    }

    public void loadOptions() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(Utils.loadFileOutSideJar(path));

        JSONObject controls = (JSONObject) jsonObject.get("controls");
        for (Object jsonId : controls.keySet()) {
            JSONObject k = (JSONObject) controls.get(jsonId.toString());
            int key_id = Utils.parseInt(k.get("key_id").toString());
            String key_char = k.get("key_char").toString();
            CONTROLS.put(jsonId.toString(), new Keybind(key_char, key_id));
        }

        DEBUG_MODE = Boolean.valueOf(jsonObject.get("DEBUG_MODE").toString());
        FPS_COUNTER = Boolean.valueOf(jsonObject.get("FPS_COUNTER").toString());

        Logger.print("Options loaded!");
    }

    public void saveOptions() {
        JSONObject jsonObject = new JSONObject();

        JSONObject controls = new JSONObject();
        for (String jsonId : CONTROLS.keySet()) {
            JSONObject k = new JSONObject();
            k.put("key_id", String.valueOf(CONTROLS.get(jsonId).getKeyCode()));
            k.put("key_char", CONTROLS.get(jsonId).getId());
            controls.put(jsonId, k);
        }
        jsonObject.put("controls", controls);

        jsonObject.put("DEBUG_MODE", DEBUG_MODE);
        jsonObject.put("FPS_COUNTER", FPS_COUNTER);

        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.print("Options saved!");
    }

    public Map<String, Keybind> CONTROLS() {
        return CONTROLS;
    }

    public void setCONTROLS(Map<String, Keybind> CONTROLS) {
        this.CONTROLS = CONTROLS;
    }

    public boolean DEBUG_MODE() {
        return DEBUG_MODE;
    }

    public void setDEBUG_MODE(boolean DEBUG_MODE) {
        this.DEBUG_MODE = DEBUG_MODE;
    }

    public boolean FPS_COUNTER() {
        return FPS_COUNTER;
    }

    public void setFPS_COUNTER(boolean FPS_COUNTER) {
        this.FPS_COUNTER = FPS_COUNTER;
    }
}
