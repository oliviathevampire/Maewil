package io.github.vampirestudios.tdg.utils.set.part;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.utils.set.ModBasedDataSet;

import java.io.DataInput;
import java.io.DataOutput;

public final class PartModBasedDataSet extends BasicDataPart<ModBasedDataSet> {

    public static final IPartFactory<PartModBasedDataSet> FACTORY = new IPartFactory<PartModBasedDataSet>() {
        @Override
        public PartModBasedDataSet parse(JsonElement element) {
            if (element.isJsonObject()) {
                try {
                    ModBasedDataSet set = new ModBasedDataSet();
//                    RockBottomAPI.getApiHandler().readDataSet(element.getAsJsonObject(), set);
                    return new PartModBasedDataSet(set);
                } catch (Exception ignored) {
                }
            }
            return null;
        }

        @Override
        public PartModBasedDataSet parse(DataInput stream) throws Exception {
            ModBasedDataSet data = new ModBasedDataSet();
//            RockBottomAPI.getApiHandler().readDataSet(stream, data);
            return new PartModBasedDataSet(data);
        }

        @Override
        public int getPriority() {
            return 100;
        }
    };

    public PartModBasedDataSet(ModBasedDataSet data) {
        super(data);
    }

    @Override
    public void write(DataOutput stream) throws Exception {
//        RockBottomAPI.getApiHandler().writeDataSet(stream, this.data);
    }

    @Override
    public JsonElement write() throws Exception {
        JsonObject object = new JsonObject();
//        RockBottomAPI.getApiHandler().writeDataSet(object, this.data);
        return object;
    }

    @Override
    public IPartFactory<? extends DataPart<ModBasedDataSet>> getFactory() {
        return FACTORY;
    }
}