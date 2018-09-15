package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UIButton extends UIObject {

    private ClickListener listener;

    private boolean disabled = false;
    private boolean hovering = false;

    private String text;
    private boolean underlined;
    private Font font;

    private BufferedImage[] currentTexture;

    public UIButton(float x, float y, int width, int height, String text, ClickListener listener) {
        this(x, y, width, height, text, false, Assets.FONT_40, listener);
    }

    public UIButton(float x, float y, int width, int height, String text, boolean underlined, Font font, ClickListener listener) {
        super(x, y, width, height);
        this.listener = listener;

        this.text = text;
        this.underlined = underlined;
        this.font = font;
        this.currentTexture = Assets.BUTTON_ENABLED;
    }

    @Override
    public void tick() {
        if (this.hovering)
            this.currentTexture = Assets.BUTTON_HOVER;
        else
            this.currentTexture = Assets.BUTTON_ENABLED;
        if (this.disabled)
            this.currentTexture = Assets.BUTTON_DISABLED;

        listener.tick();
    }

    @Override
    public void render(Graphics g) {
        int i = 1;
        int pWidth = 64;
        int pHeight = 64;
        g.drawImage(this.currentTexture[0], (int) this.x, (int) this.y, pWidth, pHeight, null);
        for (int x = 2; x < this.width / 64; x++) {
            g.drawImage(this.currentTexture[1], (int) this.x + (x - 1) * pWidth, (int) this.y, pWidth, pHeight, null);
            i = x;
        }
        int xOff = 0;
        if (width <= 64)
            xOff = 54;
        g.drawImage(this.currentTexture[2], (int) this.x + i * pWidth - xOff, (int) this.y, pWidth, pHeight, null);

        Text.drawString(g, this.text, (int) this.x + this.width / 2, (int) this.y + this.height / 2, true, underlined, Color.gray, font);
    }

    @Override
    public void onClick() {
        if (!disabled)
            this.listener.onClick();
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        this.hovering = this.bounds.contains(e.getX(), e.getY()) && !this.disabled;
    }

    @Override
    public void onMouseRelease(MouseEvent e) {
        if (this.hovering && e.getButton() == MouseEvent.BUTTON1)
            onClick();
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public UIButton setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public boolean isHovering() {
        return this.hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
