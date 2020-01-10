package io.github.vampirestudios.tdg.gfx.overlays;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class GlubOverlay extends Overlay {

    public GlubOverlay(TutEngine tutEngine, PlayerEntity player) {
        super(tutEngine, player);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        int multiplier = 6;
        int sWidth = (16 * 4) / 2;
        int sHeight = 8;
        int width = sWidth * multiplier;
        int height = sHeight * multiplier;

        int glub = player.getGlubel();
        int lvl = player.getLvl();
        String text = "Level: " + lvl;
        Font font = Assets.FONTS.get("20");
        int x = TutLauncher.WIDTH / 2 - width / 2;

        Text.drawString(g, text, TutLauncher.WIDTH / 2f, (int) (height + Text.getHeight(text, font) * 1.5f), true, false, new Color(4, 79, 163), font);
        Assets.GLUB_METER[0].draw(x, 0, width, height);

        int glubWidth = (int) NumberUtils.map(glub - 1, 0, player.getMaxGludel(), 0, sWidth);
        Assets.GLUB_METER[1].getSubImage(0, 0, glubWidth * 2 + 1, sHeight * 2).draw(x, 0, glubWidth * multiplier, height);
    }
}
