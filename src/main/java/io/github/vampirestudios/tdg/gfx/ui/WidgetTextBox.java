package io.github.vampirestudios.tdg.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

import java.util.ArrayList;
import java.util.List;

public class WidgetTextBox extends WidgetObject {

    private List<TextF> lines = new ArrayList<>();

    public WidgetTextBox() {
        this(new Vector2D());
    }

    public WidgetTextBox(Vector2D position) {
        super(position, 0, 0);
    }

    public void addText(String text) {
        addText(text, Assets.FONTS.get("20"));
    }

    public void addText(String text, Color color) {
        addText(text, Assets.FONTS.get("20"), color);
    }

    public void addText(String text, Font font) {
        addText(text, font, Color.white);
    }

    public void addText(String text, Font font, Color color) {
        TextF textF = new TextF(text, font, color);
        lines.add(textF);
    }

    public void clearText() {
        lines.clear();
    }

    private float getLongestString() {
        float len = 0;
        if (lines.size() > 0) {
            for (TextF textF : lines)
                if (textF.getWidth() > len)
                    len = textF.getWidth();
        }
        return len;
    }

    private int getHeightOfStrings() {
        int height = 0;
        if (lines.size() > 0) {
            for (TextF textF : lines)
                height += textF.getHeight();
        }
        return height;
    }

    @Override
    public float getWidth() {
        return 64 + getLongestString();
    }

    @Override
    public float getHeight() {
        return 64 + getHeightOfStrings();
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        int segWidth = 32, segHeight = 32;
        Assets.GUI_TEXTBOX[0].draw((int) position.x, (int) position.y, segWidth, segHeight);
        Assets.GUI_TEXTBOX[1].draw((int) position.x + segWidth, (int) position.y, getLongestString(), segHeight);
        Assets.GUI_TEXTBOX[2].draw((int) position.x + segWidth + getLongestString(), (int) position.y, segWidth, segHeight);

        Assets.GUI_TEXTBOX[3].draw((int) position.x, (int) position.y + segHeight, segWidth, getHeightOfStrings());
        Assets.GUI_TEXTBOX[4].draw((int) position.x + segWidth, (int) position.y + segHeight, getLongestString(), getHeightOfStrings());
        Assets.GUI_TEXTBOX[5].draw((int) position.x + segWidth + getLongestString(), (int) position.y + segHeight, segWidth, getHeightOfStrings());

        Assets.GUI_TEXTBOX[6].draw((int) position.x, (int) position.y + segHeight + getHeightOfStrings(), segWidth, segHeight);
        Assets.GUI_TEXTBOX[7].draw((int) position.x + segWidth, (int) position.y + segHeight + getHeightOfStrings(), getLongestString(), segHeight);
        Assets.GUI_TEXTBOX[8].draw((int) position.x + segWidth + getLongestString(), (int) position.y + segHeight + getHeightOfStrings(), segWidth, segHeight);

        int y = (int) position.y + segHeight;
        for (int i = 0; i < lines.size(); i++) {
            TextF textF = lines.get(i);

            if (i > 0)
                y += lines.get(i - 1).getHeight();
            Text.drawString(g, textF.getText(), (int) position.x + segWidth + getLongestString() / 2, y + textF.getHeight(), true, false, textF.getColor(), textF.getFont());
        }

        /* FOR DEBUGGING */
//        g.setLineWidth(5f);
//        g.setColor(Color.red);
//        g.drawLine((int) position.x, (int) position.y + segHeight, (int) position.x + segWidth * 2 + getLongestString(), (int) position.y + segHeight);
    }

    @Override
    public void onClick() {
    }

    class TextF {

        private String text;
        private Font font;
        private Color color;

        public TextF(String text, Font font, Color color) {
            this.text = text;
            this.font = font;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public Font getFont() {
            return font;
        }

        public Color getColor() {
            return color;
        }

        public float getWidth() {
            return Text.getWidth(text, font);
        }

        public float getHeight() {
            return Text.getHeight(text, font);
        }
    }
}
