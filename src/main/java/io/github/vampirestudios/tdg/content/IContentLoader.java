package io.github.vampirestudios.tdg.content;

import com.google.gson.JsonElement;
import io.github.vampirestudios.tdg.content.pack.ContentPack;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.utils.Identifier;
import io.github.vampirestudios.tdg.utils.registry.Registries;

public interface IContentLoader<T extends IContent> {

    default void register() {
        Registries.CONTENT_LOADER_REGISTRY.register(this.getContentIdentifier(), this);
    }

    Identifier getContentIdentifier();

    void loadContent(TutEngine game, Identifier resourceName, String path, JsonElement element, String elementName/*, IMod loadingMod*/, ContentPack pack) throws Exception;

    void disableContent(TutEngine game, Identifier resourceName);

    default boolean dealWithSpecialCases(TutEngine game, String resourceName, String path, JsonElement element, String elementName/*, IMod loadingMod*/, ContentPack pack) {
        return false;
    }

    default void finalize(TutEngine game) {

    }

}
