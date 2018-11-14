package coffeecatteam.theultimatetile.game.entities.statics.interactable;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;

public class EntityShopStall extends EntityStatic {

    private Animation ROOF;

    public EntityShopStall(Engine engine, String id) {
        super(engine, id, Entity.DEFAULT_WIDTH * 2, Entity.DEFAULT_HEIGHT * 2, EntityHitType.WOOD);

        bounds = new Rectangle(0, height / 2 + height / 3, width, height / 12);
        ROOF = pickRoof();
    }

    private Animation pickRoof() {
        int speed = 250;
        Animation anim = new Animation(speed, Assets.SHOP_ROOF_ORANGE);
        int i = Utils.getRandomInt(3);
        if (i == 0)
            anim.setFrames(Assets.SHOP_ROOF_ORANGE);
        if (i == 1)
            anim.setFrames(Assets.SHOP_ROOF_BLUE);
        if (i == 2)
            anim.setFrames(Assets.SHOP_ROOF_RED);
        if (i == 3)
            anim.setFrames(Assets.SHOP_ROOF_GREY);
        return anim;
    }

    @Override
    public void tick() {
        ROOF.tick();
    }

    @Override
    public void interact() {
        Logger.print("Shop shop, shoppy time!");
        this.interacted = false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.SHOP_STALL, this.renderX, this.renderY, width, height, null);
        g.drawImage(ROOF.getCurrentFrame(), this.renderX, this.renderY, width, height / 2, null);
    }
}
