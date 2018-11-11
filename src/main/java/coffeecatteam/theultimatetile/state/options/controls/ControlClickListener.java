package coffeecatteam.theultimatetile.state.options.controls;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButtonControl;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.utils.Utils;

import javax.swing.*;

public class ControlClickListener implements ClickListener {

    private GameEngine gameEngine;
    private UIButtonControl button;
    private String jsonId;

    private boolean listening = false;

    public ControlClickListener(GameEngine gameEngine, UIButtonControl button, String jsonId) {
        this.gameEngine = gameEngine;
        this.button = button;
        this.jsonId = jsonId;
    }

    @Override
    public void onClick() {
        listening = !listening;
    }

    @Override
    public void tick() {
        int newKeyCode = gameEngine.getKeyManager().getCurrentKeyPressedCode();
        String newId = Utils.getKeyPressed(gameEngine);
        Keybind keybind = StateOptions.OPTIONS.controls().get(jsonId);

        if (listening) {
            button.setText("> " + jsonId + " <");

            if (button.getText().contains(">") && button.getText().contains("<")) {
                button.setText("> " + newId + " <");
            }
            if (button.getText().equals("> " + newId + " <") && !newId.contains("~")) {
                StateOptions.OPTIONS.controls().replace(jsonId, new Keybind(newId, newKeyCode));
                gameEngine.getKeyManager().setCurrentKeyPressedCode(KeyStroke.getKeyStroke('~').getKeyCode());
                gameEngine.getKeyManager().setCurrentKeyPressedChar('~');
                listening = false;
            }
        } else {
            button.setText(keybind.getId());
        }
    }
}
