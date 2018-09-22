package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.state.options.Keybind;

public class UIButtonControl extends UIButton {

    private Keybind keybind;

    public UIButtonControl(float x, float y, int width, int height, Keybind keybind, ClickListener listener) {
        super(x, y, width, height, keybind.getId(), listener);
        this.keybind = keybind;
    }

    public Keybind getKeybind() {
        return keybind;
    }

    public void setKeybind(Keybind keybind) {
        this.keybind = keybind;
    }
}
