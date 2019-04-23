package coffeecatteam.theultimatetile.gfx.ui.hyperlink;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIObject;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class UIHyperlink extends UIObject {

    private ClickListener listener;

    private boolean hovering = false;
    public static Color mainColor = Color.white, hoverColor = Color.cyan;
    private Color c = mainColor;

    private String text;
    private Font font;

    public UIHyperlink(Vector2D position, float height, String text, Font font, ClickListener listener) {
        super(position, 0, height);
        this.listener = listener;

        this.text = text;
        this.font = font;
        bounds = new AABB((int) this.position.x, (int) this.position.y, (int) width, (int) height);
    }

    public UIHyperlink setColors(Color mainColor, Color hoverColor) {
        this.mainColor = mainColor;
        this.hoverColor = hoverColor;
        return this;
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
        this.hovering = this.bounds.contains(TutEngine.getTutEngine().getMousePos());

        if (this.hovering)
            this.c = hoverColor;
        else
            this.c = mainColor;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        width = Text.getWidth(text, font);
        bounds.width = (int) width;
        Text.drawString(g, text, (int) this.position.x, (int) this.position.y + Text.getHeight(text, font), false, this.c, font);
    }

    @Override
    public void onClick() {
        this.listener.onClick();
        Sounds.CLICK_1.play(0.5f, 0.2f);
    }
}
