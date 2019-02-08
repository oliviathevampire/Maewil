package coffeecatteam.theultimatetile.gfx.ui.button.world;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import org.newdawn.slick.GameContainer;

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
        String savesPath = "./saves/";
        String path1 = savesPath + path;
        this.listener = new ClickListenerWorld(tutEngine, path1, savesPath, index);
    }

    @Override
    public void update(GameContainer container, int delta) {
        super.update(container, delta);
        ClickListenerWorld listener = (ClickListenerWorld) this.listener;
        if (listener.isSaved())
            this.setText(listener.getText());
    }
}
