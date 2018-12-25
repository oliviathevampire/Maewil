package coffeecatteam.theultimatetile.game.tags;

public class TagException extends Exception {

    public TagException(String message, String json, int index) {
        super(message + " at: " + slice(json, index));
    }

    private static String slice(String json, int index) {
        StringBuilder stringbuilder = new StringBuilder();
        int i = Math.min(json.length(), index);

        if (i > 35) {
            stringbuilder.append("...");
        }

        stringbuilder.append(json.substring(Math.max(0, i - 35), i));
        stringbuilder.append("<--[HERE]");
        return stringbuilder.toString();
    }
}