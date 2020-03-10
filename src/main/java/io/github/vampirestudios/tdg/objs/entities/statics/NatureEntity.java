package io.github.vampirestudios.tdg.objs.entities.statics;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.objs.items.Item;
import io.github.vampirestudios.tdg.objs.items.ItemStack;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public abstract class NatureEntity extends StaticEntity {

    protected List<Item> drops = new ArrayList<>();

    public NatureEntity(MaewilEngine maewilEngine, String id, int width, int height, HitType hitType) {
        super(maewilEngine, id, width, height, hitType);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);
        if (this.currentHealth != this.maxHealth)
            this.renderHealth(g);
    }

    @Override
    public void die() {
        for (Item item : drops) {
            int amt = NumberUtils.getRandomInt(3);
            for (int i = 0; i < amt; i++)
                maewilEngine.getEntityManager().addItem(new ItemStack(item.newCopy()), (float) (position.x + NumberUtils.getRandomInt(width)) / Tile.TILE_SIZE, (float) (position.y + NumberUtils.getRandomInt(height)) / Tile.TILE_SIZE);
        }
    }

    public List<Item> getDrops() {
        return drops;
    }
}
