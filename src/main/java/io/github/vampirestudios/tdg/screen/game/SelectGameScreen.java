package io.github.vampirestudios.tdg.screen.game;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.gfx.ui.button.world.WidgetButtonWorld;
import io.github.vampirestudios.tdg.jsonparsers.SavedGamesJSONParser;
import io.github.vampirestudios.tdg.screen.AbstractMenuScreen;
import io.github.vampirestudios.tdg.screen.TileBackgrounds;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;

public class SelectGameScreen extends AbstractMenuScreen {

    public SelectGameScreen(MaewilEngine maewilEngine) {
        super(maewilEngine, TileBackgrounds.GRASS.getTiles());

        int btnHeight = 64;
        int y = MaewilLauncher.HEIGHT / 2 - btnHeight / 2 + 25;
        float yOff = 25 * 3f;

        for (int i = 0; i < SavedGamesJSONParser.SAVE_CAPACITY; i++) {
            String worldName = "New_World_" + NumberUtils.getRandomInt(1000);
            uiManager.addObject(new WidgetButtonWorld(maewilEngine, y + yOff * i, i, worldName));
        }
    }

}
