package coffeecatteam.theultimatetile.entities.creatures.undead;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.audio.Sound;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.theultimatetile.state.StateOptions;

public class EntityBouncer extends EntityUndead {

    private long lastSoundTimer, soundCooldown = 800, soundTimer = soundCooldown;

    public EntityBouncer(GameEngine gameEngine, String id) {
        super(gameEngine, id);
        this.drop = ItemManager.BOUNCY_BALL;
    }

    @Override
    protected void init() {
        bounds.x = 13;
        bounds.y = 28;
        bounds.width = 34;
        bounds.height = 35;

        animIdle = new Animation(animSpeed, Assets.BOUNCER_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.BOUNCER_UP);
        animDown = new Animation(animUpDownSpeed, Assets.BOUNCER_DOWN);
        animLeft = new Animation(animSpeed, Assets.BOUNCER_LEFT);
        animRight = new Animation(animSpeed, Assets.BOUNCER_RIGHT);
    }

    @Override
    public void tick() {
        super.tick();
        moveSound();
    }

    private void moveSound() {
        soundTimer += System.currentTimeMillis() - lastSoundTimer;
        lastSoundTimer = System.currentTimeMillis();
        if (soundTimer < soundCooldown)
            return;

        if (this.xMove != 0 || this.yMove != 0)
            Sound.play(Sound.BOUNCE, StateOptions.OPTIONS.getVolumeHostile(), 0, 0, 1f);

        soundTimer = 0;
    }
}
