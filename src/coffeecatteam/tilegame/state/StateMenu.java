package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.ui.ClickListener;
import coffeecatteam.tilegame.ui.UIImageButton;
import coffeecatteam.tilegame.ui.UIManager;

import java.awt.*;

public class StateMenu extends State {

    private UIManager uiManager;

    public StateMenu(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        int btnWidth = 384;
        int btnHeight = 128;
        uiManager.addObject(new UIImageButton(handler.getWidth() / 2 - btnWidth / 2, handler.getHeight() / 2 - btnHeight / 2, btnWidth, btnHeight, Assets.BUTTON_START, () -> {
                handler.getMouseManager().setUiManager(null);
                State.setState(handler.getGame().gameState); // P.31
        }));
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        uiManager.render(g);
    }
}
