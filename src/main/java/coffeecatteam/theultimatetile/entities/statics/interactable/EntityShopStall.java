package coffeecatteam.theultimatetile.entities.statics.interactable;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class EntityShopStall extends EntityStatic {

    private Animation ROOF;

    public EntityShopStall(TutEngine tutEngine, String id) {
        super(tutEngine, id, Entity.DEFAULT_WIDTH * 2, Entity.DEFAULT_HEIGHT * 2, EntityHitType.WOOD);

        bounds = new AABB(0, height / 2 + height / 3, width, height / 12);
        ROOF = pickRoof();
    }

    private Animation pickRoof() {
        int speed = 250;
        Animation anim = new Animation(speed, Assets.SHOP_ROOF_ORANGE);
        int i = NumberUtils.getRandomInt(3);
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
    public void update(GameContainer container, int delta) {
        ROOF.update(container, delta);
    }

    @Override
    public void interact() {
        tutEngine.getLogger().print("Shop shop, shoppy time!");
        this.interacted = false;
    }

    @Override
    public void render(Graphics g) {
        Assets.SHOP_STALL.draw(this.renderX, this.renderY, width, height);
        ROOF.getCurrentFrame().draw(this.renderX, this.renderY, width, height / 2f);
    }
}
