package coffeecatteam.theultimatetile.game.entities.statics.interactable;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.game.inventory.InventoryCampfire;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;

public class EntityCampfire extends EntityStatic {

    private Animation anim;
    private InventoryCampfire inventoryCampfire;

    public EntityCampfire(Engine engine, String id) {
        super(engine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.WOOD);
        anim = new Animation(135, Assets.CAMPFIRE);

        inventoryCampfire = new InventoryCampfire(engine, ((GameEngine) engine).getEntityManager().getPlayer(), TAGS);
        bounds.y = (int) (position.y + height / 2);
        bounds.height = height / 2;
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
    public void render(Graphics2D g) {
        g.drawImage(anim.getCurrentFrame(), this.renderX, this.renderY, width, height, null);
    }

    @Override
    public void postRender(Graphics2D g) {
        super.postRender(g);
        inventoryCampfire.render(g);
    }
}
