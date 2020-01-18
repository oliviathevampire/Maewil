package io.github.vampirestudios.tdg.gfx.ui;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class WidgetSlider extends WidgetObject {

    private MaewilEngine maewilEngine;
    private int minValue = 0, maxValue = 10, value;

    private int segWidth, startX, endX;
    private int slMinX, slMaxX;
    private Slider slider;

    public WidgetSlider(MaewilEngine maewilEngine, Vector2D position, int width) {
        this(maewilEngine, position, width, 0);
    }

    public WidgetSlider(MaewilEngine maewilEngine, Vector2D position, int width, int defaultValue) {
        super(position, width, 20);
        this.maewilEngine = maewilEngine;
        if (defaultValue < minValue) defaultValue = minValue;
        if (defaultValue > maxValue) defaultValue = maxValue;
        this.value = defaultValue;

        segWidth = 10;
        startX = (int) this.position.x + segWidth;
        endX = startX + width;
        float slWidth = 20;
        float slHeight = height * 2;

        slMinX = (int) (startX - slWidth / 2);
        slMaxX = slMinX + width;

        slider = new Slider(maewilEngine, new Vector2D(valueToX(defaultValue), (int) (this.position.y - slHeight / 4)), (int) slWidth, (int) slHeight);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
        slider.update(container, game, delta);
        bounds.y = slider.bounds.y;

        if (slider.isMouseHovering()) {
            if (maewilEngine.isLeftDown() || maewilEngine.isLeftPressed()) {
                slider.position.x = maewilEngine.getMousePos().x - slider.getWidth() / 2d;

                if (slider.position.x < slMinX) slider.position.x = slMinX;
                if (slider.position.x > slMaxX) slider.position.x = slMaxX;
            }
        }

        value = xToValue();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        Image bgSegStart = Assets.GUI_SLIDER_BAR.getSubImage(0, 0, 2, 6);
        Image bgSegMiddle = Assets.GUI_SLIDER_BAR.getSubImage(2, 0, 44, 6);
        Image bgSegEnd = Assets.GUI_SLIDER_BAR.getSubImage(46, 0, 2, 6);

        bgSegStart.draw((int) this.position.x, (int) this.position.y, segWidth, height);
        bgSegMiddle.draw(startX, (int) this.position.y, width, height);
        bgSegEnd.draw(endX, (int) this.position.y, segWidth, height);

        slider.render(container, game, g);
    }

    @Override
    public float getHeight() {
        return slider.getHeight();
    }

    @Override
    public void onClick() {
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        slider.position = new Vector2D(valueToX(value), slider.position.y);
    }

    private int valueToX(int value) {
        return (int) NumberUtils.map(value, minValue, maxValue, slMinX, slMaxX);
    }

    private int xToValue() {
        return (int) NumberUtils.map((float) slider.position.x, slMinX, slMaxX, minValue, maxValue);
    }

    class Slider {

        private MaewilEngine maewilEngine;
        public Vector2D position;
        private int width, height;

        private AABB bounds;

        public Slider(MaewilEngine maewilEngine, Vector2D position, int width, int height) {
            this.maewilEngine = maewilEngine;
            this.position = position;
            this.width = width;
            this.height = height;

            bounds = new AABB(this.position, width, height);
        }

        public void update(GameContainer container, StateBasedGame game, int delta) {
            bounds = new AABB(this.position, width, height);
        }

        public void render(GameContainer container, StateBasedGame game, Graphics g) {
            Image sbtn = (isMouseHovering() ? Assets.GUI_SLIDER_BUTTON[1] : Assets.GUI_SLIDER_BUTTON[0]);
            sbtn.draw((int) this.position.x, (int) this.position.y, width, height);
        }

        public boolean isMouseHovering() {
            return bounds.contains(maewilEngine.getMousePos());
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
