package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class WidgetCheckBox extends WidgetObject {

    private boolean checked, useTick = true;
    private static int size = 30;

    public WidgetCheckBox(Vector2D position) {
        this(position, size);
    }

    public WidgetCheckBox(Vector2D position, boolean checked) {
        this(position, size, checked);
    }

    public WidgetCheckBox(Vector2D position, int size) {
        this(position, size, false);
    }

    public WidgetCheckBox(Vector2D position, int size, boolean checked) {
        super(position, size, size);
        this.checked = checked;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        Assets.GUI_CHECK_BOX_BG.draw((int) position.x, (int) position.y, width, height);
        if (checked) {
            Image check = useTick ? Assets.GUI_CHECK_BOX_TICK : Assets.GUI_CHECK_BOX_CROSS;
            check.draw((int) position.x, (int) position.y, width, height);
        }
    }

    @Override
    public void onClick() {
        checked = !checked;
        Sounds.CLICK_1.play(0.5f, 0.2f);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isUseTick() {
        return useTick;
    }

    public void setUseTick(boolean useTick) {
        this.useTick = useTick;
    }
}
