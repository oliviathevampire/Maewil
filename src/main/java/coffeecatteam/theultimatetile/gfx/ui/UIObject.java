package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class UIObject {

    protected Vector2D position;
    protected float width, height;
    protected AABB bounds;

    public UIObject(float x, float y, float width, float height) {
        this(new Vector2D(x, y), width, height);
    }

    public UIObject(Vector2D position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
        bounds = new AABB(this.position, (int) width, (int) height);
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        bounds = new AABB(this.position, (int) width, (int) height);
        if (this.bounds.contains(TutEngine.getTutEngine().getMousePos()) && TutEngine.getTutEngine().isLeftPressed())
            onClick();
    }

    public abstract void render(GameContainer container, StateBasedGame game, Graphics g);

    public void postRender(GameContainer container, StateBasedGame game, Graphics g) {
    }

    public abstract void onClick();

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
