package coffeecatteam.theultimatetile.tags;

import coffeecatteam.theultimatetile.tags.primitive.TagDouble;
import coffeecatteam.theultimatetile.tags.primitive.TagFloat;
import coffeecatteam.theultimatetile.tags.primitive.TagInt;
import coffeecatteam.theultimatetile.tags.supers.TagBase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author CoffeeCatRailway
 * Created: 16/12/2018
 */
public class JsonToTag {
    private static final Pattern DOUBLE_PATTERN_NOSUFFIX = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
    private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
    private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
    private final String string;
    private int cursor;

    public static TagCompound getTagFromJson(String jsonString) throws TagException {
        return (new JsonToTag(jsonString)).readSingleStruct();
    }

    TagCompound readSingleStruct() throws TagException {
        TagCompound nbttagcompound = this.readStruct();
        this.skipWhitespace();

        if (this.canRead()) {
            ++this.cursor;
            throw this.exception("Trailing data found");
        } else {
            return nbttagcompound;
        }
    }

    JsonToTag(String stringIn) {
        this.string = stringIn;
    }

    protected String readKey() throws TagException {
        this.skipWhitespace();

        if (!this.canRead()) {
            throw this.exception("Expected key");
        } else {
            return this.peek() == '"' ? this.readQuotedString() : this.readString();
        }
    }

    private TagException exception(String message) {
        return new TagException(message, this.string, this.cursor);
    }

    protected TagBase readTypedValue() throws TagException {
        this.skipWhitespace();

        if (this.peek() == '"') {
            return new TagString(this.readQuotedString());
        } else {
            String s = this.readString();

            if (s.isEmpty()) {
                throw this.exception("Expected value");
            } else {
                return this.type(s);
            }
        }
    }

    private TagBase type(String stringIn) {
        try {
            if (FLOAT_PATTERN.matcher(stringIn).matches()) {
                return new TagFloat(Float.parseFloat(stringIn.substring(0, stringIn.length() - 1)));
            }

            if (INT_PATTERN.matcher(stringIn).matches()) {
                return new TagInt(Integer.parseInt(stringIn));
            }

            if (DOUBLE_PATTERN.matcher(stringIn).matches()) {
                return new TagDouble(Double.parseDouble(stringIn.substring(0, stringIn.length() - 1)));
            }

            if (DOUBLE_PATTERN_NOSUFFIX.matcher(stringIn).matches()) {
                return new TagDouble(Double.parseDouble(stringIn));
            }

            if ("true".equalsIgnoreCase(stringIn)) {
                return new TagBoolean(true);
            }

            if ("false".equalsIgnoreCase(stringIn)) {
                return new TagBoolean(false);
            }
        } catch (NumberFormatException var3) {
            ;
        }

        return new TagString(stringIn);
    }

    private String readQuotedString() throws TagException {
        int i = ++this.cursor;
        StringBuilder stringbuilder = null;
        boolean flag = false;

        while (this.canRead()) {
            char c0 = this.pop();

            if (flag) {
                if (c0 != '\\' && c0 != '"') {
                    throw this.exception("Invalid escape of '" + c0 + "'");
                }

                flag = false;
            } else {
                if (c0 == '\\') {
                    flag = true;

                    if (stringbuilder == null) {
                        stringbuilder = new StringBuilder(this.string.substring(i, this.cursor - 1));
                    }

                    continue;
                }

                if (c0 == '"') {
                    return stringbuilder == null ? this.string.substring(i, this.cursor - 1) : stringbuilder.toString();
                }
            }

            if (stringbuilder != null) {
                stringbuilder.append(c0);
            }
        }

        throw this.exception("Missing termination quote");
    }

    private String readString() {
        int i;

        for (i = this.cursor; this.canRead() && this.isAllowedInKey(this.peek()); ++this.cursor) {
            ;
        }

        return this.string.substring(i, this.cursor);
    }

