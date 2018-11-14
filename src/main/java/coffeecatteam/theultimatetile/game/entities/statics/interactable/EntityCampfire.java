package coffeecatteam.theultimatetile.game.entities.statics.interactable;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.game.inventory.InventoryCampfire;

import java.awt.*;

public class EntityCampfire extends EntityStatic {

    private Animation anim;
    private InventoryCampfire inventoryCampfire;

    public EntityCampfire(Engine engine, String id) {
        super(engine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.WOOD);
        anim = new Animation(135, Assets.CAMPFIRE);

        inventoryCampfire = new InventoryCampfire(engine, ((GameEngine) engine).getEntityManager().getPlayer(), TAGS);
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
            ((GameEngine) engine).getInventoryManager().openCloseInventory(inventoryCampfire);
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