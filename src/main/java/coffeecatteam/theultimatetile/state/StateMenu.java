package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIManager;
import coffeecatteam.theultimatetile.utils.Logger;

import java.awt.*;

public class StateMenu extends State {

    private UIManager uiManager;

    public StateMenu(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        init();

        int yOff = 20;

        int spBtnWidth = 7 * 64;
        int spBtnHeight = 64;
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - spBtnWidth / 2, handler.getHeight() / 2 - spBtnHeight / 2 + spBtnHeight - 50 + yOff, spBtnWidth, spBtnHeight, "Single Player", () -> {
            handler.getMouseManager().setUiManager(null);
            State.setState(handler.getGame().gameState.init());
        }));

        int mpBtnWidth = 6 * 64;
        int mpBtnHeight = 64;
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - mpBtnWidth / 2, handler.getHeight() / 2 - mpBtnHeight / 2 + mpBtnHeight + 35 + yOff, mpBtnWidth, mpBtnHeight, "Multiplayer", () -> {
            handler.getMouseManager().setUiManager(null);
            State.setState(handler.getGame().gameState.init());
        }));

        int quitBtnWidth = 192;
        int quitBtnHeight = 64;
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - quitBtnWidth / 2, handler.getHeight() / 2 - quitBtnHeight / 2 + quitBtnHeight + 120 + yOff, quitBtnWidth, quitBtnHeight, "Quit", () -> {
            Logger.print("Exiting...");
            System.exit(0);
        }));
    }

    @Override
    public State init() {
        handler.getMouseManager().setUiManager(uiManager);
        return this;
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, handler.getWidth(), handler.getHeight(), null);
        uiManager.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        g.drawImage(Assets.TITLE, w / 6, 20, w, h, null);
    }
}
