package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Overlay {

    protected TutEngine tutEngine;
    protected EntityPlayer player;

    public Overlay(TutEngine tutEngine, EntityPlayer player) {
        this.tutEngine = tutEngine;
        this.player = player;
    }

    public abstract void update(GameContainer container, StateBasedGame game, int delta);

    public abstract void render(GameContainer container, StateBasedGame game, Graphics g);
}
