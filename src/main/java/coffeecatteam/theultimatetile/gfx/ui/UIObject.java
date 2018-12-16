package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class UIObject {

    protected Vector2D position;
    protected int width, height;
    protected AABB bounds;

    public UIObject(float x, float y, int width, int height) {
        this(new Vector2D(x, y), width, height);
    }

    public UIObject(Vector2D position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
        bounds = new AABB(this.position, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public void postRender(Graphics2D g) {
    }

    public abstract void onClick();

    public abstract void onMouseMoved(MouseEvent e);

    public abstract void onMouseRelease(MouseEvent e);

    public void onMouseReleaseA(MouseEvent e) {
        onMouseRelease(e);
        if (this.bounds.contains(e.getPoint()) && e.getButton() == MouseEvent.BUTTON1)
            onClick();
    }

    public abstract void onMouseDragged(MouseEvent e);

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
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
