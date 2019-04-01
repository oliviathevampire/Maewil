package coffeecatteam.theultimatetile.objs.entities.statics.interactable;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.inventory.InventoryCampfire;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityStatic;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class EntityCampfire extends EntityStatic {

    private InventoryCampfire inventoryCampfire;

    public EntityCampfire(TutEngine tutEngine) {
        super(tutEngine, "campfire", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, HitType.WOOD);
        setCurrentTexture("main");

        inventoryCampfire = new InventoryCampfire(tutEngine, tutEngine.getPlayer(), TAGS);
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(0, height / 2f, width, height / 2);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        inventoryCampfire.setTAGS(TAGS);
        inventoryCampfire.update(container, game, delta);
    }

    @Override
    public void interact() {
        if (!inventoryCampfire.isActive())
            tutEngine.getInventoryManager().openCloseInventory(inventoryCampfire);

        this.interacted = false;
    }

    @Override
    public void postRender(GameContainer container, StateBasedGame game, Graphics g) {
        super.postRender(container, game, g);
        inventoryCampfire.render(container, game, g);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityCampfire(tutEngine));
    }
}
