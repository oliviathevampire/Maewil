package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.gfx.Text;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UIHyperlink extends UIObject {

    private ClickListener listener;

    private boolean hovering = false;
    private Color mainColor = Color.white, hoverColor = Color.cyan, c = mainColor;

    private String text;
    private boolean underlined;
    private Font font;

    public UIHyperlink(float x, float y, int width, int height, String text, boolean underlined, Font font, ClickListener listener) {
        super(x, y, width, height);
        this.listener = listener;

        this.text = text;
        this.underlined = underlined;
        this.font = font;
        bounds = new Rectangle((int) x, (int) y - height, width, height);
    }

    public UIHyperlink setColors(Color mainColor, Color hoverColor) {
        this.mainColor = mainColor;
        this.hoverColor = hoverColor;
        return this;
    }

    @Override
    public void tick() {
        if (this.hovering)
            this.c = hoverColor;
        else
            this.c = mainColor;
    }

    @Override
    public void render(Graphics g) {
        Text.drawString(g, text, (int) x, (int) y, false, underlined, c, font);
    }

    @Override
    public void onClick() {
        this.listener.onClick();
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        this.hovering = this.bounds.contains(e.getX(), e.getY());
    }

    @Override
    public void onMouseRelease(MouseEvent e) {
        if (this.hovering && e.getButton() == MouseEvent.BUTTON1)
            onClick();
    }
}
