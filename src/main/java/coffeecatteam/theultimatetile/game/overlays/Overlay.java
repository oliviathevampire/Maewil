package coffeecatteam.theultimatetile.game.overlays;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;

import java.awt.*;

public abstract class Overlay {

    protected Engine engine;
    protected EntityPlayer player;

    public Overlay(Engine engine, EntityPlayer player) {
        this.engine = engine;
        this.player = player;
    }

    public abstract void tick();

    public abstract void render(Graphics g);
}
