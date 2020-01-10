package io.github.vampirestudios.tdg.utils.set.part;

import com.google.gson.JsonElement;

import java.io.DataOutput;

public abstract class DataPart<T> {

    public abstract T get();

    public abstract void write(DataOutput stream) throws Exception;

    public abstract JsonElement write() throws Exception;

    public abstract IPartFactory<? extends DataPart<T>> getFactory();

}