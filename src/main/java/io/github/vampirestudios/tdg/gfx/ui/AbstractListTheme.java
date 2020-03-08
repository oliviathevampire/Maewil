package io.github.vampirestudios.tdg.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import org.mini2Dx.core.game.GameContainer;
import org.newdawn.slick.Image;

public abstract class AbstractListTheme extends WidgetObject {

    public static final float SIZE = 51;

    private boolean useGrayTheme = false;
    protected Image[] THEME = Assets.GUI_LIST_GRAY;

    public AbstractListTheme(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public AbstractListTheme(Vector2D position, float width, float height) {
        super(position, width, height);
    }

    public void update(GameContainer container, float delta) {
        super.update(container, delta);
//        THEME = Assets.GUI_LIST_GRAY;
    }

    public void swapTheme() {
        this.useGrayTheme = !useGrayTheme;
    }
}
