package io.github.vampirestudios.tdg.gfx.ui.button.world;

import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.start.MaewilEngine;
public class WidgetButtonWorld extends WidgetButton {

    public WidgetButtonWorld(MaewilEngine maewilEngine, float y, int index) {
        this(maewilEngine, y, index, "");
    }

    public WidgetButtonWorld(MaewilEngine maewilEngine, float y, int index, String path) {
        super(maewilEngine, true, (int) y, "New Game", null);
        String savesPath = "./data/saves/";
        String path1 = savesPath + path;
        this.listener = new WorldButtonClickListener(maewilEngine, path1, savesPath, index);
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        super.update(container, delta);
        WorldButtonClickListener listener = (WorldButtonClickListener) this.listener;
        if (listener.isSaved())
            this.setText(listener.getText());
    }
}
