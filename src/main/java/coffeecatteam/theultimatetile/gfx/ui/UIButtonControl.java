package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.state.options.Keybinds;

public class UIButtonControl extends UIButton {

    private Keybinds keybinds;

    public UIButtonControl(float x, float y, int width, int height, Keybinds keybinds, ClickListener listener) {
        super(x, y, width, height, keybinds.toString().split(":")[2], listener);
        this.keybinds = keybinds;
    }

    public Keybinds getKeybinds() {
        return keybinds;
    }

    public void setKeybinds(Keybinds keybinds) {
        this.keybinds = keybinds;
    }
}
