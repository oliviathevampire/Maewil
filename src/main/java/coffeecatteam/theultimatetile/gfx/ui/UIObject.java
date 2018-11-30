package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.AABB;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class UIObject {

    protected float x, y;
    protected int width, height;
    protected AABB bounds;

    public UIObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new AABB((int) x, (int) y, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public abstract void onClick();

    public abstract void onMouseMoved(MouseEvent e);

    public abstract void onMouseRelease(MouseEvent e);

    public void onMouseReleaseA(MouseEvent e) {
        onMouseRelease(e);
        if (this.bounds.contains(e.getPoint()) && e.getButton() == MouseEvent.BUTTON1)
            onClick();
    }

    public abstract void onMouseDragged(MouseEvent e);

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
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
