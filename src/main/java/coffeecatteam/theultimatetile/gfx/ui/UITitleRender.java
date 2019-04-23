package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2019
 */
public class UITitleRender extends UIObject {

    private Image title_fg;

    public UITitleRender() {
        super(0, 0, 0, 0);
        title_fg = Assets.GUI_TITLE_FG[0];
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        Image title = Assets.GUI_TITLE_BIG;
        float mult = 3.5f;
        float w = title.getWidth() * mult;
        float h = title.getHeight() * mult;
        title.draw(TutLauncher.WIDTH / 2f - w / 2f, 20, w, h);

        float w1 = title_fg.getWidth() * mult;
        float h1 = title_fg.getHeight() * mult;
        Vector2D pos = new Vector2D(TutLauncher.WIDTH / 2f - w1 / 2f + 1.5f * mult, 20 + 27 * mult);
        title_fg.draw((float) pos.x, (float) pos.y, w1, h1);

        this.position = pos;
        this.width = w1;
        this.height = h1;
    }

    @Override
    public void onClick() {
        title_fg = Assets.GUI_TITLE_FG[NumberUtils.getRandomInt(Assets.GUI_TITLE_FG.length - 1)];
    }
}
