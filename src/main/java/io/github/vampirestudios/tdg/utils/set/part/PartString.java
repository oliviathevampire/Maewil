package io.github.vampirestudios.tdg.utils.set.part;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.io.DataInput;
import java.io.DataOutput;

public final class PartString extends BasicDataPart<String> {

    public static final IPartFactory<PartString> FACTORY = new IPartFactory<PartString>() {
        @Override
        public PartString parse(JsonElement element) {
            if (element.isJsonPrimitive()) {
                JsonPrimitive prim = element.getAsJsonPrimitive();
                if (prim.isString()) {
                    return new PartString(prim.getAsString());
                }
            }
            return null;
        }

        @Override
        public PartString parse(DataInput stream) throws Exception {
            char[] chars = new char[stream.readInt()];
            for (int i = 0; i < chars.length; i++) {
                chars[i] = stream.readChar();
            }
            return new PartString(new String(chars));
        }

        @Override
        public int getPriority() {
            return -50;
        }
    };

    public PartString(String data) {
        super(data);
    }

    @Override
    public void write(DataOutput stream) throws Exception {
        stream.writeInt(this.data.length());

        for (char c : this.data.toCharArray()) {
            stream.writeChar(c);
        }
    }

    @Override
    public JsonElement write() {
        return new JsonPrimitive(this.data);
    }

    @Override
    public IPartFactory<? extends DataPart<String>> getFactory() {
        return FACTORY;
    }
}