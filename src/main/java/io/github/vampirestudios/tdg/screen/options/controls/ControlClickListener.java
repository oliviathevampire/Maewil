package io.github.vampirestudios.tdg.screen.options.controls;

import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButtonControl;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.utils.UtilsIdk;

import javax.swing.*;

public class ControlClickListener implements ClickListener {

    private MaewilEngine maewilEngine;
    private WidgetButtonControl button;
    private String jsonId;

    private boolean listening = false;

    public ControlClickListener(MaewilEngine maewilEngine, WidgetButtonControl button, String jsonId) {
        this.maewilEngine = maewilEngine;
        this.button = button;
        this.jsonId = jsonId;
    }

    @Override
    public void onClick() {
        listening = !listening;
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        int newKeyCode = maewilEngine.getKeyManager().getCurrentKeyPressedCode();
        String newId = UtilsIdk.getKeyPressed(maewilEngine);
        Keybind keybind = OptionsScreen.OPTIONS.controls().get(jsonId);

        if (listening) {
            button.setText("> " + jsonId + " <");

            if (button.getText().contains(">") && button.getText().contains("<")) {
                button.setText("> " + newId + " <");
            }
            if (button.getText().equals("> " + newId + " <") && !newId.contains("~")) {
                OptionsScreen.OPTIONS.controls().replace(jsonId, new Keybind(newId, newKeyCode, keybind.getDescription()));
                maewilEngine.getKeyManager().setCurrentKeyPressedCode(KeyStroke.getKeyStroke('~').getKeyCode());
                maewilEngine.getKeyManager().setCurrentKeyPressedChar('~');
                listening = false;
            }
        } else {
            button.setText(keybind.getId());
        }
    }
}
