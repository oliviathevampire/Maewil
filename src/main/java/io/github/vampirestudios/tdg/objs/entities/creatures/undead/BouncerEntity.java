package io.github.vampirestudios.tdg.objs.entities.creatures.undead;

import io.github.vampirestudios.tdg.gfx.assets.Sounds;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityUndead;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.mini2Dx.core.game.GameContainer;

public class BouncerEntity extends EntityUndead {

    private long lastSoundTimer, soundCooldown = 800, soundTimer = soundCooldown;

    public BouncerEntity(MaewilEngine maewilEngine) {
        super(maewilEngine, "bouncer");
        this.drop = Items.BOUNCY_BALL;
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new BouncerEntity(maewilEngine));
    }

    @Override
    public void update(GameContainer container, float delta) {
        super.update(container, delta);
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
