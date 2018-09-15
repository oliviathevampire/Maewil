package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
import coffeecatteam.theultimatetile.state.State;

import java.awt.*;
import java.awt.event.KeyEvent;
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

        // Z
        UIButton btnZ = new UIButton(15, 15, conBtnWidth, conBtnHeight, "Z", null);
        btnZ.setListener(new ControlClickListener(btnZ, Keybinds.Z));
        uiManager.addObject(btnZ);

        // R
        UIButton btnR = new UIButton(15 + xOff, 15, conBtnWidth, conBtnHeight, "R", null);
        btnR.setListener(new ControlClickListener(btnR, Keybinds.R));
        uiManager.addObject(btnR);

        // E
        UIButton btnE = new UIButton(15 + xOff * 2, 15, conBtnWidth, conBtnHeight, "E", null);
        btnE.setListener(new ControlClickListener(btnE, Keybinds.E));
        uiManager.addObject(btnE);

        // Q
        UIButton btnQ = new UIButton(15, 15 + yOff, conBtnWidth, conBtnHeight, "Q", null);
        btnQ.setListener(new ControlClickListener(btnQ, Keybinds.Q));
        uiManager.addObject(btnQ);

        // W
        UIButton btnW = new UIButton(15 + xOff, 15 + yOff, conBtnWidth, conBtnHeight, "W", null);
        btnW.setListener(new ControlClickListener(btnW, Keybinds.W));
        uiManager.addObject(btnW);

        // A
        UIButton btnA = new UIButton(15 + xOff * 2, 15 + yOff, conBtnWidth, conBtnHeight, "A", null);
        btnA.setListener(new ControlClickListener(btnA, Keybinds.A));
        uiManager.addObject(btnA);

        // S
        UIButton btnS = new UIButton(15, 15 + yOff * 2, conBtnWidth, conBtnHeight, "S", null);
        btnS.setListener(new ControlClickListener(btnS, Keybinds.S));
        uiManager.addObject(btnS);

        // D
        UIButton btnD = new UIButton(15 + xOff, 15 + yOff * 2, conBtnWidth, conBtnHeight, "D", null);
        btnD.setListener(new ControlClickListener(btnD, Keybinds.D));
        uiManager.addObject(btnD);

        // ONE
        UIButton btnONE = new UIButton(15 + xOff * 2, 15 + yOff * 2, conBtnWidth, conBtnHeight, "ONE", null);
        btnONE.setListener(new ControlClickListener(btnONE, Keybinds.ONE));
        uiManager.addObject(btnONE);

        // TWO
        UIButton btnTWO = new UIButton(15, 15 + yOff * 3, conBtnWidth, conBtnHeight, "TWO", null);
        btnTWO.setListener(new ControlClickListener(btnTWO, Keybinds.TWO));
        uiManager.addObject(btnTWO);

        // THREE
        UIButton btnTHREE = new UIButton(15 + xOff, 15 + yOff * 3, conBtnWidth, conBtnHeight, "THREE", null);
        btnTHREE.setListener(new ControlClickListener(btnTHREE, Keybinds.THREE));
        uiManager.addObject(btnTHREE);

        // CONTROL
        UIButton btnCONTROL = new UIButton(15 + xOff * 2, 15 + yOff * 3, conBtnWidth, conBtnHeight, "CONTROL", null);
        btnCONTROL.setListener(new ControlClickListener(btnCONTROL, Keybinds.CONTROL));
        uiManager.addObject(btnCONTROL);

        // SPACE
        UIButton btnSPACE = new UIButton(15, 15 + yOff * 4, conBtnWidth, conBtnHeight, "SPACE", null);
        btnSPACE.setListener(new ControlClickListener(btnSPACE, Keybinds.SPACE));
        uiManager.addObject(btnSPACE);
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

        private UIButton button;
        private boolean listening = false;
        private String ogText;
        private Keybinds key;

        public ControlClickListener(UIButton button, Keybinds key) {
            this.button = button;
            ogText = button.getText();
            this.key = key;
        }

        @Override
        public void onClick() {
            listening = !listening;
        }

        @Override
        public void tick() {
            if (listening) {
                button.setText("> ~ <");
            } else {
                button.setText(ogText);
            }

            if (listening) {
                int code = theUltimateTile.getKeyManager().getCurrentKeyPressedCode();
                String newText = String.valueOf(theUltimateTile.getKeyManager().getCurrentKeyPressedChar()).toUpperCase();
                if (button.getText().contains(">") && button.getText().contains("<")) {
                    button.setText("> " + newText + " <");
                }
                if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
                    ogText = newText;
                    key.setKey(code);
                    listening = false;
                }
            }
        }
    }
}
