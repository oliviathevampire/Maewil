package coffeecatteam.theultimatetile.objs.entities.statics.interactable;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityStatic;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class EntityShopStall extends EntityStatic {

    public EntityShopStall(TutEngine tutEngine) {
        super(tutEngine, "shop_Stall", Entity.DEFAULT_WIDTH * 2, Entity.DEFAULT_HEIGHT * 2, EntityHitType.WOOD);
        setCurrentTexture(pickRoof().getId());
    }

    private RoofType pickRoof() {
        int i = NumberUtils.getRandomInt(3);
        if (i == 0)
            return RoofType.ORANGE;
        if (i == 1)
            return RoofType.BLUE;
        if (i == 2)
            return RoofType.RED;
        return RoofType.WHITE;
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(0, height / 2f + height / 3f, width, height / 12);
    }

    @Override
    public void update(GameContainer container, int delta) {
        // TODO: ADD FUNCTION
    }

    @Override
    public void interact() {
        tutEngine.getLogger().warn("Shop shop, shoppy time!");
        this.interacted = false;
    }

    @Override
    public void render(Graphics g) {
        getTexture("stall").draw(this.renderX, this.renderY, width, height);
        getCurrentTexture().getCurrentFrame().draw(this.renderX, this.renderY, width, height / 2f);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityShopStall(tutEngine));
    }

    enum RoofType {

        ORANGE("orange"),
        BLUE("blue"),
        RED("red"),
        WHITE("white");

        private String id;

        RoofType(String id) {
            this.id = "roof" + id;
        }

        public String getId() {
            return id;
        }
    }
}
