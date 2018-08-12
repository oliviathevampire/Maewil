package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.gfx.ui.UIButton;
import coffeecatteam.tilegame.gfx.ui.UIManager;

import java.awt.*;

public class StateMenu extends State {

    private UIManager uiManager;

    public StateMenu(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        init();

        int btnWidth = 192;
        int btnHeight = 64;
        int xOffset = 150;
        int yOffset = 150;
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - btnWidth / 2 - xOffset, handler.getHeight() / 2 - btnHeight / 2 + btnHeight - 50 + yOffset, btnWidth, btnHeight, "Start", () -> {
            handler.getMouseManager().setUiManager(null);
            State.setState(handler.getGame().gameState.init());
        }));
        uiManager.addObject(new UIButton(handler.getWidth() / 2 - btnWidth / 2 + xOffset, handler.getHeight() / 2 - btnHeight / 2 + btnHeight - 50 + yOffset, btnWidth, btnHeight, "Quit", () -> {
            System.out.println("Exiting...");
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
        g.drawImage(Assets.TITLE, w / 6, 30, w, h, null);
    }
}
