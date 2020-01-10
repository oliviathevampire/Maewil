package io.github.vampirestudios.tdg.content.pack;

public final class ContentPack {

    public static final String DEFAULT_PACK_ID = "default";

    private final String id;
    private final String name;
    private final String version;
    private final String[] author;
    private final String description;

    public ContentPack(String id, String name, String version, String[] author, String description) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.author = author;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public String[] getAuthor() {
        return this.author;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDefault() {
        return this.id.equals(DEFAULT_PACK_ID);
    }
}