package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.manager.UIManager;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    protected UIManager uiManager;
    protected static GameEngine gameEngine;

    public State(GameEngine gameEngineIn) {
        gameEngine = gameEngineIn;
        uiManager = new UIManager(gameEngine);
    }

    public void init() {
        gameEngine.getMouseManager().setUiManager(uiManager);
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public static void setState(State state) {
        currentState = state;
        gameEngine.getMouseManager().setUiManager(null);
        currentState.init();
    }

    public static State getState() {
        return currentState;
    }
}
