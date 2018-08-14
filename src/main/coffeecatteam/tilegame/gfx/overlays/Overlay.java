package coffeecatteam.tilegame.gfx.overlays;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.creatures.EntityPlayer;

import java.awt.*;

public abstract class Overlay {

    protected Handler handler;
    protected EntityPlayer player;

    public Overlay(Handler handler, EntityPlayer player) {
        this.handler = handler;
        this.player = player;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
