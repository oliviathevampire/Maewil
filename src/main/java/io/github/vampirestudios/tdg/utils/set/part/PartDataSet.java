package io.github.vampirestudios.tdg.utils.set.part;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.utils.set.DataSet;

import java.io.DataInput;
import java.io.DataOutput;

public final class PartDataSet extends BasicDataPart<DataSet> {

    public static final IPartFactory<PartDataSet> FACTORY = new IPartFactory<PartDataSet>() {
        @Override
        public PartDataSet parse(JsonElement element) {
            if (element.isJsonObject()) {
                try {
                    DataSet set = new DataSet();
//                    RockBottomAPI.getApiHandler().readDataSet(element.getAsJsonObject(), set);
                    return new PartDataSet(set);
                } catch (Exception ignored) {
                }
            }
            return null;
        }

        @Override
        public PartDataSet parse(DataInput stream) throws Exception {
            DataSet data = new DataSet();
//            RockBottomAPI.getApiHandler().readDataSet(stream, data);
            return new PartDataSet(data);
        }
    };

    public PartDataSet(DataSet data) {
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
    public IPartFactory getFactory() {
        return FACTORY;
    }
}