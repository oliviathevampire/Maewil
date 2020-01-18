package io.github.vampirestudios.tdg.gfx.ui.button;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Animation;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.assets.Sounds;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.WidgetObject;
import io.github.vampirestudios.tdg.gfx.ui.WidgetTextBox;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class WidgetButton extends WidgetObject {

    protected MaewilEngine maewilEngine;
    protected ClickListener listener;

    private boolean disabled = false;
    private boolean hovering = false;

    private String text;
    private boolean underlined;
    private Font font;

    private boolean useImage, renderBtnBG = true, renderBtnHover = true;

    private Animation image = new Animation();
    private float imgScale = 1.0f;
    private int padding = 16;

    private Image[] currentTexture;
    protected boolean hasTooltip = false, hasCustomWidth = false;
    protected WidgetTextBox tooltip;

    private boolean centeredX, centeredY;

    /*
     * Button - Image/Animation
     */
    public WidgetButton(MaewilEngine maewilEngine, Vector2D position, Image image, ClickListener listener) {
        this(maewilEngine, position, new Animation(image), listener);
    }

    public WidgetButton(MaewilEngine maewilEngine, Vector2D position, Image image, float imgScale, ClickListener listener) {
        this(maewilEngine, position, new Animation(image), imgScale, listener);
    }

    public WidgetButton(MaewilEngine maewilEngine, Vector2D position, Image image, float imgScale, int padding, ClickListener listener) {
        this(maewilEngine, position, new Animation(image), imgScale, padding, listener);
    }

    public WidgetButton(MaewilEngine maewilEngine, Vector2D position, Animation animation, ClickListener listener) {
        this(maewilEngine, position, animation, 1.0f, listener);
    }

    public WidgetButton(MaewilEngine maewilEngine, Vector2D position, Animation animation, float imgScale, ClickListener listener) {
        this(maewilEngine, position, animation, imgScale, 16, listener);
    }

    public WidgetButton(MaewilEngine maewilEngine, Vector2D position, Animation animation, float imgScale, int padding, ClickListener listener) {
        this(maewilEngine, position, listener);
        this.useImage = true;
        this.image = animation;
        this.imgScale = imgScale;
        this.padding = padding;
    }

    /*
     * Button - Text
     */
    public WidgetButton(MaewilEngine maewilEngine, boolean centered, String text, ClickListener listener) {
        this(maewilEngine, new Vector2D(), text, false, Assets.FONTS.get("40"), listener);
        this.centeredX = centeredY = centered;
    }

    public WidgetButton(MaewilEngine maewilEngine, boolean centeredX, int y, String text, ClickListener listener) {
        this(maewilEngine, new Vector2D(0, y), text, false, Assets.FONTS.get("40"), listener);
        this.centeredX = centeredX;
    }

    public WidgetButton(MaewilEngine maewilEngine, int x, boolean centeredY, String text, ClickListener listener) {
        this(maewilEngine, new Vector2D(x, 0), text, false, Assets.FONTS.get("40"), listener);
        this.centeredY = centeredY;
    }

    public WidgetButton(MaewilEngine maewilEngine, Vector2D position, String text, ClickListener listener) {
        this(maewilEngine, position, text, false, Assets.FONTS.get("40"), listener);
    }

    public WidgetButton(MaewilEngine maewilEngine, Vector2D position, String text, boolean underlined, Font font, ClickListener listener) {
        this(maewilEngine, position, listener);
        this.useImage = false;
        this.text = text;
        this.underlined = underlined;
        this.font = font;
    }

    /*
     * Button - Base
     */
    WidgetButton(MaewilEngine maewilEngine, Vector2D position, ClickListener listener) {
        super(position, 0, 0);
        this.maewilEngine = maewilEngine;
        this.listener = listener;
        this.currentTexture = Assets.GUI_BUTTON_ENABLED;

        this.centeredX = centeredY = false;
        tooltip = new WidgetTextBox(position);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
        this.hovering = this.bounds.contains(maewilEngine.getMousePos()) && !this.disabled;

        if (this.hovering) {
            this.currentTexture = Assets.GUI_BUTTON_HOVER;
        } else {
            this.currentTexture = Assets.GUI_BUTTON_ENABLED;
        }
        if (this.disabled)
            this.currentTexture = Assets.GUI_BUTTON_DISABLED;

        listener.update(container, game, delta);
        bounds = new AABB(position, (int) getWidth(), (int) getHeight());
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
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

            if (OptionsScreen.OPTIONS.debugMode()) {
                g.setColor(Color.red);
                g.drawRect((int) position.x, (int) position.y, width, height);
            }

            if (hasCustomWidth)
                textX = (int) this.position.x + sectionWidth / 4f + (width / 2 - textWidth / 2f);
            Text.drawString(g, this.text, (int) textX, (int) textY + textHeight, false, underlined, Color.gray, font);
        }

        if (centeredX)
            this.position.x = MaewilLauncher.WIDTH / 2d - this.width / 2d;
        if (centeredY)
            this.position.y = MaewilLauncher.HEIGHT / 2d - this.height / 2d;
    }

    @Override
    public void postRender(GameContainer container, StateBasedGame game, Graphics g) {
        super.postRender(container, game, g);
        if (isHovering() && hasTooltip) {
            float x = (float) maewilEngine.getMousePos().x, x1 = x + this.tooltip.getWidth(), xDiff = 0;
            float y = (float) maewilEngine.getMousePos().y, y1 = y + this.tooltip.getHeight(), yDiff = 0;

            if (x1 > MaewilLauncher.WIDTH) xDiff = x1 - MaewilLauncher.WIDTH;
            if (y1 > MaewilLauncher.HEIGHT) yDiff = y1 - MaewilLauncher.HEIGHT;

            tooltip.setPosition(new Vector2D(x - xDiff, y - yDiff));
            tooltip.render(container, game, g);
        }
    }

    public WidgetButton setCustomWidth(int width) {
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

    public WidgetButton setDisabled(boolean disabled) {
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

    public boolean isUseImage() {
        return useImage;
    }

    public void setUseImage(boolean useImage) {
        this.useImage = useImage;
    }

    public boolean isHasTooltip() {
        return hasTooltip;
    }

    public void setHasTooltip(boolean hasTooltip) {
        this.hasTooltip = hasTooltip;
    }

    public WidgetTextBox getTooltip() {
        return tooltip;
    }

    public void setTooltip(WidgetTextBox tooltip) {
        this.tooltip = tooltip;
    }
}
