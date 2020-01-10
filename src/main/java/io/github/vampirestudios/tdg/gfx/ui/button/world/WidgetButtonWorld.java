package io.github.vampirestudios.tdg.gfx.ui.button.world;

import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author CoffeeCatRailway
 * Created: 10/01/2019
 */
public class WidgetButtonWorld extends WidgetButton {

    public WidgetButtonWorld(TutEngine tutEngine, float y, int index) {
        this(tutEngine, y, index, "");
    }

    public WidgetButtonWorld(TutEngine tutEngine, float y, int index, String path) {
        super(tutEngine, true, (int) y, "New Game", null);
        String savesPath = "./data/saves/";
        String path1 = savesPath + path;
        this.listener = new WorldButtonClickListener(tutEngine, path1, savesPath, index);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
        WorldButtonClickListener listener = (WorldButtonClickListener) this.listener;
        if (listener.isSaved())
            this.setText(listener.getText());
    }
}
