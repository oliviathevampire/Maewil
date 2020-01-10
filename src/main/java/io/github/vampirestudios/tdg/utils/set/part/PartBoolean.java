package io.github.vampirestudios.tdg.utils.set.part;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.io.DataInput;
import java.io.DataOutput;

public final class PartBoolean extends BasicDataPart<Boolean> {

    public static final IPartFactory<PartBoolean> FACTORY = new IPartFactory<PartBoolean>() {
        @Override
        public PartBoolean parse(JsonElement element) {
            if (element.isJsonPrimitive()) {
                JsonPrimitive prim = element.getAsJsonPrimitive();
                if (prim.isBoolean()) {
                    return new PartBoolean(prim.getAsBoolean());
                }
            }
            return null;
        }

        @Override
        public PartBoolean parse(DataInput stream) throws Exception {
            return new PartBoolean(stream.readBoolean());
        }
    };

    public PartBoolean(Boolean data) {
        super(data);
    }

    @Override
    public void write(DataOutput stream) throws Exception {
        stream.writeBoolean(this.data);
    }

    @Override
    public JsonElement write() {
        return new JsonPrimitive(this.data);
    }

    @Override
    public IPartFactory getFactory() {
        return FACTORY;
    }
}