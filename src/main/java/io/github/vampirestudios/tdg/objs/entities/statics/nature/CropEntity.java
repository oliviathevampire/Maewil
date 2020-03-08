package io.github.vampirestudios.tdg.objs.entities.statics.nature;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.statics.NatureEntity;
import io.github.vampirestudios.tdg.objs.items.Item;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

public class CropEntity extends NatureEntity {

    private CropType type;

    public CropEntity(MaewilEngine maewilEngine, CropType type) {
        super(maewilEngine, "crop", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, HitType.BUSH);
        isCollidable = false;
        setCurrentTexture(type.getId());
        this.type = type;

        drops.add(this.type.getDrop());
    }

    @Override
    public String getUnlocalizedName() {
        return getId() + "_" + this.type.getId();
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        getTexture("ground").draw(this.renderX, this.renderY, width, height);
        super.render(container, g);
    }

    public enum CropType {

        CARROT("carrot", Items.CARROT),
        WHEAT("wheat", Items.WHEAT),
        POTATO("potato", Items.POTATO),
        CORN("corn", Items.CORN);

        private String id;
        private Item drop;

        CropType(String id, Item drop) {
            this.id = id;
            this.drop = drop;
        }

        public String getId() {
            return id;
        }

        public Item getDrop() {
            return drop;
        }
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new CropEntity(maewilEngine, type));
    }
}
