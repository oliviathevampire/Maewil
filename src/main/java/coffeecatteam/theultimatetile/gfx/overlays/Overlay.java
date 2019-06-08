package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.objs.entities.creatures.PlayerEntity;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Overlay {

    protected TutEngine tutEngine;
    protected PlayerEntity player;

    public Overlay(TutEngine tutEngine, PlayerEntity player) {
        this.tutEngine = tutEngine;
        this.player = player;
    }

    public abstract void update(GameContainer container, StateBasedGame game, int delta);

    public abstract void render(GameContainer container, StateBasedGame game, Graphics g);
}
