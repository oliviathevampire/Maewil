package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.manager.UIManager;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    protected UIManager uiManager;
    protected static TheUltimateTile theUltimateTile;

    public State(TheUltimateTile theUltimateTileIn) {
        theUltimateTile = theUltimateTileIn;
        uiManager = new UIManager(theUltimateTile);
    }

    public void init() {
        theUltimateTile.getMouseManager().setUiManager(uiManager);
    }

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
