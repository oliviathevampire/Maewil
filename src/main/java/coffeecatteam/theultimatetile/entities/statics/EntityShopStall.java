package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;

public class EntityShopStall extends EntityStatic {

    private Animation ROOF;

    public EntityShopStall(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id, Entity.DEFAULT_WIDTH * 2, Entity.DEFAULT_HEIGHT * 2, EntityHitType.WOOD);

        bounds = new Rectangle(0, height / 2 + height / 3, width, height / 12);
        ROOF = pickRoof();
    }

    private Animation pickRoof() {
        int speed = 250;
        Animation anim = new Animation(speed, Assets.SHOP_ROOF_ORANGE);
        int i = Utils.getRandomInt(0, 3);
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
        g.drawImage(Assets.SHOP_STALL, (int) (this.x - theUltimateTile.getCamera().getxOffset()), (int) (this.y - theUltimateTile.getCamera().getyOffset()), width, height, null);
        g.drawImage(ROOF.getCurrentFrame(), (int) (this.x - theUltimateTile.getCamera().getxOffset()), (int) (this.y - theUltimateTile.getCamera().getyOffset()), width, height / 2, null);
    }
}
