package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.utils.AABB;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UISlider extends UIObject {

    private Engine engine;
    private int minValue = 0, maxValue = 10, value;

    private int segWidth, startX, endX;
    private int slMinX, slMaxX;
    private Slider slider;

    public UISlider(Engine engine, float x, float y, int width) {
        this(engine, x, y, width, 0);
    }

    public UISlider(Engine engine, float x, float y, int width, int defaultValue) {
        super(x, y, width, 20);
        this.engine = engine;
        if (defaultValue < minValue) defaultValue = minValue;
        if (defaultValue > maxValue) defaultValue = maxValue;
        this.value = defaultValue;

        segWidth = 10;
        startX = (int) x + segWidth;
        endX = startX + width;
        int slWidth = 20;
        int slHeight = height * 2;

        slMinX = startX - slWidth / 2;
        slMaxX = slMinX + width;

        slider = new Slider(engine, valueToX(defaultValue), (int) (y - slHeight / 4), slWidth, slHeight);
    }

    @Override
    public void tick() {
        slider.tick();
        if (slider.isMouseHovering()) {
            if (engine.getMouseManager().isLeftDown() || engine.getMouseManager().isLeftPressed()) {
                slider.setX(engine.getMouseManager().getMouseX() - slider.getWidth() / 2);

                if (slider.getX() < slMinX) slider.setX(slMinX);
                if (slider.getX() > slMaxX) slider.setX(slMaxX);
            }
        }

        value = xToValue();
    }

    @Override
    public void render(Graphics g) {
        BufferedImage bgSegStart = Assets.SLIDER_BAR.getSubimage(0, 0, 2, 6);
        BufferedImage bgSegMiddle = Assets.SLIDER_BAR.getSubimage(2, 0, 44, 6);
        BufferedImage bgSegEnd = Assets.SLIDER_BAR.getSubimage(46, 0, 2, 6);

        g.drawImage(bgSegStart, (int) x, (int) y, segWidth, height, null);
        g.drawImage(bgSegMiddle, startX, (int) y, width, height, null);
        g.drawImage(bgSegEnd, endX, (int) y, segWidth, height, null);

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
        slider.setX(valueToX(value));
    }

    private int valueToX(int value) {
        return (int) Utils.map(value, minValue, maxValue, slMinX, slMaxX);
    }

    private int xToValue() {
        return (int) Utils.map(slider.getX(), slMinX, slMaxX, minValue, maxValue);
    }

    class Slider {

        private Engine engine;
        private int x, y;
        private int width, height;

        private AABB bounds;

        public Slider(Engine engine, int x, int y, int width, int height) {
            this.engine = engine;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            bounds = new AABB(x, y, width, height);
        }

        public void tick() {
            bounds = new AABB(x, y, width, height);
        }

        public void render(Graphics g) {
            g.drawImage((isMouseHovering() ? Assets.SLIDER_BUTTON[1] : Assets.SLIDER_BUTTON[0]), x, y, width, height, null);
        }

        public boolean isMouseHovering() {
            return bounds.contains(engine.getMouseManager().getMouseX(), engine.getMouseManager().getMouseY());
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
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
