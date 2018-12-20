package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.manager.UIManager;

import java.awt.*;

public abstract class State {

    protected CatLogger logger;

    private static State currentState = null;

    protected UIManager uiManager;
    protected static Engine engine;

    public State(Engine engine) {
        State.engine = engine;
        this.logger = engine.getLogger();
        uiManager = new UIManager(engine);
    }

    public void init() {
        engine.getMouseManager().setUiManager(uiManager);
    }

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public static void setState(State state) {
        currentState = state;
        engine.getMouseManager().setUiManager(null);
        currentState.init();
    }

    public static State getState() {
        return currentState;
    }
}
