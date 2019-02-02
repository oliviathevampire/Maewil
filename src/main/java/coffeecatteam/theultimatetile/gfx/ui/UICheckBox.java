package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.Graphics;

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
    public void render(Graphics g) {
        Assets.CHECK_BOX_BG.draw((int) position.x, (int) position.y, width, height);
        if (checked)
            Assets.CHECK_BOX_FG.draw((int) position.x, (int) position.y, width, height);
    }

    @Override
    public void onClick() {
        checked = !checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
