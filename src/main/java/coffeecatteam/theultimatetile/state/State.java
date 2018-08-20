package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.Handler;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    protected static Handler handler;

    public State(Handler handlerIn) {
        handler = handlerIn;
    }

    public abstract void init();

    public abstract void tick();

    public abstract void render(Graphics g);

    public static void setState(State state) {
        currentState = state;
        handler.getMouseManager().setUiManager(null);
        currentState.init();
    }

    public static State getState() {
        return currentState;
    }
}
