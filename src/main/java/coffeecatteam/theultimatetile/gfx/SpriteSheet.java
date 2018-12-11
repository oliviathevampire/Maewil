package coffeecatteam.theultimatetile.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private String path;
    private BufferedImage sheet;

    public SpriteSheet(String path) {
        this.path = path;
        this.sheet = ImageLoader.loadImage(path);
    }

    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }

    public BufferedImage getSheet() {
        return sheet;
    }

    public String getPath() {
        return path;
    }
}
