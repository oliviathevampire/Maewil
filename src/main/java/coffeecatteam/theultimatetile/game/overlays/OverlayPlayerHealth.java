package coffeecatteam.theultimatetile.game.overlays;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;

import java.awt.*;

public class OverlayPlayerHealth extends Overlay {

    public OverlayPlayerHealth(Engine engine, EntityPlayer player) {
        super(engine, player);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D g) {
        int hWidth = 96;
        int hHeight = 96;
        int hStage = 0;

        if (player.getCurrentHealth() <= Entity.DEFAULT_HEALTH && player.getCurrentHealth() >= Entity.DEFAULT_HEALTH / 2 + Entity.DEFAULT_HEALTH / 4)
            hStage = 0;
        if (player.getCurrentHealth() <= Entity.DEFAULT_HEALTH / 2 + Entity.DEFAULT_HEALTH / 4 && player.getCurrentHealth() >= Entity.DEFAULT_HEALTH / 2)
            hStage = 1;
        if (player.getCurrentHealth() <= Entity.DEFAULT_HEALTH / 2 && player.getCurrentHealth() >= Entity.DEFAULT_HEALTH / 4)
            hStage = 2;
        if (player.getCurrentHealth() <= Entity.DEFAULT_HEALTH / 4 && player.getCurrentHealth() >= 0)
            hStage = 3;
        if (player.getCurrentHealth() <= 0)
            hStage = 4;

        Text.drawString(g, "HP: " + player.getCurrentHealth(), 10, engine.getHeight() - hHeight, false, false, Color.red, Assets.FONT_20);

        g.drawImage(Assets.HEARTS[hStage], 0, engine.getHeight() - hHeight, hWidth, hHeight, null);
    }
}
