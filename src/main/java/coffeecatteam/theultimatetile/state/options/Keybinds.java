package coffeecatteam.theultimatetile.state.options;

import java.awt.event.KeyEvent;

public class Keybinds {

    /*
     * KEYS
     */
    public static final Keybinds Z = new Keybinds(KeyEvent.VK_Z);
    public static final Keybinds R = new Keybinds(KeyEvent.VK_R);
    public static final Keybinds E = new Keybinds(KeyEvent.VK_E);
    public static final Keybinds Q = new Keybinds(KeyEvent.VK_Q);

    public static final Keybinds W = new Keybinds(KeyEvent.VK_W);
    public static final Keybinds A = new Keybinds(KeyEvent.VK_A);
    public static final Keybinds S = new Keybinds(KeyEvent.VK_S);
    public static final Keybinds D = new Keybinds(KeyEvent.VK_D);

    public static final Keybinds ONE = new Keybinds(KeyEvent.VK_1);
    public static final Keybinds TWO = new Keybinds(KeyEvent.VK_2);
    public static final Keybinds THREE = new Keybinds(KeyEvent.VK_3);

    public static final Keybinds CONTROL = new Keybinds(KeyEvent.VK_CONTROL);
    public static final Keybinds SPACE = new Keybinds(KeyEvent.VK_SPACE);
    public static final Keybinds ESCAPE = new Keybinds(KeyEvent.VK_ESCAPE);

    /*
     * CLASS
     */
    private int key;

    public Keybinds(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
