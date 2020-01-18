package io.github.vampirestudios.tdg.objs.entities.statics.interactable;

import coffeecatteam.coffeecatutils.position.AABB;
import io.github.vampirestudios.tdg.inventory.InventoryCampfire;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.statics.StaticEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

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
    public void update(GameContainer container, StateBasedGame game, int delta) {
        inventoryCampfire.setTAGS(TAGS);
        inventoryCampfire.update(container, game, delta);
    }

    @Override
    public void interact() {
        if (!inventoryCampfire.isActive())
            maewilEngine.getInventoryManager().openCloseInventory(inventoryCampfire);

        this.interacted = false;
    }

    @Override
    public void postRender(GameContainer container, StateBasedGame game, Graphics g) {
        super.postRender(container, game, g);
        inventoryCampfire.render(container, game, g);
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new CampfireEntity(maewilEngine));
    }
}
