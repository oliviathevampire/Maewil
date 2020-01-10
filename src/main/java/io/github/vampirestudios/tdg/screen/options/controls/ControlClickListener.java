package io.github.vampirestudios.tdg.screen.options.controls;

import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButtonControl;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import io.github.vampirestudios.tdg.utils.UtilsIdk;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.*;

public class ControlClickListener implements ClickListener {

    private TutEngine tutEngine;
    private WidgetButtonControl button;
    private String jsonId;

    private boolean listening = false;

    public ControlClickListener(TutEngine tutEngine, WidgetButtonControl button, String jsonId) {
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
        String newId = UtilsIdk.getKeyPressed(tutEngine);
        Keybind keybind = OptionsScreen.OPTIONS.controls().get(jsonId);

        if (listening) {
            button.setText("> " + jsonId + " <");

            if (button.getText().contains(">") && button.getText().contains("<")) {
                button.setText("> " + newId + " <");
            }
            if (button.getText().equals("> " + newId + " <") && !newId.contains("~")) {
                OptionsScreen.OPTIONS.controls().replace(jsonId, new Keybind(newId, newKeyCode, keybind.getDescription()));
                tutEngine.getKeyManager().setCurrentKeyPressedCode(KeyStroke.getKeyStroke('~').getKeyCode());
                tutEngine.getKeyManager().setCurrentKeyPressedChar('~');
                listening = false;
            }
        } else {
            button.setText(keybind.getId());
        }
    }
}
