package coffeecatteam.tilegame.gfx.overlays;

import coffeecatteam.tilegame.Handler;

import java.awt.*;

public abstract class Overlay {

    protected Handler handler;

    public Overlay(Handler handler) {
        this.handler = handler;
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
