package coffeecatteam.theultimatetile.objs.entities.statics.interactable;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.inventory.InventoryCampfire;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class EntityCampfire extends EntityStatic {

    private Animation anim;
    private InventoryCampfire inventoryCampfire;

    public EntityCampfire(TutEngine tutEngine, String id) {
        super(tutEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.WOOD);
        anim = new Animation(135, Assets.CAMPFIRE);

        inventoryCampfire = new InventoryCampfire(tutEngine, tutEngine.getEntityManager().getPlayer(), TAGS);
        bounds.y = (int) (position.y + height / 2);
        bounds.height = height / 2;
    }

    @Override
    public void update(GameContainer container, int delta) {
        anim.update();

        inventoryCampfire.setTAGS(TAGS);
        inventoryCampfire.update(container, delta);
    }

    @Override
    public void interact() {
        if (!inventoryCampfire.isActive()) {
            tutEngine.getInventoryManager().openCloseInventory(inventoryCampfire);
        }

        this.interacted = false;
    }

    @Override
    public void render(Graphics g) {
        anim.getCurrentFrame().draw(this.renderX, this.renderY, width, height);
    }

    @Override
    public void postRender(Graphics g) {
        super.postRender(g);
        inventoryCampfire.render(g);
    }
}
