package coffeecatteam.theultimatetile.state.options.controls;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.ui.UIButtonControl;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.StateAbstractOptions;

public class OptionsControls extends StateAbstractOptions {

    public OptionsControls(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
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
            button.setListener(new ControlClickListener(theUltimateTile, button, jsonId));
            uiManager.addObject(button);

            x++;
            if (x > 2) {
                x = 0;
                y++;
            }
        }
    }
}
