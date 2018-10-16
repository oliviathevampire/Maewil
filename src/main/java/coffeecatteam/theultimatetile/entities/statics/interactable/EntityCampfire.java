package coffeecatteam.theultimatetile.entities.statics.interactable;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.utils.Logger;

import java.awt.*;

public class EntityCampfire extends EntityStatic {

    private Animation anim;

    public EntityCampfire(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.WOOD);
        anim = new Animation(135, Assets.CAMPFIRE);
    }

    @Override
    public void tick() {
        anim.tick();
    }

    @Override
    public void interact() {
        Logger.print("Cook Cook!");
        this.interacted = false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(anim.getCurrentFrame(), this.renderX, this.renderY, width, height, null);
    }
}
