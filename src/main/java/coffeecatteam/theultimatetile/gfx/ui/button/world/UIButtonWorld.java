package coffeecatteam.theultimatetile.gfx.ui.button.world;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import org.newdawn.slick.GameContainer;

/**
 * @author CoffeeCatRailway
 * Created: 10/01/2019
 */
public class UIButtonWorld extends UIButton {

    public UIButtonWorld(Engine engine, float y, int index) {
        this(engine, y, index, "");
    }

    public UIButtonWorld(Engine engine, float y, int index, String path) {
        super(engine, true, (int) y, "New Game", null);
        String savesPath = "./saves/";
        String path1 = savesPath + path;
        this.listener = new ClickListenerWorld(engine, path1, savesPath, index);
    }

    @Override
    public void update(GameContainer container, int delta) {
        super.update(container, delta);
        ClickListenerWorld listener = (ClickListenerWorld) this.listener;
        if (listener.isSaved())
            this.setText(listener.getText());
    }
}
