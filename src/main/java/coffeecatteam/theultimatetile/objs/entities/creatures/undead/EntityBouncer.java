package coffeecatteam.theultimatetile.objs.entities.creatures.undead;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.state.options.StateOptions;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class EntityBouncer extends EntityUndead {

    private long lastSoundTimer, soundCooldown = 800, soundTimer = soundCooldown;

    public EntityBouncer(TutEngine tutEngine) {
        super(tutEngine, "bouncer");
        this.drop = Items.BOUNCY_BALL;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityBouncer(tutEngine));
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
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
