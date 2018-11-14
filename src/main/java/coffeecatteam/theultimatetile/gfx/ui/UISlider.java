package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.game.GameEngine;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UISlider extends UIObject {

    public UISlider(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

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

    class Slider {

        private GameEngine gameEngine;
        private int x, y;
        private int width, height;

        private Rectangle bounds;

        public Slider(GameEngine gameEngine, int x, int y, int width, int height) {
            this.gameEngine = gameEngine;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            bounds = new Rectangle(x, y, width, height);
        }

        public void tick() {
            bounds = new Rectangle(x, y, width, height);
        }

        public boolean isMouseInBounds() {
            return bounds.contains(gameEngine.getMouseManager().getMouseX(), gameEngine.getMouseManager().getMouseY());
        }
    }
}
