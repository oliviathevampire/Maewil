package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.screen.options.OptionsScreen;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class WidgetObject {

    protected Vector2D position;
    protected float width, height;
    protected AABB bounds;
    protected boolean showBounds = false;

    public WidgetObject(float x, float y, float width, float height) {
        this(new Vector2D(x, y), width, height);
    }

    public WidgetObject(Vector2D position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
        bounds = new AABB(this.position, (int) width, (int) height);
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        bounds = new AABB(this.position, (int) getWidth(), (int) getHeight());
        if (TutEngine.INSTANCE.isLeftPressed()) {
            if (this.bounds.contains(TutEngine.INSTANCE.getMousePos()))
                onClick();
            else
                onClickOutBounds();
        }
    }

    public abstract void render(GameContainer container, StateBasedGame game, Graphics g);

    public void postRender(GameContainer container, StateBasedGame game, Graphics g) {
        if (OptionsScreen.OPTIONS.debugMode() || showBounds) {
            g.setColor(Color.red);
            g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public abstract void onClick();

    public void onClickOutBounds() {
    }

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
