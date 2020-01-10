package io.github.vampirestudios.tdg.settings;

import java.io.File;

public interface IDataManager {

    File getGameDir();

    File getModsDir();

    File getContentPacksDir();

    File getWorldsDir();

    File getScreenshotDir();

    File getSettingsFile();

    File getServerSettingsFile();

    File getCommandPermsFile();

    File getWhitelistFile();

    File getBlacklistFile();

    File getModSettingsFile();

    File getContentPackSettingsFile();

    File getPlayerDesignFile();

    File getModConfigFolder();

    File getSettingsFolder();

    void loadSettings(IJsonSettings settings);

    void saveSettings(IJsonSettings settings);
}