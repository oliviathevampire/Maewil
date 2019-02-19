package coffeecatteam.theultimatetile.entities.creatures.undead;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.manager.ItemManager;
import org.newdawn.slick.GameContainer;

public class EntityBouncer extends EntityUndead {

    private long lastSoundTimer, soundCooldown = 800, soundTimer = soundCooldown;

    public EntityBouncer(TutEngine tutEngine, String id) {
        super(tutEngine, id);
        this.drop = ItemManager.BOUNCY_BALL;
    }

    @Override
    protected void init() {
        animIdle = new Animation(animSpeed, Assets.BOUNCER_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.BOUNCER_UP);
        animDown = new Animation(animUpDownSpeed, Assets.BOUNCER_DOWN);
        animLeft = new Animation(animSpeed, Assets.BOUNCER_LEFT);
        animRight = new Animation(animSpeed, Assets.BOUNCER_RIGHT);
    }

    @Override
    public void update(GameContainer container, int delta) {
        super.update(container, delta);
        moveSound();
    }

    private void moveSound() {
        soundTimer += System.currentTimeMillis() - lastSoundTimer;
        lastSoundTimer = System.currentTimeMillis();
        if (soundTimer < soundCooldown)
            return;

        if (this.xMove != 0 || this.yMove != 0)
            Sounds.play(Sounds.BOUNCE, StateOptions.OPTIONS.getVolumeHostile(), 0, 0, 1f);

        soundTimer = 0;
    }
}
