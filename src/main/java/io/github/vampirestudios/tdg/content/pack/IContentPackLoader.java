package io.github.vampirestudios.tdg.content.pack;

import io.github.vampirestudios.tdg.settings.ContentPackSettings;

import java.io.File;
import java.util.List;

public interface IContentPackLoader {

    void load(File dir);

    List<ContentPack> getAllPacks();

    List<ContentPack> getActivePacks();

    List<ContentPack> getDisabledPacks();

    ContentPackSettings getPackSettings();

    ContentPack getPack(String id);
}
