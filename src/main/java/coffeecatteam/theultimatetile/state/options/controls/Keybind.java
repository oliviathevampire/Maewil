package coffeecatteam.theultimatetile.state.options.controls;

public class Keybind {

    /*
     * KEYS
     */
    public static final String Z = "Z";
    public static final String R = "R";
    public static final String E = "E";
    public static final String Q = "Q";
    public static final String X = "X";

    public static final String W = "W";
    public static final String A = "A";
    public static final String S = "S";
    public static final String D = "D";

    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";

    public static final String CONTROL = "CONTROL";
    public static final String SPACE = "SPACE";
    public static final String ESCAPE = "ESCAPE";

    /*
     * CLASS
     */
    private String id;
    private int keyCode;

    public Keybind(String id, int keyCode) {
        this.id = id;
        this.keyCode = keyCode;
    }

    public String getId() {
        return id;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
