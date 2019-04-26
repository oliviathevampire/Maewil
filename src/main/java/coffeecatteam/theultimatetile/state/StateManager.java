package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 4/02/2019
 */
public class StateManager {

    private static State currentState = null;

    public static State getCurrentState() {
        return StateManager.currentState;
    }

    public static void setCurrentState(State newState) {
        TutEngine.INSTANCE.getCamera().setxOffset(0);
        TutEngine.INSTANCE.getCamera().setyOffset(0);

        StateManager.currentState = newState;
        StateManager.currentState.init();
    }
}
