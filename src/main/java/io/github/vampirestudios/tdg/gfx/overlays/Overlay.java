package io.github.vampirestudios.tdg.gfx.overlays;

import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

public abstract class Overlay {

    protected MaewilEngine maewilEngine;
    protected PlayerEntity player;

    public Overlay(MaewilEngine maewilEngine, PlayerEntity player) {
        this.maewilEngine = maewilEngine;
        this.player = player;
    }

    public abstract void update(GameContainer container, float delta);

    public abstract void render(org.mini2Dx.core.game.GameContainer container, Graphics g);
}
