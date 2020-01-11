package io.github.vampirestudios.tdg.screen.options.controls;

public class Keybind {

    /*
     * KEYS
     */
    public static final String Z = "z";
    public static final String R = "r";
    public static final String E = "e";
    public static final String Q = "q";
    public static final String X = "x";

    public static final String W = "w";
    public static final String A = "a";
    public static final String S = "s";
    public static final String D = "d";

    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";

    public static final String CONTROL = "control";
    public static final String SPACE = "space";
    public static final String ESCAPE = "escape";

    public static final String F2 = "F2";
    public static final String F11 = "F11";

    /*
     * CLASS
     */
    private String id;
    private int keyCode;
    private String description;

    public Keybind(String id, int keyCode, String description) {
        this.id = id;
        this.keyCode = keyCode;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public String getDescription() {
        return description;
    }
}
