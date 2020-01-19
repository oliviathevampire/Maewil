package io.github.vampirestudios.tdg.gfx.ui.button;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.screen.options.controls.Keybind;
import org.newdawn.slick.Color;

public class WidgetButtonControl extends WidgetButton {

    private Keybind keybind;
    private String description;

    public WidgetButtonControl(MaewilEngine maewilEngine, Vector2D position, Keybind keybind, ClickListener listener) {
        super(maewilEngine, position, keybind.getId(), 3, listener);
        this.keybind = keybind;
        this.description = keybind.getDescription();

        hasTooltip = true;
        tooltip.addText(description, Color.darkGray);
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
