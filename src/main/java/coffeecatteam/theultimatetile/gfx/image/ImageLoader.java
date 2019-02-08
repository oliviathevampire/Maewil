package coffeecatteam.theultimatetile.gfx.image;

import coffeecatteam.theultimatetile.TutEngine;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.InputStream;

public class ImageLoader {

    public static Image loadImage(String path) {
        try {
            InputStream file = ImageLoader.class.getResourceAsStream(path);
            if (file == null)
                file = ImageLoader.class.getResourceAsStream("/assets/textures/missing.png");

            Image image = new Image(file, path, false);
            image.setFilter(Image.FILTER_NEAREST);
            return image;
        } catch (SlickException e) {
            TutEngine.getTutEngine().getLogger().print(e);
        }
        return null;
    }
}
