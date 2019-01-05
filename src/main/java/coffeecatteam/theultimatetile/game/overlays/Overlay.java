package coffeecatteam.theultimatetile.game.overlays;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Overlay {

    protected Engine engine;
    protected EntityPlayer player;

    public Overlay(Engine engine, EntityPlayer player) {
        this.engine = engine;
        this.player = player;
    }

    public abstract void update(GameContainer container, int delta);

    public abstract void render(Graphics g);
}
