package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UISlider extends UIObject {

    private Engine engine;
    private int minValue = 0, maxValue = 10, value;

    private int segWidth, startX, endX;
    private int slMinX, slMaxX;
    private Slider slider;

    public UISlider(Engine engine, Vector2D position, int width) {
        this(engine, position, width, 0);
    }

    public UISlider(Engine engine, Vector2D position, int width, int defaultValue) {
        super(position, width, 20);
        this.engine = engine;
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

        slider = new Slider(engine, new Vector2D(valueToX(defaultValue), (int) (this.position.y - slHeight / 4)), slWidth, slHeight);
    }

    @Override
    public void tick() {
        slider.tick();
        if (slider.isMouseHovering()) {
            if (engine.getMouseManager().isLeftDown() || engine.getMouseManager().isLeftPressed()) {
                slider.position.x = engine.getMouseManager().getMouseX() - slider.getWidth() / 2d;

                if (slider.position.x < slMinX) slider.position.x = slMinX;
                if (slider.position.x > slMaxX) slider.position.x = slMaxX;
            }
        }

        value = xToValue();
    }

    @Override
    public void render(Graphics2D g) {
        BufferedImage bgSegStart = Assets.SLIDER_BAR.getSubimage(0, 0, 2, 6);
        BufferedImage bgSegMiddle = Assets.SLIDER_BAR.getSubimage(2, 0, 44, 6);
        BufferedImage bgSegEnd = Assets.SLIDER_BAR.getSubimage(46, 0, 2, 6);

        g.drawImage(bgSegStart, (int) this.position.x, (int) this.position.y, segWidth, height, null);
        g.drawImage(bgSegMiddle, startX, (int) this.position.y, width, height, null);
        g.drawImage(bgSegEnd, endX, (int) this.position.y, segWidth, height, null);

        slider.render(g);
    }

    @Override
    public void onClick() {
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
    }

    @Override
    public void onMouseRelease(MouseEvent e) {
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
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

        private Engine engine;
        public Vector2D position;
        private int width, height;

        private AABB bounds;

        public Slider(Engine engine, Vector2D position, int width, int height) {
            this.engine = engine;
            this.position = position;
            this.width = width;
            this.height = height;

            bounds = new AABB(this.position, width, height);
        }

        public void tick() {
            bounds = new AABB(this.position, width, height);
        }

        public void render(Graphics2D g) {
            g.drawImage((isMouseHovering() ? Assets.SLIDER_BUTTON[1] : Assets.SLIDER_BUTTON[0]), (int) this.position.x, (int) this.position.y, width, height, null);
        }

        public boolean isMouseHovering() {
            return bounds.contains(engine.getMouseManager().getMouseX(), engine.getMouseManager().getMouseY());
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
