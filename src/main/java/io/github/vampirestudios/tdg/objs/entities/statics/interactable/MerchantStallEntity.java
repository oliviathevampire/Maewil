package io.github.vampirestudios.tdg.objs.entities.statics.interactable;

import coffeecatteam.coffeecatutils.position.AABB;
import com.google.common.collect.ImmutableList;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.statics.StaticEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.math.Rands;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

public class MerchantStallEntity extends StaticEntity {

    public MerchantStallEntity(MaewilEngine maewilEngine) {
        super(maewilEngine, "merchant_stall", Entity.DEFAULT_WIDTH * 2, Entity.DEFAULT_HEIGHT * 2, HitType.WOOD);
        setCurrentTexture(pickRoof().getId());
    }

    private RoofType pickRoof() {
        return Rands.list(ImmutableList.of(RoofType.values()));
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(0, height / 2f + height / 3f, width, height / 12);
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        // TODO: ADD FUNCTIONALITY
    }

    @Override
    public void interact() {
        MaewilLauncher.LOGGER.warn("Shop shop, shoppy time!");
        this.interacted = false;
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        getTexture("merchant_stall").draw(this.renderX, this.renderY, width, height);
    }

    @Override
    public Entity newCopy() {
        MerchantStallEntity stall = super.newCopy(new MerchantStallEntity(maewilEngine));
        stall.setCurrentTexture(pickRoof().getId());
        return stall;
    }

    enum RoofType {
        DARK_ORANGE("dark_orange"),
        BLUE("blue"),
        GREEN("green"),
        PINK("pink"),
        ORANGE("orange"),
        FELDGRAU("feldgrau"),
        MAGENTA("magenta"),
        LIGHT_GREEN("light_green"),
        DARK_YELLOW("dark_yellow"),
        PURPLE("purple"),
        DARK_BROWN("dark_brown"),
        MALACHITE("malachite"),
        MAROON("maroon"),
        WHITE("white"),
        LIGHT_BLUE("light_blue"),
        LIME("lime"),
        CYAN("cyan"),
        RAZZMATAZZ("razzmatazz"),
        BROWN("brown"),
        KHAKI("khaki"),
        BEIGE("beige"),
        RED("red"),
        RAINBOW("rainbow"),
        FALU_RED("falu_red"),
        BLACK_AND_WHITE("black_and_white"),
        BABY_PINK("baby_pink"),
        BABY_BLUE("baby_blue"),
        LIGHT_GRAY("light_gray"),
        ARSENIC("arsenic"),
        DARK_GRAY("dark_gray"),
        WINE_RED("wine_red"),
        YELLOW("yellow"),
        MILITARY_GREEN("military_green"),
        TURMERIC_ORANGE("turmeric_orange");

        private String id;

        RoofType(String id) {
            this.id = "roof_" + id;
        }

        public String getId() {
            return id;
        }
    }

}