package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.player.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;

import java.awt.*;

public class OverlayPlayerHealth extends Overlay {

    public OverlayPlayerHealth(Handler handler, EntityPlayer player) {
        super(handler, player);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        int hWidth = 96;
        int hHeight = 96;
        int hStage = 0;

        if (player.getCurrentHealth() < Entity.DEFAULT_HEALTH && player.getCurrentHealth() > Entity.DEFAULT_HEALTH / 2 + Entity.DEFAULT_HEALTH / 4)
            hStage = 0;
        if (player.getCurrentHealth() < Entity.DEFAULT_HEALTH / 2 + Entity.DEFAULT_HEALTH / 4 && player.getCurrentHealth() > Entity.DEFAULT_HEALTH / 2)
            hStage = 1;
        if (player.getCurrentHealth() < Entity.DEFAULT_HEALTH / 2 && player.getCurrentHealth() > Entity.DEFAULT_HEALTH / 4)
            hStage = 2;
        if (player.getCurrentHealth() < Entity.DEFAULT_HEALTH / 4 && player.getCurrentHealth() > 0)
            hStage = 3;
        if (player.getCurrentHealth() == 0)
            hStage = 4;

        Text.drawString(g, "HP: " + player.getCurrentHealth(), 10, handler.getHeight() - hHeight, Color.red, Assets.FONT_20);

        g.drawImage(Assets.HEARTS[hStage], 0, handler.getHeight() - hHeight, hWidth, hHeight, null);
    }
}
