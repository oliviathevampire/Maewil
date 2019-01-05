package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 10/12/2018
 */
public class UITextBox extends UIObject {

    private List<TextF> lines = new ArrayList<>();

    public UITextBox() {
        this(new Vector2D());
    }

    public UITextBox(Vector2D position) {
        super(position, 0, 0);
    }

    public void addText(String text) {
        addText(text, Assets.FONTS.get("20"));
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

    private int getLongestString() {
        int len = 0;
        if (lines.size() > 0) {
            for (TextF textF : lines)
                if (textF.getWidth() > len)
                    len = textF.getWidth();
        }
        return len;
    }

    private int getHeightOFStrings() {
        int height = 0;
        if (lines.size() > 0) {
            for (TextF textF : lines)
                height += textF.getHeight();
        }
        return height;
    }

    @Override
    public int getWidth() {
        return 64 + getLongestString();
    }

    @Override
    public int getHeight() {
        return 64 + getHeightOFStrings();
    }

    @Override
    public void render(Graphics g) {
        int segWidth = 32, segHeight = 32;
        Assets.TEXTBOX[0].draw((int) position.x, (int) position.y, segWidth, segHeight);
        Assets.TEXTBOX[1].draw((int) position.x + segWidth, (int) position.y, getLongestString(), segHeight);
        Assets.TEXTBOX[2].draw((int) position.x + segWidth + getLongestString(), (int) position.y, segWidth, segHeight);

        Assets.TEXTBOX[3].draw((int) position.x, (int) position.y + segHeight, segWidth, getHeightOFStrings());
        Assets.TEXTBOX[4].draw((int) position.x + segWidth, (int) position.y + segHeight, getLongestString(), getHeightOFStrings());
        Assets.TEXTBOX[5].draw((int) position.x + segWidth + getLongestString(), (int) position.y + segHeight, segWidth, getHeightOFStrings());

        Assets.TEXTBOX[6].draw((int) position.x, (int) position.y + segHeight + getHeightOFStrings(), segWidth, segHeight);
        Assets.TEXTBOX[7].draw((int) position.x + segWidth, (int) position.y + segHeight + getHeightOFStrings(), getLongestString(), segHeight);
        Assets.TEXTBOX[8].draw((int) position.x + segWidth + getLongestString(), (int) position.y + segHeight + getHeightOFStrings(), segWidth, segHeight);

        for (int i = 0; i < lines.size(); i++) {
            TextF textF = lines.get(i);

            int y = (int) position.y + segHeight;
            if (i > 0)
                y += lines.get(i - 1).getHeight() * i;
            Text.drawString(g, textF.getText(), (int) position.x + segWidth + getLongestString() / 2, y, true, false, textF.getColor(), textF.getFont());
        }

        /* FOR DEBUGGING */
        g.setLineWidth(5f);
        g.setColor(Color.red);
        g.drawLine((int) position.x, (int) position.y + segHeight, (int) position.x + segWidth * 2 + getLongestString(), (int) position.y + segHeight);
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

        public int getWidth() {
            return Text.getWidth(text, font);
        }

        public int getHeight() {
            return Text.getHeight(text, font);
        }
    }
}
