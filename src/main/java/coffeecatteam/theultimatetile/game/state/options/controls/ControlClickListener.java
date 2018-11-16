package coffeecatteam.theultimatetile.game.state.options.controls;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButtonControl;
import coffeecatteam.theultimatetile.utils.Utils;

import javax.swing.*;

public class ControlClickListener implements ClickListener {

    private Engine engine;
    private UIButtonControl button;
    private String jsonId;

    private boolean listening = false;

    public ControlClickListener(Engine engine, UIButtonControl button, String jsonId) {
        this.engine = engine;
        this.button = button;
        this.jsonId = jsonId;
    }

    @Override
    public void onClick() {
        listening = !listening;
    }

    @Override
    public void tick() {
        int newKeyCode = engine.getKeyManager().getCurrentKeyPressedCode();
        String newId = Utils.getKeyPressed(engine);
        Keybind keybind = StateOptions.OPTIONS.controls().get(jsonId);

        if (listening) {
            button.setText("> " + jsonId + " <");

            if (button.getText().contains(">") && button.getText().contains("<")) {
                button.setText("> " + newId + " <");
            }
            if (button.getText().equals("> " + newId + " <") && !newId.contains("~")) {
                StateOptions.OPTIONS.controls().replace(jsonId, new Keybind(newId, newKeyCode));
                engine.getKeyManager().setCurrentKeyPressedCode(KeyStroke.getKeyStroke('~').getKeyCode());
                engine.getKeyManager().setCurrentKeyPressedChar('~');
                listening = false;
            }
        } else {
            button.setText(keybind.getId());
        }
    }
}
