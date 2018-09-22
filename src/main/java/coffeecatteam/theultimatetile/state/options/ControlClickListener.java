package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButtonControl;
import coffeecatteam.theultimatetile.state.StateOptions;

import javax.swing.*;

public class ControlClickListener implements ClickListener {

    private TheUltimateTile theUltimateTile;
    private UIButtonControl button;
    private String jsonId;

    private boolean listening = false;

    public ControlClickListener(TheUltimateTile theUltimateTile, UIButtonControl button, String jsonId) {
        this.theUltimateTile = theUltimateTile;
        this.button = button;
        this.jsonId = jsonId;
    }

    @Override
    public void onClick() {
        listening = !listening;
    }

    @Override
    public void tick() {
        int newKeyCode = theUltimateTile.getKeyManager().getCurrentKeyPressedCode();
        String newId = String.valueOf(theUltimateTile.getKeyManager().getCurrentKeyPressedChar()).toUpperCase();
        Keybind keybind = StateOptions.OPTIONS.controls().get(jsonId);

        if (listening) {
            button.setText("> " + jsonId + " <");
        } else {
            button.setText(keybind.getId());
        }

        if (listening) {
            if (button.getText().contains(">") && button.getText().contains("<")) {
                button.setText("> " + newId + " <");
            }
            if (button.getText().equals("> " + newId + " <") && !newId.contains("~")) {
                StateOptions.OPTIONS.controls().replace(jsonId, new Keybind(newId, newKeyCode));
                theUltimateTile.getKeyManager().setCurrentKeyPressedCode(KeyStroke.getKeyStroke('~').getKeyCode());
                theUltimateTile.getKeyManager().setCurrentKeyPressedChar('~');
                listening = false;
            }
        }
    }
}