    protected TagBase readValue() throws TagException {
        this.skipWhitespace();

        if (!this.canRead()) {
            throw this.exception("Expected value");
        } else {
            char c0 = this.peek();

            if (c0 == '{') {
                return this.readStruct();
            } else {
                return c0 == '[' ? this.readList() : this.readTypedValue();
            }
        }
    }

    protected TagBase readList() throws TagException {
        return this.canRead(2) ? this.readArrayTag() : this.readListTag();
    }

    protected TagCompound readStruct() throws TagException {
        this.expect('{');
        TagCompound nbttagcompound = new TagCompound();
        this.skipWhitespace();

        while (this.canRead() && this.peek() != '}') {
            String s = this.readKey();

            if (s.isEmpty()) {
                throw this.exception("Expected non-empty key");
            }

            this.expect(':');
            nbttagcompound.setTag(s, this.readValue());

            if (!this.hasElementSeparator()) {
                break;
            }

            if (!this.canRead()) {
                throw this.exception("Expected key");
            }
        }

        this.expect('}');
        return nbttagcompound;
    }

    private TagBase readListTag() throws TagException {
        this.expect('[');
        this.skipWhitespace();

        if (!this.canRead()) {
            throw this.exception("Expected value");
        } else {
            TagList nbttaglist = new TagList();
            int i = -1;

            while (this.peek() != ']') {
                TagBase nbtbase = this.readValue();
                int j = nbtbase.getId();

                if (i < 0) {
                    i = j;
                } else if (j != i) {
                    throw this.exception("Unable to insert " + TagBase.getTagTypeName(j) + " into ListTag of type " + TagBase.getTagTypeName(i));
                }

                nbttaglist.appendTag(nbtbase);

                if (!this.hasElementSeparator()) {
                    break;
                }

                if (!this.canRead()) {
                    throw this.exception("Expected value");
                }
            }

            this.expect(']');
            return nbttaglist;
        }
    }

    private TagBase readArrayTag() throws TagException {
        this.expect('[');
        this.pop();
        this.skipWhitespace();

        if (!this.canRead()) {
            throw this.exception("Expected value");
        } else {
            return new TagList(readArray());
        }
    }

    private List<TagBase> readArray() throws TagException {
        List<TagBase> list = new ArrayList<>();
        StringBuilder json = new StringBuilder("{\"array\":[\"");

        while (this.peek() != ']') {
            json.append(this.peek());

            if (this.hasElementSeparator()) {
                if (!this.canRead()) {
                    throw this.exception("Expected value");
                }

                continue;
            }
            this.pop();
        }
        json.append("]}");

        this.expect(']');

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(json.toString());
            JSONArray array = (JSONArray) jsonObj.get("array");
            for (int i = 0; i < array.size(); i++) list.add(new TagString((String) array.get(i)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void skipWhitespace() {
        while (this.canRead() && Character.isWhitespace(this.peek())) {
            ++this.cursor;
        }
    }

    private boolean hasElementSeparator() {
        this.skipWhitespace();

        if (this.canRead() && this.peek() == ',') {
            ++this.cursor;
            this.skipWhitespace();
            return true;
        } else {
            return false;
        }
    }

    private void expect(char expected) throws TagException {
        this.skipWhitespace();
        boolean flag = this.canRead();

        if (flag && this.peek() == expected) {
            ++this.cursor;
        } else {
            throw new TagException("Expected '" + expected + "' but got '" + (flag ? this.peek() : "<EOF>") + "'", this.string, this.cursor + 1);
        }
    }

    protected boolean isAllowedInKey(char charIn) {
        return charIn >= '0' && charIn <= '9' || charIn >= 'A' && charIn <= 'Z' || charIn >= 'a' && charIn <= 'z' || charIn == '_' || charIn == '-' || charIn == '.' || charIn == '+';
    }

    private boolean canRead(int amt) {
        return this.cursor + amt < this.string.length();
    }

    boolean canRead() {
        return this.canRead(0);
    }

    private char peek(int amt) {
        return this.string.charAt(this.cursor + amt);
    }

    private char peek() {
        return this.peek(0);
    }

    private char pop() {
        return this.string.charAt(this.cursor++);
    }
}
