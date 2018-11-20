package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UIButton extends UIObject {

    protected Engine engine;
    protected ClickListener listener;

    private boolean disabled = false;
    private boolean hovering = false;
    private int mouseX = 0, mouseY = 0;

    private String text;
    private boolean underlined;
    private Font font;

    private BufferedImage[] currentTexture;
    protected boolean hasTooltip = false;

    private boolean centeredX, centeredY;

    public UIButton(Engine engine, boolean centered, String text, ClickListener listener) {
        this(engine, 0, 0, text, false, Assets.FONT_40, listener);
        this.centeredX = centeredY = centered;
    }

    public UIButton(Engine engine, boolean centeredX, int y, String text, ClickListener listener) {
        this(engine, 0, y, text, false, Assets.FONT_40, listener);
        this.centeredX = centeredX;
    }

    public UIButton(Engine engine, int x, boolean centeredY, String text, ClickListener listener) {
        this(engine, x, 0, text, false, Assets.FONT_40, listener);
        this.centeredY = centeredY;
    }

    public UIButton(Engine engine, float x, float y, String text, ClickListener listener) {
        this(engine, x, y, text, false, Assets.FONT_40, listener);
    }

    public UIButton(Engine engine, float x, float y, String text, boolean underlined, Font font, ClickListener listener) {
        super(x, y, 0, 0);
        this.engine = engine;
        this.listener = listener;

        this.text = text;
        this.underlined = underlined;
        this.font = font;
        this.currentTexture = Assets.BUTTON_ENABLED;

        this.centeredX = centeredY = false;
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
        bounds = new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public void render(Graphics g) {
        int textWidth = Text.getWidth(g, text, font);
        int textHeight = Text.getHeight(g, font);

        int pWidth = 64;
        int pHeight = textHeight + 16;

        int textX = (int) this.x + pWidth / 4;
        int textY = (int) this.y + textHeight + 8;

        g.drawImage(this.currentTexture[0], (int) this.x, (int) this.y, pWidth, pHeight, null);

        g.drawImage(this.currentTexture[1], textX, (int) this.y, textWidth, pHeight, null);

        g.drawImage(this.currentTexture[2], textX + (textWidth - (pWidth / 2 + pWidth / 4)), (int) this.y, pWidth, pHeight, null);

        this.width = textWidth + pWidth / 2;
        this.height = pHeight;

        if (StateOptions.OPTIONS.debugMode()) {
            g.setColor(Color.RED);
            g.drawRect((int) x, (int) y, width, height);
        }

        Text.drawString(g, this.text, textX, textY, false, underlined, Color.gray, font);

        if (centeredX)
            this.x = engine.getWidth() / 2 - this.width / 2;
        if (centeredY)
            this.y = engine.getHeight() / 2 - this.height / 2;

        List<String> tooltip = new ArrayList<>();
        setTooltip(tooltip);
        if (tooltip.size() > 0) {
            if (hasTooltip && this.hovering) {
                Font font = Assets.FONT_20;
                int xTOff = 40;
                int yTOff = 20;
                g.drawImage(Assets.TOOLTIP_LONG_SMALL, mouseX, mouseY, Text.getWidth(g, tooltip.get(0), font) + xTOff, Text.getHeight(g, font) * tooltip.size() + yTOff, null);
                int line = 1;
                for (String tip : tooltip) {
                    Text.drawString(g, tip, mouseX + xTOff / 2, mouseY + Text.getHeight(g, font) * line + yTOff / 2, false, false, Color.white, font);
                    line++;
                }
            }
        }
    }

    public UIButton setTooltip(List<String> tooltip) {
        return this;
    }

    @Override
    public void onClick() {
        if (!disabled)
            this.listener.onClick();
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        this.hovering = this.bounds.contains(mouseX, mouseY) && !this.disabled;
    }

    @Override
    public void onMouseRelease(MouseEvent e) {
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
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
