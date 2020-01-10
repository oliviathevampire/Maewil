package io.github.vampirestudios.tdg.settings;

import com.google.gson.JsonObject;

import java.io.File;

public interface IJsonSettings {

    void load(JsonObject object);

    void save(JsonObject object);

    File getSettingsFile(IDataManager manager);

    String getName();

    default void save() {
//        RockBottomAPI.getGame().getDataManager().saveSettings(this);
    }

    default void load() {
//        RockBottomAPI.getGame().getDataManager().loadSettings(this);
    }

    default void set(JsonObject object, String name, String val) {
        object.addProperty(name, val);
    }

    default void set(JsonObject object, String name, Character val) {
        object.addProperty(name, val);
    }

    default void set(JsonObject object, String name, Boolean val) {
        object.addProperty(name, val);
    }

    default void set(JsonObject object, String name, Number val) {
        object.addProperty(name, val);
    }

    default int get(JsonObject object, String name, int def) {
        return object.has(name) ? object.get(name).getAsInt() : def;
    }

    default boolean get(JsonObject object, String name, boolean def) {
        return object.has(name) ? object.get(name).getAsBoolean() : def;
    }

    default float get(JsonObject object, String name, float def) {
        return object.has(name) ? object.get(name).getAsFloat() : def;
    }

    default String get(JsonObject object, String name, String def) {
        return object.has(name) ? object.get(name).getAsString() : def;
    }
}