package io.github.vampirestudios.tdg.utils.set.part;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.UUID;

public final class PartUniqueId extends BasicDataPart<UUID> {

    public static final IPartFactory<PartUniqueId> FACTORY = new IPartFactory<PartUniqueId>() {
        @Override
        public PartUniqueId parse(JsonElement element) {
            if (element.isJsonPrimitive()) {
                JsonPrimitive prim = element.getAsJsonPrimitive();
                if (prim.isString()) {
                    try {
                        return new PartUniqueId(UUID.fromString(prim.getAsString()));
                    } catch (Exception ignored) {
                    }
                }
            }
            return null;
        }

        @Override
        public PartUniqueId parse(DataInput stream) throws Exception {
            return new PartUniqueId(new UUID(stream.readLong(), stream.readLong()));
        }

        @Override
        public int getPriority() {
            return 50;
        }
    };

    public PartUniqueId(UUID data) {
        super(data);
    }

    @Override
    public void write(DataOutput stream) throws Exception {
        stream.writeLong(this.data.getMostSignificantBits());
        stream.writeLong(this.data.getLeastSignificantBits());
    }

    @Override
    public JsonElement write() {
        return new JsonPrimitive(this.data.toString());
    }

    @Override
    public IPartFactory<? extends DataPart<UUID>> getFactory() {
        return FACTORY;
    }
}