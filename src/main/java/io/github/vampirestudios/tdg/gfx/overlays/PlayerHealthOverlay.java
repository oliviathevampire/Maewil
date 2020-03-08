package io.github.vampirestudios.tdg.gfx.overlays;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.newdawn.slick.Color;

public class PlayerHealthOverlay extends Overlay {

    public PlayerHealthOverlay(MaewilEngine maewilEngine, PlayerEntity player) {
        super(maewilEngine, player);
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        int hWidth = 96;
        int hHeight = 96;

        int overMaxHealth = player.getMaxHealth() * 6;
        boolean overMaxHealthDouble = player.getCurrentHealth() > overMaxHealth;
        float from = (overMaxHealthDouble ? player.getCurrentHealth() : overMaxHealth);
        float to = Assets.HEARTS.length - 1;
        int hStage = (int) NumberUtils.map(player.getCurrentHealth(), 0, from, to, 0);

        Text.drawString(g, "HP: " + player.getCurrentHealth(), 10, MaewilLauncher.HEIGHT - hHeight, false, false, Color.red, Assets.FONTS.get("20"));
        Assets.HEARTS[hStage].draw(0, MaewilLauncher.HEIGHT - hHeight, hWidth, hHeight);
    }
}
