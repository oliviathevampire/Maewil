package coffeecatteam.theultimatetile.overlays;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Overlay {

    protected TutEngine tutEngine;
    protected EntityPlayer player;

    public Overlay(TutEngine tutEngine, EntityPlayer player) {
        this.tutEngine = tutEngine;
        this.player = player;
    }

    public abstract void update(GameContainer container, int delta);

    public abstract void render(Graphics g);
}
