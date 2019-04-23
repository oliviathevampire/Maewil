package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class EntityCrop extends EntityNature {

    private CropType type;

    public EntityCrop(TutEngine tutEngine, CropType type) {
        super(tutEngine, "crop", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, HitType.BUSH);
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
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        getTexture("ground").draw(this.renderX, this.renderY, width, height);
        super.render(container, game, g);
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
