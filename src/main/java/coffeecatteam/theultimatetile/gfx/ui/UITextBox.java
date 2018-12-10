package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;

import java.awt.*;
import java.awt.event.MouseEvent;
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
        addText(text, Assets.FONT_20);
    }

    public void addText(String text, Font font) {
        addText(text, font, false);
    }

    public void addText(String text, Font font, boolean underlined) {
        addText(text, font, underlined, Color.WHITE);
    }

    public void addText(String text, Font font, boolean underlined, Color color) {
        TextF textF = new TextF(text, font, underlined, color);
        lines.add(textF);
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
    public void tick() {
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
    public void render(Graphics2D g) {
        int segWidth = 32, segHeight = 32;
        g.drawImage(Assets.TEXTBOX[0], (int) position.x, (int) position.y, segWidth, segHeight, null);
        g.drawImage(Assets.TEXTBOX[1], (int) position.x + segWidth, (int) position.y, getLongestString(), segHeight, null);
        g.drawImage(Assets.TEXTBOX[2], (int) position.x + segWidth + getLongestString(), (int) position.y, segWidth, segHeight, null);

        g.drawImage(Assets.TEXTBOX[3], (int) position.x, (int) position.y + segHeight, segWidth, getHeightOFStrings(), null);
        g.drawImage(Assets.TEXTBOX[4], (int) position.x + segWidth, (int) position.y + segHeight, getLongestString(), getHeightOFStrings(), null);
        g.drawImage(Assets.TEXTBOX[5], (int) position.x + segWidth + getLongestString(), (int) position.y + segHeight, segWidth, getHeightOFStrings(), null);

        g.drawImage(Assets.TEXTBOX[6], (int) position.x, (int) position.y + segHeight + getHeightOFStrings(), segWidth, segHeight, null);
        g.drawImage(Assets.TEXTBOX[7], (int) position.x + segWidth, (int) position.y + segHeight + getHeightOFStrings(), getLongestString(), segHeight, null);
        g.drawImage(Assets.TEXTBOX[8], (int) position.x + segWidth + getLongestString(), (int) position.y + segHeight + getHeightOFStrings(), segWidth, segHeight, null);

        for (int i = 0; i < lines.size(); i++) {
            TextF textF = lines.get(i);

            String text = textF.getText();
            Font font = textF.getFont();
            int height = textF.getHeight();
            boolean underlined = textF.isUnderlined();
            Color color = textF.getColor();

            int y = (int) position.y + segHeight + lines.get(0).getHeight();
            if (i > 0) y += height * i;
            Text.drawString(g, text, (int) position.x + segWidth + getLongestString() / 2, y, true, false, underlined, color, font);
        }

        /* FOR DEBUGGING */
//        g.drawLine((int) position.x, (int) position.y + segHeight, (int) position.x + segWidth * 2 + getLongestString(), (int) position.y + segHeight);
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

    class TextF {

        private String text;
        private Font font;
        private boolean underlined;
        private Color color;

        public TextF(String text, Font font, boolean underlined, Color color) {
            this.text = text;
            this.font = font;
            this.underlined = underlined;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public Font getFont() {
            return font;
        }

        public boolean isUnderlined() {
            return underlined;
        }

        public Color getColor() {
            return color;
        }

        public int getWidth() {
            return Text.getWidth(Engine.getEngine().newGraphics(), text, font);
        }

        public int getHeight() {
            return Text.getHeight(Engine.getEngine().newGraphics(), font);
        }
    }
}
