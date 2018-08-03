package coffeecatteam.tilegame.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject {

    private BufferedImage[] textures;
    private ClickListener listener;

    public UIImageButton(float x, float y, int width, int height, BufferedImage[] textures, ClickListener listener) {
        super(x, y, width, height);
        this.textures = textures;
        this.listener = listener;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        if (hovering)
            g.drawImage(textures[1], (int) x, (int) y, width, height, null);
        else
            g.drawImage(textures[0], (int) x, (int) y, width, height, null);
    }

    @Override
    public void onClick() {
        listener.onClick();
    }
}
