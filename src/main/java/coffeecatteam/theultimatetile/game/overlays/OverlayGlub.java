package coffeecatteam.theultimatetile.game.overlays;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class OverlayGlub extends Overlay {

    public OverlayGlub(Engine engine, EntityPlayer player) {
        super(engine, player);
    }

    @Override
    public void update(GameContainer container, int delta) {

    }

    @Override
    public void render(Graphics g) {
        int multiplier = 6;
        int sWidth = (16 * 4) / 2;
        int sHeight = 8;
        int width = sWidth * multiplier;
        int height = sHeight * multiplier;

        int glub = player.getGlubel();
        int lvl = player.getLvl();
        String text = "Level: " + lvl;
        Font font = Assets.FONTS.get("20");
        int x = engine.getWidth() / 2 - width / 2;

        Text.drawString(g, text, engine.getWidth() / 2, height + Text.getHeight(text, font) / 2, true, false, new Color(4, 79, 163), font);
        Assets.GLUB_METER[0].draw(x, 0, width, height);

        int glubWidth = (int) NumberUtils.map(glub - 1, 0, player.getMaxGludel(), 0, sWidth);
        Assets.GLUB_METER[1].getSubImage(0, 0, glubWidth * 2 + 1, sHeight * 2).draw(x, 0, glubWidth * multiplier, height);
    }
}
