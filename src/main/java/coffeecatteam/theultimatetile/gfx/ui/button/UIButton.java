package coffeecatteam.theultimatetile.gfx.ui.button;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIObject;
import coffeecatteam.theultimatetile.gfx.ui.UITextBox;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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
    protected boolean hasTooltip = false, hasCustomWidth = false;
    protected UITextBox tooltip;

    private boolean centeredX, centeredY;

    public UIButton(Engine engine, boolean centered, String text, ClickListener listener) {
        this(engine, new Vector2D(), text, false, Assets.FONTS.get("40"), listener);
        this.centeredX = centeredY = centered;
    }

    public UIButton(Engine engine, boolean centeredX, int y, String text, ClickListener listener) {
        this(engine, new Vector2D(0, y), text, false, Assets.FONTS.get("40"), listener);
        this.centeredX = centeredX;
    }

    public UIButton(Engine engine, int x, boolean centeredY, String text, ClickListener listener) {
        this(engine, new Vector2D(x, 0), text, false, Assets.FONTS.get("40"), listener);
        this.centeredY = centeredY;
    }

    public UIButton(Engine engine, Vector2D position, String text, ClickListener listener) {
        this(engine, position, text, false, Assets.FONTS.get("40"), listener);
    }

    public UIButton(Engine engine, Vector2D position, String text, boolean underlined, Font font, ClickListener listener) {
        super(position, 0, 0);
        this.engine = engine;
        this.listener = listener;

        this.text = text;
        this.underlined = underlined;
        this.font = font;
        this.currentTexture = Assets.BUTTON_ENABLED;

        this.centeredX = centeredY = false;
        tooltip = new UITextBox(position);
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
        bounds = new AABB(position, width, height);
    }

    @Override
    public void render(Graphics2D g) {
        int textWidth = Text.getWidth(g, text, font);
        int textHeight = Text.getHeight(g, font);

        int pWidth = 64;
        int pHeight = textHeight + 16;

        int textX = (int) this.position.x + pWidth / 4;
        int textY = (int) this.position.y + textHeight + 8;

        g.drawImage(this.currentTexture[0], (int) this.position.x, (int) this.position.y, pWidth, pHeight, null);

        g.drawImage(this.currentTexture[1], textX, (int) this.position.y, (hasCustomWidth ? width : textWidth), pHeight, null);

        g.drawImage(this.currentTexture[2], textX + ((hasCustomWidth ? width : textWidth) - (pWidth / 2 + pWidth / 4)), (int) this.position.y, pWidth, pHeight, null);

        if (!hasCustomWidth)
            this.width = textWidth + pWidth / 2;
        this.height = pHeight;

        if (StateOptions.OPTIONS.debugMode()) {
            g.setColor(Color.RED);
            g.drawRect((int) position.x, (int) position.y, width, height);
        }

        if (hasCustomWidth) {
            textX = (int) this.position.x + pWidth / 4 + (width / 2 - textWidth / 2);
        }
        Text.drawString(g, this.text, textX, textY, false, underlined, Color.gray, font);

        if (centeredX)
            this.position.x = engine.getWidth() / 2d - this.width / 2d;
        if (centeredY)
            this.position.y = engine.getHeight() / 2d - this.height / 2d;
    }

    @Override
    public void postRender(Graphics2D g) {
        if (isHovering() && hasTooltip) {
            int x = mouseX, x1 = x + this.tooltip.getWidth(), xDiff = 0;
            int y = mouseY, y1 = y + this.tooltip.getHeight(), yDiff = 0;

            if (x1 > engine.getWidth()) xDiff = x1 - engine.getWidth();
            if (y1 > engine.getHeight()) yDiff = y1 - engine.getHeight();

            tooltip.setPosition(new Vector2D(x - xDiff, y - yDiff));
            tooltip.render(g);
        }
    }

    public UIButton setCustomWidth(int width) {
        this.width = width;
        this.hasCustomWidth = true;
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
