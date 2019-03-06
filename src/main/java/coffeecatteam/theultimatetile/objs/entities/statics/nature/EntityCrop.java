package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.objs.items.Items;
import org.newdawn.slick.Graphics;

public class EntityCrop extends EntityNature {

    private CropType type;

    public EntityCrop(TutEngine tutEngine, CropType type) {
        super(tutEngine, "crop", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.BUSH);
        isCollidable = false;
        setCurrentTexture(type.getId());
        this.type = type;

        drops.add(this.type.getDrop());
    }

    @Override
    public void render(Graphics g) {
        getTexture("ground").draw(this.renderX, this.renderY, width, height);
        super.render(g);
    }

    public enum CropType {

        CARROT("carrot", Items.CARROT),
        WHEAT("wheat", Items.WHEAT),
        POTATO("potato", Items.POTATO),
        TOMATO("tomato", Items.TOMATO),
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
        return super.newCopy(new EntityCrop(tutEngine, type));
    }
}
