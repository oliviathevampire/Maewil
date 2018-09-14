package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;

import java.awt.*;

public abstract class Overlay {

    protected TheUltimateTile theUltimateTile;
    protected EntityPlayer player;

    public Overlay(TheUltimateTile theUltimateTile, EntityPlayer player) {
        this.theUltimateTile = theUltimateTile;
        this.player = player;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public TheUltimateTile getTheUltimateTile() {
        return theUltimateTile;
    }

    public void setTheUltimateTile(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
    }
}
