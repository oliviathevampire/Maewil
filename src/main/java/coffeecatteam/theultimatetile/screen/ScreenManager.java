package coffeecatteam.theultimatetile.screen;

import coffeecatteam.theultimatetile.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 4/02/2019
 */
public class ScreenManager {

    private static Screen currentScreen = null;

    public static Screen getCurrentScreen() {
        return ScreenManager.currentScreen;
    }

    public static void setCurrentScreen(Screen newScreen) {
        TutEngine.INSTANCE.getCamera().setXOffset(0);
        TutEngine.INSTANCE.getCamera().setYOffset(0);

        ScreenManager.currentScreen = newScreen;
        ScreenManager.currentScreen.init();
    }
}
