package coffeecatteam.theultimatetile.game.state.options.controls;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.gfx.ui.UIButtonControl;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.game.state.options.StateAbstractOptions;

public class OptionsControls extends StateAbstractOptions {

    public OptionsControls(GameEngine gameEngineIn) {
        super(gameEngineIn);
        initControlsButtons();
    }

    private void initControlsButtons() {
        int conBtnWidth = 3 * 64;
        int conBtnHeight = 64;

        int xOff = conBtnWidth + 10;
        int yOff = conBtnHeight + 10;

        int x = 0, y = 0;
        for (String jsonId : StateOptions.OPTIONS.controls().keySet()) {
            UIButtonControl button = new UIButtonControl(15 + xOff * x, 15 + yOff * y, conBtnWidth, conBtnHeight, StateOptions.OPTIONS.controls().get(jsonId), null);
            button.setListener(new ControlClickListener(gameEngine, button, jsonId));
            uiManager.addObject(button);

            x++;
            if (x > 2) {
                x = 0;
                y++;
            }
        }
    }
}
