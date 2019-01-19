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

public class OverlayPlayerSprint extends Overlay {

    public OverlayPlayerSprint(Engine engine, EntityPlayer player) {
        super(engine, player);
    }

    @Override
    public void update(GameContainer container, int delta) {

    }

    @Override
    public void render(Graphics g) {
        int multiplier = 6;
        int sWidth = 32;
        int sHeight = 16;
        int width = sWidth * multiplier;
        int height = sHeight * multiplier;

        int sprint = (int) player.getSprintTimer();
        String text = "Sprint left: " + sprint;
        Font font = Assets.FONTS.get("20");
        int x = engine.getWidth() - width;
        int y = engine.getHeight() - height;

        Text.drawString(g, text, engine.getWidth() - Text.getWidth(text, font) - 10, engine.getHeight() - height, false, false, Color.white, font);
        Assets.SPRINT[0].draw(x, y, width, height);

        int sprintWidth = (int) NumberUtils.map(sprint - 1, 0, player.getMaxSprintTimer(), 0, sWidth);
        Assets.SPRINT[1].getSubImage(0, 0, sWidth - sprintWidth, sHeight).draw(x, y, width - sprintWidth * multiplier, height);
    }
}
