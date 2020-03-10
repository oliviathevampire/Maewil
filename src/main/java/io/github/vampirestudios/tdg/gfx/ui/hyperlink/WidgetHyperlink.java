package io.github.vampirestudios.tdg.gfx.ui.hyperlink;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Sounds;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.WidgetObject;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class WidgetHyperlink extends WidgetObject {

    private ClickListener listener;

    private boolean hovering = false;
    public static Color mainColor = Color.white, hoverColor = Color.cyan;
    private Color c = mainColor;

    private String text;
    private Font font;

    public WidgetHyperlink(Vector2D position, float height, String text, Font font, ClickListener listener) {
        super(position, 0, height);
        this.listener = listener;

        this.text = text;
        this.font = font;
        bounds = new AABB((int) this.position.x, (int) this.position.y, (int) width, (int) height);
    }

    public WidgetHyperlink setColors(Color mainColor, Color hoverColor) {
        this.mainColor = mainColor;
        this.hoverColor = hoverColor;
        return this;
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
        this.hovering = this.bounds.contains(MaewilEngine.INSTANCE.getMousePos());

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
