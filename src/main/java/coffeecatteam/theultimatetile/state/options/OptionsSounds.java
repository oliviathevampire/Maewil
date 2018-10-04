package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.ui.UISlider;

public class OptionsSounds extends StateAbstractOptions {

    public OptionsSounds(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
        init();

        uiManager.addObject(new UISlider(15, 15, "Music"));
    }
}
