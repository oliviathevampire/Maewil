package coffeecatteam.theultimatetile.overlays;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class OverlayPlayerHealth extends Overlay {

    public OverlayPlayerHealth(TutEngine tutEngine, EntityPlayer player) {
        super(tutEngine, player);
    }

    @Override
    public void update(GameContainer container, int delta) {
    }

    @Override
    public void render(Graphics g) {
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

        Text.drawString(g, "HP: " + player.getCurrentHealth(), 10, tutEngine.getHeight() - hHeight, false, false, Color.red, Assets.FONTS.get("20"));

        Assets.HEARTS[hStage].draw(0, tutEngine.getHeight() - hHeight, hWidth, hHeight);
    }
}
