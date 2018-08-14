package coffeecatteam.tilegame.gfx.overlays;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.entities.creatures.EntityPlayer;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.gfx.Text;

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

        if (player.getHealth() < Entity.DEFAULT_HEALTH && player.getHealth() > Entity.DEFAULT_HEALTH / 2 + Entity.DEFAULT_HEALTH / 4)
            hStage = 0;
        if (player.getHealth() < Entity.DEFAULT_HEALTH / 2 + Entity.DEFAULT_HEALTH / 4 && player.getHealth() > Entity.DEFAULT_HEALTH / 2)
            hStage = 1;
        if (player.getHealth() < Entity.DEFAULT_HEALTH / 2 && player.getHealth() > Entity.DEFAULT_HEALTH / 4)
            hStage = 2;
        if (player.getHealth() < Entity.DEFAULT_HEALTH / 4 && player.getHealth() > 0)
            hStage = 3;
        if (player.getHealth() == 0)
            hStage = 4;

        Text.drawString(g, "HP: " + player.getHealth(), 10, handler.getHeight() - hHeight, Color.red, Assets.FONT_20);

        g.drawImage(Assets.HEARTS[hStage], 0, handler.getHeight() - hHeight, hWidth, hHeight, null);
    }
}
