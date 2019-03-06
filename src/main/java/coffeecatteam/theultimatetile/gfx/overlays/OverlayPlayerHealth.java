package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
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
        int hStage = (int) NumberUtils.map(player.getCurrentHealth(), 0, player.getMaxHealth() * 2, Assets.HEARTS.length - 1, 0);

        Text.drawString(g, "HP: " + player.getCurrentHealth(), 10, tutEngine.getHeight() - hHeight, false, false, Color.red, Assets.FONTS.get("20"));

        Assets.HEARTS[hStage].draw(0, tutEngine.getHeight() - hHeight, hWidth, hHeight);
    }
}
