package io.github.vampirestudios.tdg.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.content.pack.ContentPack;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

public final class ContentPackSettings implements IJsonSettings {

    private final List<SettingsEntry> enabledPacks = new ArrayList<>();
    private final SettingsEntry defaultPack = new SettingsEntry(ContentPack.DEFAULT_PACK_ID, 0);

    @Override
    public void load(JsonObject object) {
        this.enabledPacks.clear();

        if (object.has("packs")) {
            JsonArray array = object.get("packs").getAsJsonArray();
            for (JsonElement element : array) {
                JsonObject entryObj = element.getAsJsonObject();
                this.enabledPacks.add(new SettingsEntry(entryObj.get("id").getAsString(), entryObj.get("prio").getAsInt()));
            }
        }
    }

    @Override
    public void save(JsonObject object) {
        JsonArray array = new JsonArray();
        for (SettingsEntry entry : this.enabledPacks) {
            JsonObject entryObj = new JsonObject();
            entryObj.addProperty("id", entry.id);
            entryObj.addProperty("prio", entry.priority);
            array.add(entryObj);
        }
        object.add("packs", array);
    }

    @Override
    public File getSettingsFile(IDataManager manager) {
        return manager.getContentPackSettingsFile();
    }

    @Override
    public String getName() {
        return "Content pack settings";
    }

    public boolean isDisabled(String id) {
        return this.getEntry(id) == null;
    }

    public int getPriority(String id) {
        SettingsEntry entry = this.getEntry(id);
        return entry == null ? Integer.MIN_VALUE : entry.priority;
    }

    private SettingsEntry getEntry(String id) {
        if (id.equals(this.defaultPack.id)) {
            return this.defaultPack;
        } else {
            for (SettingsEntry entry : this.enabledPacks) {
                if (entry.id.equals(id)) {
                    return entry;
                }
            }
            return null;
        }
    }

    public Comparator<ContentPack> getPriorityComparator() {
        return Comparator.comparingInt((ToIntFunction<ContentPack>) value -> this.getPriority(value.getId())).reversed();
    }

    public void setEnabledPriority(String id, int priority) {
        SettingsEntry entry = this.getEntry(id);
        if (entry == null) {
            this.enabledPacks.add(new SettingsEntry(id, priority));
        } else if (!id.equals(this.defaultPack.id)) {
            entry.priority = priority;
        }
    }

    public void setDisabled(String id) {
        if (!id.equals(this.defaultPack.id)) {
            this.enabledPacks.remove(this.getEntry(id));
        }
    }

    private static class SettingsEntry {

        public final String id;
        public int priority;

        public SettingsEntry(String id, int priority) {
            this.id = id;
            this.priority = priority;
        }
    }
}