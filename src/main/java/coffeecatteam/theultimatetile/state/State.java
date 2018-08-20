package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.Handler;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    public static void setState(State state) {
        currentState = state;
        currentState.init();
    }

    public static State getState() {
        return currentState;
    }

    protected Handler handler;

    public State(Handler handler) {
        this.handler = handler;
    }

    public abstract void init();

    public abstract void tick();

    public abstract void render(Graphics g);
}
