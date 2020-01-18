package io.github.vampirestudios.tdg.screen;

import io.github.vampirestudios.tdg.start.MaewilEngine;

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
        MaewilEngine.INSTANCE.getCamera().setXOffset(0);
        MaewilEngine.INSTANCE.getCamera().setYOffset(0);

        ScreenManager.currentScreen = newScreen;
        ScreenManager.currentScreen.init();
    }
}
