package coffeecatteam.theultimatetile.gfx.ui.button;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.TutLauncher;
import coffeecatteam.theultimatetile.gfx.Animation;
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

    private boolean useImage, renderBtnBG = true, renderBtnHover = true;

    private Animation image = new Animation();
    private float imgScale;
    private int padding;

    private Image[] currentTexture;
    protected boolean hasTooltip = false, hasCustomWidth = false;
    protected UITextBox tooltip;

    private boolean centeredX, centeredY;

    /*
     * Button - Image/Animation
     */
    public UIButton(TutEngine tutEngine, Vector2D position, Image image, ClickListener listener) {
        this(tutEngine, position, new Animation(image), listener);
    }

    public UIButton(TutEngine tutEngine, Vector2D position, Image image, float imgScale, ClickListener listener) {
        this(tutEngine, position, new Animation(image), imgScale, listener);
    }

    public UIButton(TutEngine tutEngine, Vector2D position, Image image, float imgScale, int padding, ClickListener listener) {
        this(tutEngine, position, new Animation(image), imgScale, padding, listener);
    }

    public UIButton(TutEngine tutEngine, Vector2D position, Animation animation, ClickListener listener) {
        this(tutEngine, position, animation, 1.0f, listener);
    }

    public UIButton(TutEngine tutEngine, Vector2D position, Animation animation, float imgScale, ClickListener listener) {
        this(tutEngine, position, animation, imgScale, 16, listener);
    }

    public UIButton(TutEngine tutEngine, Vector2D position, Animation animation, float imgScale, int padding, ClickListener listener) {
        this(tutEngine, position, listener);
        this.useImage = true;
        this.image = animation;
        this.imgScale = imgScale;
        this.padding = padding;
    }

    /*
     * Button - Text
     */
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
        this(tutEngine, position, listener);
        this.useImage = false;
        this.text = text;
        this.underlined = underlined;
        this.font = font;
    }

    /*
     * Button - Base
     */
    UIButton(TutEngine tutEngine, Vector2D position, ClickListener listener) {
        super(position, 0, 0);
        this.tutEngine = tutEngine;
        this.listener = listener;
        this.currentTexture = Assets.BUTTON_ENABLED;

        this.centeredX = centeredY = false;
        tooltip = new UITextBox(position);
    }

    @Override
    public void update(GameContainer container, int delta) {
        super.update(container, delta);
        this.hovering = this.bounds.contains(tutEngine.getMousePos()) && !this.disabled;
        if (useImage) this.image.update();

        if (this.hovering) {
            this.currentTexture = Assets.BUTTON_HOVER;
        } else {
            this.currentTexture = Assets.BUTTON_ENABLED;
        }
        if (this.disabled)
            this.currentTexture = Assets.BUTTON_DISABLED;

        listener.update(container, delta);
        bounds = new AABB(position, (int) width, (int) height);
    }

    @Override
    public void render(Graphics g) {
        int sectionWidth = 64;
        if (useImage) {
            Image current = image.getCurrentFrame();

            float pad = this.padding * 2;
            float imgX = (float) (this.position.x + this.padding);
            float imgWidth = current.getWidth() * this.imgScale;
            float imgHeight = current.getHeight() * this.imgScale;

            this.width = imgWidth + pad;
            this.height = imgHeight + pad;
            float midWidth = imgWidth - sectionWidth + this.padding * 1.5f;

            if (renderBtnBG) {
                this.currentTexture[0].draw((int) this.position.x, (int) this.position.y, sectionWidth, this.height);
                this.currentTexture[1].draw(imgX, (int) this.position.y, midWidth, this.height);
                this.currentTexture[2].draw(imgX + midWidth, (int) this.position.y, sectionWidth, this.height);
            }

            current.draw((int) this.position.x + this.padding, (int) this.position.y + this.padding, imgWidth, imgHeight);
            if (hovering && renderBtnHover) {
                g.setColor(new Color(101, 189, 171, 108));
                g.fillRect((float) this.position.x + this.padding, (float) (this.position.y + this.padding), imgWidth, imgHeight);
            }
        } else {
            float textWidth = Text.getWidth(text, font);
            float textHeight = Text.getHeight(text, font);

            float pHeight = textHeight + 16;

            float textX = (float) (this.position.x + sectionWidth / 4f);
            float textY = (float) (this.position.y + 8);

            this.currentTexture[0].draw((int) this.position.x, (int) this.position.y, sectionWidth, pHeight);
            this.currentTexture[1].draw(textX, (int) this.position.y, (hasCustomWidth ? width : textWidth), pHeight);
            this.currentTexture[2].draw(textX + ((hasCustomWidth ? width : textWidth) - (sectionWidth / 2f + sectionWidth / 4f)), (int) this.position.y, sectionWidth, pHeight);

            if (!hasCustomWidth)
                this.width = textWidth + sectionWidth / 2f;
            this.height = pHeight;

            if (StateOptions.OPTIONS.debugMode()) {
                g.setColor(Color.red);
                g.drawRect((int) position.x, (int) position.y, width, height);
            }

            if (hasCustomWidth)
                textX = (int) this.position.x + sectionWidth / 4f + (width / 2 - textWidth / 2f);
            Text.drawString(g, this.text, (int) textX, (int) textY + textHeight, false, underlined, Color.gray, font);
        }

        if (centeredX)
            this.position.x = TutLauncher.WIDTH / 2d - this.width / 2d;
        if (centeredY)
            this.position.y = TutLauncher.HEIGHT / 2d - this.height / 2d;
    }

    @Override
    public void postRender(Graphics g) {
        if (isHovering() && hasTooltip) {
            float x = (float) tutEngine.getMousePos().x, x1 = x + this.tooltip.getWidth(), xDiff = 0;
            float y = (float) tutEngine.getMousePos().y, y1 = y + this.tooltip.getHeight(), yDiff = 0;

            if (x1 > TutLauncher.WIDTH) xDiff = x1 - TutLauncher.WIDTH;
            if (y1 > TutLauncher.HEIGHT) yDiff = y1 - TutLauncher.HEIGHT;

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
        if (!disabled) {
            Sounds.CLICK_1.play(0.5f, 0.2f);
            this.listener.onClick();
        }
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

    public void setRenderBtnBG(boolean renderBtnBG) {
        this.renderBtnBG = renderBtnBG;
    }

    public void setRenderBtnHover(boolean renderBtnHover) {
        this.renderBtnHover = renderBtnHover;
    }

    public Animation getImage() {
        return image;
    }

    public void setImage(Animation image) {
        this.image = image;
    }

    public float getImgScale() {
        return imgScale;
    }

    public void setImgScale(float imgScale) {
        this.imgScale = imgScale;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }
}
