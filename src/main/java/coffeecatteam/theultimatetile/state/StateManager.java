package coffeecatteam.theultimatetile.state;

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
        StateManager.currentState = newState;
        StateManager.currentState.init();
    }
}
