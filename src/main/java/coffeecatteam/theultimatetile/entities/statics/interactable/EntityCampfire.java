package coffeecatteam.theultimatetile.entities.statics.interactable;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.InventoryCampfire;

import java.awt.*;

public class EntityCampfire extends EntityStatic {

    private Animation anim;
    private InventoryCampfire inventoryCampfire;

    public EntityCampfire(GameEngine gameEngine, String id) {
        super(gameEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.WOOD);
        anim = new Animation(135, Assets.CAMPFIRE);

        inventoryCampfire = new InventoryCampfire(gameEngine, gameEngine.getEntityManager().getPlayer(), TAGS);
    }

    @Override
    public void tick() {
        anim.tick();

        inventoryCampfire.setTAGS(TAGS);
        inventoryCampfire.tick();
    }

    @Override
    public void interact() {
        if (!inventoryCampfire.isActive()) {
            gameEngine.getInventoryManager().openCloseInventory(inventoryCampfire);
        }

        this.interacted = false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(anim.getCurrentFrame(), this.renderX, this.renderY, width, height, null);
    }

    @Override
    public void postRender(Graphics g) {
        inventoryCampfire.render(g);
    }
}
