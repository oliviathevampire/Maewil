package io.github.vampirestudios.tdg.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.ui.hyperlink.WidgetHyperlink;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

public class WidgetInputBox extends AbstractListTheme {

    private String text = "";
    private boolean selected = false;

    public WidgetInputBox(float x, float y, float width) {
        this(new Vector2D(x, y), width);
    }

    public WidgetInputBox(float x, float y, float width, String text) {
        this(new Vector2D(x, y), width);
        this.setText(text);
    }

    public WidgetInputBox(Vector2D position, float width) {
        super(position, width, SIZE);
    }

    @Override
    public void update(GameContainer container, float delta) {
        super.update(container, delta);
        if (selected) {
            text += MaewilEngine.INSTANCE.getKeyJustPressed();
            swapTheme();
        }
    }

    @Override
    public void render(org.mini2Dx.core.game.GameContainer container, Graphics g) {
        Image secLeft = THEME[0];
        Image secMid = THEME[1];
        Image secRight = THEME[2];

        secMid.draw((float) position.x, (float) position.y, width, SIZE);
        secLeft.draw((float) position.x, (float) position.y, SIZE, SIZE);
        secRight.draw((float) (position.x + width - SIZE), (float) position.y, SIZE, SIZE);

        String renderText = text;
        float padding = 10f;
        Vector2D textPos = new Vector2D(position.x + padding, position.y + SIZE - padding * 1.5f);
        Color color = bounds.contains(MaewilEngine.INSTANCE.getMousePos()) ? WidgetHyperlink.hoverColor : WidgetHyperlink.mainColor;

        Font font = Text.getCorrectFont(renderText, SIZE - padding * 3f);
        float fullTextWidth = width - padding * 2f;
        if (Text.getWidth(renderText, font) > fullTextWidth) {

            int startIndex = 0;
            String check = "";
            for (int i = text.length() - 1; i >= 0; i--) {
                check += text.charAt(i);
                if (Text.getWidth(check, font) >= fullTextWidth) {
                    startIndex = i + 1;
                    break;
                }
            }
            renderText = text.substring(startIndex);
            font = Text.getCorrectFont(renderText, SIZE - padding * 3f);
        }

        Text.drawString(g, renderText, (float) textPos.x, (float) textPos.y, false, color, font);
    }

    @Override
    public void onClick() {
        selected = true;
    }

    @Override
    public void onClickOutBounds() {
        selected = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
