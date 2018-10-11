package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class UISlider extends UIObject {

    private String title;

    protected float maxValue = 1f, v;
    private UIButton valueDown, valueUp;

    public UISlider(float x, float y, String title) {
        super(x, y, 64, 64);
        this.title = title;
        v = changeValue(0f);

        valueDown = new UIButton(x, y, width, height, "<", new ClickListener() {
            @Override
            public void onClick() {
                v = changeValue(-0.1f);
            }

            @Override
            public void tick() {
            }
        });
        valueUp = new UIButton(x, y, width, height, ">", new ClickListener() {
            @Override
            public void onClick() {
                v = changeValue(0.1f);
            }

            @Override
            public void tick() {
            }
        });
    }

    public abstract float changeValue(float amt);

    @Override
    public void tick() {
        valueDown.tick();
        valueUp.tick();
    }

    @Override
    public void render(Graphics g) {
        valueDown.render(g);

        Font font = Assets.FONT_40;
        String text = String.valueOf((int) (v * 10));
        int y = (int) (this.y + Text.getHeight(g, font) + 5);
        Text.drawString(g, text, (int) (x + 79), y, false, false, Color.lightGray, font);

        float vupx = x + 84 + Text.getWidth(g, text, font);
        valueUp.setX(vupx);
        valueUp.render(g);

        Text.drawString(g, this.title, (int) (vupx + 79), y, false, false, Color.lightGray, font);
    }

    @Override
    public void onClick() {
        valueDown.onClick();
        valueUp.onClick();
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        valueDown.onMouseMoved(e);
        valueUp.onMouseMoved(e);
    }

    @Override
    public void onMouseRelease(MouseEvent e) {
        valueDown.onMouseRelease(e);
        valueUp.onMouseRelease(e);
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        valueDown.onMouseDragged(e);
        valueUp.onMouseDragged(e);
    }
}
