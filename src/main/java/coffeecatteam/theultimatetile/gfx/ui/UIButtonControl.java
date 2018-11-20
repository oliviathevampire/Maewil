package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.state.options.controls.Keybind;

public class UIButtonControl extends UIButton {

    private Keybind keybind;

    public UIButtonControl(Engine engine, float x, float y, Keybind keybind, ClickListener listener) {
        super(engine, x, y, keybind.getId(), listener);
        this.keybind = keybind;
    }

    public Keybind getKeybind() {
        return keybind;
    }

    public void setKeybind(Keybind keybind) {
        this.keybind = keybind;
    }
}
