package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class UISlider extends UIObject {

    private TutEngine tutEngine;
    private int minValue = 0, maxValue = 10, value;

    private int segWidth, startX, endX;
    private int slMinX, slMaxX;
    private Slider slider;

    public UISlider(TutEngine tutEngine, Vector2D position, int width) {
        this(tutEngine, position, width, 0);
    }

    public UISlider(TutEngine tutEngine, Vector2D position, int width, int defaultValue) {
        super(position, width, 20);
        this.tutEngine = tutEngine;
        if (defaultValue < minValue) defaultValue = minValue;
        if (defaultValue > maxValue) defaultValue = maxValue;
        this.value = defaultValue;

        segWidth = 10;
        startX = (int) this.position.x + segWidth;
        endX = startX + width;
        int slWidth = 20;
        int slHeight = height * 2;

        slMinX = startX - slWidth / 2;
        slMaxX = slMinX + width;

        slider = new Slider(tutEngine, new Vector2D(valueToX(defaultValue), (int) (this.position.y - slHeight / 4)), slWidth, slHeight);
    }

    @Override
    public void update(GameContainer container, int delta) {
        super.update(container, delta);
        slider.update(container, delta);
        if (slider.isMouseHovering()) {
            if (tutEngine.isLeftDown() || tutEngine.isLeftPressed()) {
                slider.position.x = tutEngine.getMousePos().x - slider.getWidth() / 2d;

                if (slider.position.x < slMinX) slider.position.x = slMinX;
                if (slider.position.x > slMaxX) slider.position.x = slMaxX;
            }
        }

        value = xToValue();
    }

    @Override
    public void render(Graphics g) {
        Image bgSegStart = Assets.SLIDER_BAR.getSubImage(0, 0, 2, 6);
        Image bgSegMiddle = Assets.SLIDER_BAR.getSubImage(2, 0, 44, 6);
        Image bgSegEnd = Assets.SLIDER_BAR.getSubImage(46, 0, 2, 6);

        bgSegStart.draw((int) this.position.x, (int) this.position.y, segWidth, height);
        bgSegMiddle.draw(startX, (int) this.position.y, width, height);
        bgSegEnd.draw(endX, (int) this.position.y, segWidth, height);

        slider.render(g);
    }

    @Override
    public void onClick() {
    }

    protected void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    protected void setMaxValue(int maxValue) {
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

        private TutEngine tutEngine;
        public Vector2D position;
        private int width, height;

        private AABB bounds;

        public Slider(TutEngine tutEngine, Vector2D position, int width, int height) {
            this.tutEngine = tutEngine;
            this.position = position;
            this.width = width;
            this.height = height;

            bounds = new AABB(this.position, width, height);
        }

        public void update(GameContainer container, int delta) {
            bounds = new AABB(this.position, width, height);
        }

        public void render(Graphics g) {
            Image sbtn = (isMouseHovering() ? Assets.SLIDER_BUTTON[1] : Assets.SLIDER_BUTTON[0]);
            sbtn.draw((int) this.position.x, (int) this.position.y, width, height);
        }

        public boolean isMouseHovering() {
            return bounds.contains(tutEngine.getMousePos());
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
