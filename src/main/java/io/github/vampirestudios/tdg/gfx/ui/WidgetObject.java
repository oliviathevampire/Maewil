package io.github.vampirestudios.tdg.gfx.ui;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import com.badlogic.gdx.graphics.Color;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

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

    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        bounds = new AABB(this.position, (int) getWidth(), (int) getHeight());
        if (MaewilEngine.INSTANCE.isLeftPressed()) {
            if (this.bounds.contains(MaewilEngine.INSTANCE.getMousePos()))
                onClick();
            else
                onClickOutBounds();
        }
    }

    public abstract void render(org.mini2Dx.core.game.GameContainer container, org.mini2Dx.core.graphics.Graphics g);

    public void postRender(GameContainer container, Graphics g) {
        if (OptionsScreen.OPTIONS.debugMode() || showBounds) {
            g.setColor(Color.RED);
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
