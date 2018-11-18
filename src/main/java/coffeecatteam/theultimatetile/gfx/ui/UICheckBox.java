package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UICheckBox extends UIObject {

    private boolean checked;
    private static int size = 30;

    public UICheckBox(float x, float y) {
        this(x, y, false);
    }

    public UICheckBox(float x, float y, boolean checked) {
        super(x, y, size, size);
        this.checked = checked;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.CHECK_BOX_BG, (int) x, (int) y, width, height, null);
        if (checked)
            g.drawImage(Assets.CHECK_BOX_FG, (int) x, (int) y, width, height, null);
    }

    @Override
    public void onClick() {
        checked = !checked;
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
    }

    @Override
    public void onMouseRelease(MouseEvent e) {
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
