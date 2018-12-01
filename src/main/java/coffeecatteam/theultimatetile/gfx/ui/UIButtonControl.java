package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.state.options.controls.Keybind;

public class UIButtonControl extends UIButton {

    private Keybind keybind;

    public UIButtonControl(Engine engine, Vector2D position, Keybind keybind, ClickListener listener) {
        super(engine, position, keybind.getId(), listener);
        this.keybind = keybind;
    }

    public Keybind getKeybind() {
        return keybind;
    }

    public void setKeybind(Keybind keybind) {
        this.keybind = keybind;
    }
}
