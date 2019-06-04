package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author CoffeeCatRailway
 * Created: 28/04/2019
 */
public abstract class AbstractListTheme extends UIObject {

    public static final float SIZE = 51;

    private boolean useGrayTheme = false;
    protected Image[] THEME;

    public AbstractListTheme(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public AbstractListTheme(Vector2D position, float width, float height) {
        super(position, width, height);
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
        if (useGrayTheme)
            THEME = Assets.GUI_LIST_GRAY;
        else
            THEME = Assets.GUI_LIST_BLUE;
    }

    public void swapTheme() {
        this.useGrayTheme = !useGrayTheme;
    }
}
