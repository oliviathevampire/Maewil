package coffeecatteam.theultimatetile.gfx.image;

import coffeecatteam.theultimatetile.utils.Identifier;
import org.newdawn.slick.Image;

public class SpriteSheet {

    private Identifier identifier;
    private Image sheet;

    public SpriteSheet(Identifier identifier) {
        this.identifier = identifier;
        this.sheet = ImageLoader.loadImage(identifier);
    }

    public SpriteSheet(Image sheet) {
        this.identifier = new Identifier("tut", sheet.getResourceReference());
        this.sheet = sheet;
    }

    public SpriteSheet(Identifier identifier, Image sheet) {
        this.identifier = identifier;
        this.sheet = sheet;
    }

    public Image crop(int x, int y, int width, int height) {
        return sheet.getSubImage(x, y, width, height);
    }

    public Image getSheet() {
        return sheet;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public int getWidth() {
        return sheet.getWidth();
    }

    public int getHeight() {
        return sheet.getHeight();
    }

    public SpriteSheet copy() {
        return new SpriteSheet(identifier, sheet.copy());
    }
}
