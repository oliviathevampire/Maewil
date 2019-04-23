package coffeecatteam.theultimatetile.gfx.ui.button.world;

import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author CoffeeCatRailway
 * Created: 10/01/2019
 */
public class UIButtonWorld extends UIButton {

    public UIButtonWorld(TutEngine tutEngine, float y, int index) {
        this(tutEngine, y, index, "");
    }

    public UIButtonWorld(TutEngine tutEngine, float y, int index, String path) {
        super(tutEngine, true, (int) y, "New Game", null);
        String savesPath = "./data/saves/";
        String path1 = savesPath + path;
        this.listener = new ClickListenerWorld(tutEngine, path1, savesPath, index);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
        ClickListenerWorld listener = (ClickListenerWorld) this.listener;
        if (listener.isSaved())
            this.setText(listener.getText());
    }
}
