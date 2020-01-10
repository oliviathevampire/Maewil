package io.github.vampirestudios.tdg.utils.set.part;

import com.google.gson.JsonElement;

import java.io.DataInput;

public interface IPartFactory<T extends DataPart> {

    T parse(JsonElement element) throws Exception;

    T parse(DataInput stream) throws Exception;

    default int getPriority() {
        return 0;
    }
}