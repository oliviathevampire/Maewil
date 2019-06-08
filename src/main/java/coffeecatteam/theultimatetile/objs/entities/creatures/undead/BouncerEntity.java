package coffeecatteam.theultimatetile.objs.entities.creatures.undead;

import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.screen.options.OptionsScreen;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class BouncerEntity extends EntityUndead {

    private long lastSoundTimer, soundCooldown = 800, soundTimer = soundCooldown;

    public BouncerEntity(TutEngine tutEngine) {
        super(tutEngine, "bouncer");
        this.drop = Items.BOUNCY_BALL;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new BouncerEntity(tutEngine));
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
            Sounds.play(Sounds.BOUNCE, OptionsScreen.OPTIONS.getVolumeHostile(), 0, 0, 1f);

        soundTimer = 0;
    }
}
