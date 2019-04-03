package coffeecatteam.theultimatetile.state.options.controls;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButtonControl;
import coffeecatteam.theultimatetile.state.options.StateOptions;
import coffeecatteam.theultimatetile.utils.Utils;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.*;

public class ControlClickListener implements ClickListener {

    private TutEngine tutEngine;
    private UIButtonControl button;
    private String jsonId;

    private boolean listening = false;

    public ControlClickListener(TutEngine tutEngine, UIButtonControl button, String jsonId) {
        this.tutEngine = tutEngine;
        this.button = button;
        this.jsonId = jsonId;
    }

    @Override
    public void onClick() {
        listening = !listening;
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        int newKeyCode = tutEngine.getKeyManager().getCurrentKeyPressedCode();
        String newId = Utils.getKeyPressed(tutEngine);
        Keybind keybind = StateOptions.OPTIONS.controls().get(jsonId);

        if (listening) {
            button.setText("> " + jsonId + " <");

            if (button.getText().contains(">") && button.getText().contains("<")) {
                button.setText("> " + newId + " <");
            }
            if (button.getText().equals("> " + newId + " <") && !newId.contains("~")) {
                StateOptions.OPTIONS.controls().replace(jsonId, new Keybind(newId, newKeyCode, keybind.getDescription()));
                tutEngine.getKeyManager().setCurrentKeyPressedCode(KeyStroke.getKeyStroke('~').getKeyCode());
                tutEngine.getKeyManager().setCurrentKeyPressedChar('~');
                listening = false;
            }
        } else {
            button.setText(keybind.getId());
        }
    }
}
