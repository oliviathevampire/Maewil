package coffeecatteam.theultimatetile.gfx.image;

import org.newdawn.slick.Image;

public class SpriteSheet {

    private String path;
    private Image sheet;

    public SpriteSheet(String path) {
        this.path = path;
        this.sheet = ImageLoader.loadImage(path);
    }

    public Image crop(int x, int y, int width, int height) {
        return sheet.getSubImage(x, y, width, height);
    }

    public Image getSheet() {
        return sheet;
    }

    public String getPath() {
        return path;
    }
}
