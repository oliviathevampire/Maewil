package io.github.vampirestudios.tdg.gfx.overlays;

import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Overlay {

    protected MaewilEngine maewilEngine;
    protected PlayerEntity player;

    public Overlay(MaewilEngine maewilEngine, PlayerEntity player) {
        this.maewilEngine = maewilEngine;
        this.player = player;
    }

    public abstract void update(GameContainer container, StateBasedGame game, int delta);

    public abstract void render(GameContainer container, StateBasedGame game, Graphics g);
}
