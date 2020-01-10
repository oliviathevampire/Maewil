package io.github.vampirestudios.tdg.objs.entities.statics.interactable;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.statics.StaticEntity;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class ShopStallEntity extends StaticEntity {

    public ShopStallEntity(TutEngine tutEngine) {
        super(tutEngine, "shop_stall", Entity.DEFAULT_WIDTH * 2, Entity.DEFAULT_HEIGHT * 2, HitType.WOOD);
        setCurrentTexture(pickRoof().getId());
    }

    private RoofType pickRoof() {
        int i = NumberUtils.getRandomInt(3);
        if (i == 0)
            return RoofType.ORANGE;
        if (i == 1)
            return RoofType.BLUE;
        if (i == 2)
            return RoofType.RED;
        return RoofType.WHITE;
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(0, height / 2f + height / 3f, width, height / 12);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        // TODO: ADD FUNCTIONALITY
    }

    @Override
    public void interact() {
        TutLauncher.LOGGER.warn("Shop shop, shoppy time!");
        this.interacted = false;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        getTexture("stall").draw(this.renderX, this.renderY, width, height);
        getCurrentTexture().getCurrentFrame().draw(this.renderX, this.renderY, width, height / 2f);
    }

    @Override
    public Entity newCopy() {
        ShopStallEntity stall = super.newCopy(new ShopStallEntity(tutEngine));
        stall.setCurrentTexture(pickRoof().getId());
        return stall;
    }

    enum RoofType {

        ORANGE("orange"),
        BLUE("blue"),
        RED("red"),
        WHITE("white");

        private String id;

        RoofType(String id) {
            this.id = "roof_" + id;
        }

        public String getId() {
            return id;
        }
    }
}
