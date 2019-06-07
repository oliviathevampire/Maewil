package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.entities.creatures.PlayerEntity;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class OverlayPlayerSprint extends Overlay {

    public OverlayPlayerSprint(TutEngine tutEngine, PlayerEntity player) {
        super(tutEngine, player);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        int multiplier = 6;
        int sWidth = 32;
        int sHeight = 16;
        int width = sWidth * multiplier;
        int height = sHeight * multiplier;

        int sprint = (int) player.getSprintTimer();
        String text = "Sprint left: " + sprint;
        Font font = Assets.FONTS.get("20");
        int x = TutLauncher.WIDTH - width;
        int y = TutLauncher.HEIGHT - height;

        Text.drawString(g, text, TutLauncher.WIDTH - Text.getWidth(text, font) - 10, TutLauncher.HEIGHT - height, false, false, Color.white, font);
        Assets.SPRINT[0].draw(x, y, width, height);

        int sprintWidth = (int) NumberUtils.map(sprint - 1, 0, player.getMaxSprintTimer(), 0, sWidth);
        Assets.SPRINT[1].getSubImage(0, 0, sWidth - sprintWidth, sHeight).draw(x, y, width - sprintWidth * multiplier, height);
    }
}
