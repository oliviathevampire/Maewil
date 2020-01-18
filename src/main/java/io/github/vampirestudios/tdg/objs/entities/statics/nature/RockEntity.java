package io.github.vampirestudios.tdg.objs.entities.statics.nature;

import coffeecatteam.coffeecatutils.position.AABB;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.statics.NatureEntity;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;

public class RockEntity extends NatureEntity {

    private RockType type;

    public RockEntity(MaewilEngine maewilEngine, RockType type) {
        super(maewilEngine, "rock", type.getWidth(), type.getHeight(), HitType.STONE);
        setCurrentTexture(type.getId());
        this.type = type;

        drops.add(Items.ROCK);
    }

    @Override
    public String getUnlocalizedName() {
        return getId() + "_" + this.type.getId();
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(0, height / 2f + height / 3f, width, height / 3);
    }

    public enum RockType {

        SMALL("small", Tile.TILE_SIZE, Tile.TILE_SIZE),
        MEDIUM("medium", Tile.TILE_SIZE * 2, Tile.TILE_SIZE);

        private String id;
        private int width, height;

        RockType(String id, int width, int height) {
            this.id = id;
            this.width = width;
            this.height = height;
        }

        public String getId() {
            return id;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new RockEntity(maewilEngine, type));
    }
}
