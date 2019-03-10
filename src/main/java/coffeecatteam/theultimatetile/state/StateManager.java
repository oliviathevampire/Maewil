package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TutEngine;

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
        TutEngine.getTutEngine().getCamera().setxOffset(0);
        TutEngine.getTutEngine().getCamera().setyOffset(0);

        StateManager.currentState = newState;
        StateManager.currentState.init();
    }
}
