package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    protected static TheUltimateTile theUltimateTile;

    public State(TheUltimateTile theUltimateTileIn) {
        theUltimateTile = theUltimateTileIn;
    }

    public abstract void init();

    public abstract void tick();

    public abstract void render(Graphics g);

    public static void setState(State state) {
        currentState = state;
        theUltimateTile.getMouseManager().setUiManager(null);
        currentState.init();
    }

    public static State getState() {
        return currentState;
    }
}
