package coffeecatteam.theultimatetile.gfx.ui.hyperlink;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class UIHyperlink extends UIObject {

    private ClickListener listener;

    private boolean hovering = false;
    public Color mainColor = Color.white, hoverColor = Color.cyan;
    private Color c = mainColor;

    private String text;
    private Font font;

    public UIHyperlink(Vector2D position, int height, String text, Font font, ClickListener listener) {
        super(position, 0, height);
        this.listener = listener;

        this.text = text;
        this.font = font;
        bounds = new AABB((int) this.position.x, (int) this.position.y, width, height);
    }

    public UIHyperlink setColors(Color mainColor, Color hoverColor) {
        this.mainColor = mainColor;
        this.hoverColor = hoverColor;
        return this;
    }

    @Override
    public void update(GameContainer container, int delta) {
        super.update(container, delta);
        this.hovering = this.bounds.contains(TutEngine.getTutEngine().getMouseX(), TutEngine.getTutEngine().getMouseY());

        if (this.hovering)
            this.c = hoverColor;
        else
            this.c = mainColor;
    }

    @Override
    public void render(Graphics g) {
        width = Text.getWidth(text, font);
        bounds.width = width;
        Text.drawString(g, text, (int) this.position.x, (int) this.position.y + Text.getHeight(text, font), false, this.c, font);
    }

    @Override
    public void onClick() {
        this.listener.onClick();
    }
}
