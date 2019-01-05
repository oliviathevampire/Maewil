package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.manager.UIManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

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
    }

    public abstract void update(GameContainer container, int delta);

    public abstract void render(Graphics g);

    public static void setState(State state) {
        currentState = state;
        currentState.init();
    }

    public static State getState() {
        return currentState;
    }
}
