package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UICheckBox extends UIObject {

    private boolean checked;
    private static int size = 30;

    public UICheckBox(Vector2D position) {
        this(position, size);
    }

    public UICheckBox(Vector2D position, boolean checked) {
        this(position, size, checked);
    }

    public UICheckBox(Vector2D position, int size) {
        this(position, size, false);
    }

    public UICheckBox(Vector2D position, int size, boolean checked) {
        super(position, size, size);
        this.checked = checked;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.CHECK_BOX_BG, (int) position.x, (int) position.y, width, height, null);
        if (checked)
            g.drawImage(Assets.CHECK_BOX_FG, (int) position.x, (int) position.y, width, height, null);
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
