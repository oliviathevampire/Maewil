package coffeecatteam.theultimatetile.gfx.ui.button;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIObject;
import coffeecatteam.theultimatetile.gfx.ui.UITextBox;
import coffeecatteam.theultimatetile.state.StateOptions;
import org.newdawn.slick.*;

public class UIButton extends UIObject {

    protected TutEngine tutEngine;
    protected ClickListener listener;

    private boolean disabled = false;
    private boolean hovering = false;

    private String text;
    private boolean underlined;
    private Font font;

    private Image[] currentTexture;
    protected boolean hasTooltip = false, hasCustomWidth = false, hoverSound = false;
    protected UITextBox tooltip;

    private boolean centeredX, centeredY;

    public UIButton(TutEngine tutEngine, boolean centered, String text, ClickListener listener) {
        this(tutEngine, new Vector2D(), text, false, Assets.FONTS.get("40"), listener);
        this.centeredX = centeredY = centered;
    }

    public UIButton(TutEngine tutEngine, boolean centeredX, int y, String text, ClickListener listener) {
        this(tutEngine, new Vector2D(0, y), text, false, Assets.FONTS.get("40"), listener);
        this.centeredX = centeredX;
    }

    public UIButton(TutEngine tutEngine, int x, boolean centeredY, String text, ClickListener listener) {
        this(tutEngine, new Vector2D(x, 0), text, false, Assets.FONTS.get("40"), listener);
        this.centeredY = centeredY;
    }

    public UIButton(TutEngine tutEngine, Vector2D position, String text, ClickListener listener) {
        this(tutEngine, position, text, false, Assets.FONTS.get("40"), listener);
    }

    public UIButton(TutEngine tutEngine, Vector2D position, String text, boolean underlined, Font font, ClickListener listener) {
        super(position, 0, 0);
        this.tutEngine = tutEngine;
        this.listener = listener;

        this.text = text;
        this.underlined = underlined;
        this.font = font;
        this.currentTexture = Assets.BUTTON_ENABLED;

        this.centeredX = centeredY = false;
        tooltip = new UITextBox(position);
    }

    @Override
    public void update(GameContainer container, int delta) {
        super.update(container, delta);
        this.hovering = this.bounds.contains(tutEngine.getMouseX(), tutEngine.getMouseY()) && !this.disabled;

        if (this.hovering) {
            this.currentTexture = Assets.BUTTON_HOVER;
            if (!hoverSound)
                Sounds.CLICK_1.play(0.5f, 1);
            hoverSound = true;
        } else {
            this.currentTexture = Assets.BUTTON_ENABLED;
            hoverSound = false;
        }
        if (this.disabled)
            this.currentTexture = Assets.BUTTON_DISABLED;

        listener.update(container, delta);
        bounds = new AABB(position, width, height);
    }

    @Override
    public void render(Graphics g) {
        int textWidth = Text.getWidth(text, font);
        int textHeight = Text.getHeight(text, font);

        int pWidth = 64;
        int pHeight = textHeight + 16;

        int textX = (int) this.position.x + pWidth / 4;
        int textY = (int) this.position.y + 8;

        this.currentTexture[0].draw((int) this.position.x, (int) this.position.y, pWidth, pHeight);

        this.currentTexture[1].draw(textX, (int) this.position.y, (hasCustomWidth ? width : textWidth), pHeight);

        this.currentTexture[2].draw(textX + ((hasCustomWidth ? width : textWidth) - (pWidth / 2f + pWidth / 4f)), (int) this.position.y, pWidth, pHeight);

        if (!hasCustomWidth)
            this.width = textWidth + pWidth / 2;
        this.height = pHeight;

        if (StateOptions.OPTIONS.debugMode()) {
            g.setColor(Color.red);
            g.drawRect((int) position.x, (int) position.y, width, height);
        }

        if (hasCustomWidth) {
            textX = (int) this.position.x + pWidth / 4 + (width / 2 - textWidth / 2);
        }
        Text.drawString(g, this.text, textX, textY + textHeight, false, underlined, Color.gray, font);

        if (centeredX)
            this.position.x = tutEngine.getWidth() / 2d - this.width / 2d;
        if (centeredY)
            this.position.y = tutEngine.getHeight() / 2d - this.height / 2d;
    }

    @Override
    public void postRender(Graphics g) {
        if (isHovering() && hasTooltip) {
            int x = tutEngine.getMouseX(), x1 = x + this.tooltip.getWidth(), xDiff = 0;
            int y = tutEngine.getMouseY(), y1 = y + this.tooltip.getHeight(), yDiff = 0;

            if (x1 > tutEngine.getWidth()) xDiff = x1 - tutEngine.getWidth();
            if (y1 > tutEngine.getHeight()) yDiff = y1 - tutEngine.getHeight();

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
