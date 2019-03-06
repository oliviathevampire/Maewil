package coffeecatteam.theultimatetile.objs.entities.statics.interactable;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.inventory.InventoryCampfire;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityStatic;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class EntityCampfire extends EntityStatic {

    private InventoryCampfire inventoryCampfire;

    public EntityCampfire(TutEngine tutEngine) {
        super(tutEngine, "campfire", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.WOOD);
        setCurrentTexture("main");

        inventoryCampfire = new InventoryCampfire(tutEngine, tutEngine.getPlayer(), TAGS);
        bounds.y = (int) (position.y + height / 2);
        bounds.height = height / 2;
    }

    @Override
    public void update(GameContainer container, int delta) {
        inventoryCampfire.setTAGS(TAGS);
        inventoryCampfire.update(container, delta);
    }

    @Override
    public void interact() {
        if (!inventoryCampfire.isActive())
            tutEngine.getInventoryManager().openCloseInventory(inventoryCampfire);

        this.interacted = false;
    }

    @Override
    public void postRender(Graphics g) {
        super.postRender(g);
        inventoryCampfire.render(g);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityCampfire(tutEngine));
    }
}
