package io.github.vampirestudios.tdg.tags;

import io.github.vampirestudios.tdg.tags.primitive.DoubleTag;
import io.github.vampirestudios.tdg.tags.primitive.FloatTag;
import io.github.vampirestudios.tdg.tags.primitive.IntTag;
import io.github.vampirestudios.tdg.tags.supers.BaseTag;
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

    public static CompoundTag getTagFromJson(String jsonString) throws TagException {
        return (new JsonToTag(jsonString)).readSingleStruct();
    }

    CompoundTag readSingleStruct() throws TagException {
        CompoundTag nbttagcompound = this.readStruct();
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

    protected BaseTag readTypedValue() throws TagException {
        this.skipWhitespace();

        if (this.peek() == '"') {
            return new StringTag(this.readQuotedString());
        } else {
            String s = this.readString();

            if (s.isEmpty()) {
                throw this.exception("Expected value");
            } else {
                return this.type(s);
            }
        }
    }

    private BaseTag type(String stringIn) {
        try {
            if (FLOAT_PATTERN.matcher(stringIn).matches()) {
                return new FloatTag(Float.parseFloat(stringIn.substring(0, stringIn.length() - 1)));
            }

            if (INT_PATTERN.matcher(stringIn).matches()) {
                return new IntTag(Integer.parseInt(stringIn));
            }

            if (DOUBLE_PATTERN.matcher(stringIn).matches()) {
                return new DoubleTag(Double.parseDouble(stringIn.substring(0, stringIn.length() - 1)));
            }

            if (DOUBLE_PATTERN_NOSUFFIX.matcher(stringIn).matches()) {
                return new DoubleTag(Double.parseDouble(stringIn));
            }

            if ("true".equalsIgnoreCase(stringIn)) {
                return new BooleanTag(true);
            }

            if ("false".equalsIgnoreCase(stringIn)) {
                return new BooleanTag(false);
            }
        } catch (NumberFormatException var3) {
            ;
        }

        return new StringTag(stringIn);
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

    protected BaseTag readValue() throws TagException {
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

    protected BaseTag readList() throws TagException {
        return this.canRead(2) ? this.readArrayTag() : this.readListTag();
    }

    protected CompoundTag readStruct() throws TagException {
        this.expect('{');
        CompoundTag nbttagcompound = new CompoundTag();
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

    private BaseTag readListTag() throws TagException {
        this.expect('[');
        this.skipWhitespace();

        if (!this.canRead()) {
            throw this.exception("Expected value");
        } else {
            ListTag nbttaglist = new ListTag();
            int i = -1;

            while (this.peek() != ']') {
                BaseTag nbtbase = this.readValue();
                int j = nbtbase.getId();

                if (i < 0) {
                    i = j;
                } else if (j != i) {
                    throw this.exception("Unable to insert " + BaseTag.getTagTypeName(j) + " into ListTag of type " + BaseTag.getTagTypeName(i));
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

    private BaseTag readArrayTag() throws TagException {
        this.expect('[');
        this.pop();
        this.skipWhitespace();

        if (!this.canRead()) {
            throw this.exception("Expected value");
        } else {
            return new ListTag(readArray());
        }
    }

    private List<BaseTag> readArray() throws TagException {
        List<BaseTag> list = new ArrayList<>();
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
            for (int i = 0; i < array.size(); i++) list.add(new StringTag((String) array.get(i)));
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
