package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Keybinds {

    private static Map<String, Keybinds> KEYBINDS = new HashMap<>();

    /*
     * KEYS
     */
    public static final Keybinds Z = new Keybinds("Z", KeyEvent.VK_Z);
    public static final Keybinds R = new Keybinds("R", KeyEvent.VK_R);
    public static final Keybinds E = new Keybinds("E", KeyEvent.VK_E);
    public static final Keybinds Q = new Keybinds("Q", KeyEvent.VK_Q);

    public static final Keybinds W = new Keybinds("W", KeyEvent.VK_W);
    public static final Keybinds A = new Keybinds("A", KeyEvent.VK_A);
    public static final Keybinds S = new Keybinds("S", KeyEvent.VK_S);
    public static final Keybinds D = new Keybinds("D", KeyEvent.VK_D);

    public static final Keybinds ONE = new Keybinds("1", KeyEvent.VK_1);
    public static final Keybinds TWO = new Keybinds("2", KeyEvent.VK_2);
    public static final Keybinds THREE = new Keybinds("3", KeyEvent.VK_3);

    public static final Keybinds CONTROL = new Keybinds("CONTROL", KeyEvent.VK_CONTROL);
    public static final Keybinds SPACE = new Keybinds("SPACE", KeyEvent.VK_SPACE);
    public static final Keybinds ESCAPE = new Keybinds("ESCAPE", KeyEvent.VK_ESCAPE);

    /*
     * CLASS
     */
    private String id;
    private int keyCode;

    public Keybinds(String id, int keyCode) {
        this.id = id;
        this.keyCode = keyCode;
        KEYBINDS.put(id, this);
    }

    public String getId() {
        return id;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public static void save() {
        try {
            File file = new File("./options.txt");
            file.createNewFile();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

            for (Keybinds keybind : KEYBINDS.values()) {
                bw.write(keybind.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.print("Saved keybindings!");
    }

    public static void load() {
        try {
            File file = new File("./options.txt");
            if (!file.exists())
                save();

            BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            String line;
            while ((line = br.readLine()) != null) {
                String id = line.split(":")[0];
                String keyCode = line.split(":")[1];

                for (Keybinds keybind : KEYBINDS.values())
                    if (keybind.getId().equals(id))
                        keybind.setKeyCode(Utils.parseInt(keyCode));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.print("Loaded keybindings!");
    }

    @Override
    public String toString() {
        return id + ":" + keyCode + ":" + KeyStroke.getKeyStroke(keyCode, 0).toString().substring(8);
    }
}
