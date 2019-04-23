package coffeecatteam.theultimatetile.state.options.controls;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButtonControl;
import coffeecatteam.theultimatetile.state.options.StateAbstractOptions;
import coffeecatteam.theultimatetile.state.options.StateOptions;

public class OptionsControls extends StateAbstractOptions {

    public OptionsControls(TutEngine tutEngineIn) {
        super(tutEngineIn);
        initControlsButtons();
    }

    private void initControlsButtons() {
        int conBtnWidth = 3 * 64;
        int conBtnHeight = 64;

        int xOff = conBtnWidth + 10;
        int yOff = conBtnHeight + 10;

        int x = 0, y = 0;
        for (String jsonId : StateOptions.OPTIONS.controls().keySet()) {
            UIButtonControl button = new UIButtonControl(tutEngine, new Vector2D(15 + xOff * x, 15 + yOff * y), StateOptions.OPTIONS.controls().get(jsonId), null);
            button.setCustomWidth(160);
            button.setListener(new ControlClickListener(tutEngine, button, jsonId));
            uiManager.addObject(button);

            x++;
            if (x > 2) {
                x = 0;
                y++;
            }
        }
    }
}
