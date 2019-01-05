package coffeecatteam.theultimatetile.game.entities.statics.interactable;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.game.inventory.InventoryCampfire;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

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
    public void update(GameContainer container, int delta) {
        anim.update(container, delta);

        inventoryCampfire.setTAGS(TAGS);
        inventoryCampfire.update(container, delta);
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
        anim.getCurrentFrame().draw(this.renderX, this.renderY, width, height);
    }

    @Override
    public void postRender(Graphics g) {
        super.postRender(g);
        inventoryCampfire.render(g);
    }
}
