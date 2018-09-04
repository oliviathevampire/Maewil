package coffeecatteam.theultimatetile.entities.statics;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityShopStall extends EntityStatic {

    private BufferedImage ROOF;

    public EntityShopStall(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id, Entity.DEFAULT_WIDTH * 2, Entity.DEFAULT_HEIGHT * 2);
        //isCollidable = false;
        showHitbox = true;

        bounds = new Rectangle(0, height / 2 + height / 3, width, height / 12);
        ROOF = Assets.SHOP_ROOFS[Utils.getRandomInt(0, 2)];
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.SHOP_STALL, (int) (this.x - theUltimateTile.getCamera().getxOffset()), (int) (this.y - theUltimateTile.getCamera().getyOffset()), width, height, null);
        g.drawImage(ROOF, (int) (this.x - theUltimateTile.getCamera().getxOffset()), (int) (this.y - theUltimateTile.getCamera().getyOffset()), width, height / 2, null);
    }
}
