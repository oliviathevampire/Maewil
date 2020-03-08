package io.github.vampirestudios.tdg.objs.entities.statics.interactable;

import coffeecatteam.coffeecatutils.position.AABB;
import io.github.vampirestudios.tdg.inventory.InventoryCampfire;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.statics.StaticEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

public class CampfireEntity extends StaticEntity {

    private InventoryCampfire inventoryCampfire;

    public CampfireEntity(MaewilEngine maewilEngine) {
        super(maewilEngine, "campfire", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, HitType.WOOD);
        setCurrentTexture("main");

        inventoryCampfire = new InventoryCampfire(maewilEngine, maewilEngine.getPlayer(), TAGS);
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(0, height / 2f, width, height / 2);
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        inventoryCampfire.setTAGS(TAGS);
//        inventoryCampfire.update(container, delta);
    }

    @Override
    public void interact() {
        if (!inventoryCampfire.isActive())
            maewilEngine.getInventoryManager().openCloseInventory(inventoryCampfire);

        this.interacted = false;
    }

    @Override
    public void postRender(GameContainer container, Graphics g) {
        super.postRender(container, g);
//        inventoryCampfire.render(g);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new CampfireEntity(maewilEngine));
    }
}
