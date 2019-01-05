package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;

import coffeecatteam.theultimatetile.Engine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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

    public void update(GameContainer container, int delta) {
        if (this.bounds.contains(Engine.getEngine().getMouseX(), Engine.getEngine().getMouseY()) && Engine.getEngine().isLeftPressed())
            onClick();
    }

    public abstract void render(Graphics g);

    public void postRender(Graphics g) {
    }

    public abstract void onClick();

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
