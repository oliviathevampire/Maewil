package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * @author CoffeeCatRailway
 * Created: 17/03/2019
 */
public class UITitleRender extends UIObject {

    private Image title_fg;

    public UITitleRender() {
        super(0, 0, 0, 0);
        title_fg = Assets.TITLE_FG[0];
    }

    @Override
    public void render(Graphics g) {
        Image title = Assets.TITLE;
        float mult = 3.5f;
        float w = title.getWidth() * mult;
        float h = title.getHeight() * mult;
        title.draw(TutEngine.getTutEngine().getWidth() / 2f - w / 2f, 20, w, h);

        float w1 = title_fg.getWidth() * mult;
        float h1 = title_fg.getHeight() * mult;
        Vector2D pos = new Vector2D(TutEngine.getTutEngine().getWidth() / 2f - w1 / 2f + 1.5f * mult, 20 + 27 * mult);
        title_fg.draw((float) pos.x, (float) pos.y, w1, h1);

        this.position = pos;
        this.width = w1;
        this.height = h1;
    }

    @Override
    public void onClick() {
        title_fg = Assets.TITLE_FG[NumberUtils.getRandomInt(Assets.TITLE_FG.length - 1)];
    }
}
