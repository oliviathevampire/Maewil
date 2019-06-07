package coffeecatteam.theultimatetile.screen.options.controls;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.ui.button.WidgetButtonControl;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.screen.options.AbstractOptions;
import coffeecatteam.theultimatetile.screen.options.OptionsScreen;

public class ControlOptions extends AbstractOptions {

    public ControlOptions(TutEngine tutEngineIn) {
        super(tutEngineIn);
        initControlsButtons();
    }

    private void initControlsButtons() {
        int conBtnWidth = 3 * 64;
        int conBtnHeight = 64;

        int xOff = conBtnWidth + 10;
        int yOff = conBtnHeight + 10;

        int x = 0, y = 0;
        for (String jsonId : OptionsScreen.OPTIONS.controls().keySet()) {
            WidgetButtonControl button = new WidgetButtonControl(tutEngine, new Vector2D(15 + xOff * x, 15 + yOff * y), OptionsScreen.OPTIONS.controls().get(jsonId), null);
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
