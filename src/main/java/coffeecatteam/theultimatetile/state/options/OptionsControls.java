package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIButtonControl;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
import coffeecatteam.theultimatetile.state.State;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class OptionsControls extends State {

    public OptionsControls(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
        initControlsButtons();

        int exBtnWidth = 3 * 64;
        int exBtnHeight = 64;
        uiManager.addObject(new UIButton(15, theUltimateTile.getHeight() - exBtnHeight - 35, exBtnWidth, exBtnHeight, "Back", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(theUltimateTile.stateOptions);
                Keybinds.load();
            }

            @Override
            public void tick() {
            }
        }));

        int saBtnWidth = 3 * 64;
        int saBtnHeight = 64;
        uiManager.addObject(new UIButton(30 + saBtnWidth, theUltimateTile.getHeight() - saBtnHeight - 35, saBtnWidth, saBtnHeight, "Save", new ClickListener() {
            @Override
            public void onClick() {
                Keybinds.save();
            }

            @Override
            public void tick() {
            }
        }));

        Font font = Assets.FONT_20;
        String crText = "Copyright (C) CoffeeCatTeam 2018";
        int crWidth = Text.getWidth(theUltimateTile.getGraphics(), crText, font);
        int crHeight = Text.getHeight(theUltimateTile.getGraphics(), font);
        int crx = 5;
        int cry = theUltimateTile.getHeight() - 10;
        uiManager.addObject(new UIHyperlink(crx, cry, crWidth, crHeight, crText, true, font, new ClickListener() {
            @Override
            public void onClick() {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI link = new URI("https://github.com/CoffeeCatRailway/TheUltimateTile/blob/master/LICENSE.md");
                    desktop.browse(link);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void tick() {
            }
        }));
    }

    private void initControlsButtons() {
        int conBtnWidth = 3 * 64;
        int conBtnHeight = 64;

        int xOff = conBtnWidth + 10;
        int yOff = conBtnHeight + 10;

        int x = 0, y = 0;
        for (Keybinds keybind : Keybinds.KEYBINDS) {
            UIButtonControl btn = new UIButtonControl(15 + xOff * x, 15 + yOff * y, conBtnWidth, conBtnHeight, keybind, null);
            btn.setListener(new ControlClickListener(btn));
            uiManager.addObject(btn);

            x++;
            if (x > 2) {
                x = 0;
                y++;
            }
        }
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, theUltimateTile.getWidth(), theUltimateTile.getHeight(), null);
        uiManager.render(g);
    }

    private class ControlClickListener implements ClickListener {

        private UIButtonControl button;
        private boolean listening = false;
        private String ogText;
        private Keybinds key;

        public ControlClickListener(UIButtonControl button) {
            this.button = button;
            ogText = button.getText();
            this.key = button.getKeybinds();
        }

        @Override
        public void onClick() {
            listening = !listening;
        }

        @Override
        public void tick() {
            int code = theUltimateTile.getKeyManager().getCurrentKeyPressedCode();
            String newText = String.valueOf(theUltimateTile.getKeyManager().getCurrentKeyPressedChar()).toUpperCase();

            if (listening) {
                button.setText("> " + ogText + " <");
            } else {
                button.setText(key.toString().split(":")[2]);
            }

            if (listening) {
                if (button.getText().contains(">") && button.getText().contains("<")) {
                    button.setText("> " + newText + " <");
                }
                if (button.getText().equals("> " + newText + " <") && !ogText.equals(newText) && !newText.contains("~")) {
                    ogText = newText;
                    key.setKeyCode(code);
                    theUltimateTile.getKeyManager().setCurrentKeyPressedCode(KeyStroke.getKeyStroke('~').getKeyCode());
                    theUltimateTile.getKeyManager().setCurrentKeyPressedChar('~');
                    listening = false;
                }
            }
        }
    }
}
