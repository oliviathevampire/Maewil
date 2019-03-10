package coffeecatteam.theultimatetile.gfx.image;

import org.newdawn.slick.Image;

public class SpriteSheet {

    private String path;
    private Image sheet;

    public SpriteSheet(String path) {
        this.path = path;
        this.sheet = ImageLoader.loadImage(path);
    }

    public SpriteSheet(Image sheet) {
        this.path = sheet.getName();
        this.sheet = sheet;
    }

    public SpriteSheet(String path, Image sheet) {
        this.path = path;
        this.sheet = sheet;
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

    public SpriteSheet copy() {
        return new SpriteSheet(path, sheet.copy());
    }
}
