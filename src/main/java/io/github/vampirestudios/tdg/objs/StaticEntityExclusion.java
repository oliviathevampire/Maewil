package io.github.vampirestudios.tdg.objs;

public enum StaticEntityExclusion {

    PLAYER("entity.player"),
    ZOMBIE("\"Zombie\""),
    SKELETON("\"Skeleton\"");

    private String name;

    StaticEntityExclusion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}