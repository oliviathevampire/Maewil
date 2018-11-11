package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;

import java.awt.*;

public abstract class Overlay {

    protected GameEngine gameEngine;
    protected EntityPlayer player;

    public Overlay(GameEngine gameEngine, EntityPlayer player) {
        this.gameEngine = gameEngine;
        this.player = player;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
}
