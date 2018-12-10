package coffeecatteam.theultimatetile.gfx.ui.button;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;

public class UIButtonControl extends UIButton {

    private Keybind keybind;
    private String description;

    public UIButtonControl(Engine engine, Vector2D position, Keybind keybind, ClickListener listener) {
        super(engine, position, keybind.getId(), listener);
        this.keybind = keybind;
        this.description = keybind.getDescription();

        hasTooltip = true;
        tooltip.addText(description);
    }

    public Keybind getKeybind() {
        return keybind;
    }

    public void setKeybind(Keybind keybind) {
        this.keybind = keybind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
