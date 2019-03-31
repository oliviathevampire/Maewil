package coffeecatteam.theultimatetile.gfx.image;

import coffeecatteam.theultimatetile.TutLauncher;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.InputStream;

public class ImageLoader {

    public static Image loadImage(String path) {
        try {
            InputStream file = ImageLoader.class.getResourceAsStream(path);
            if (file == null) {
                file = ImageLoader.class.getResourceAsStream("/assets/textures/missing.png");
                TutLauncher.LOGGER.error(new Exception("Can't find texture: " + path));
            }

            Image image = new Image(file, path, false);
            image.setFilter(Image.FILTER_NEAREST);
            return image;
        } catch (SlickException e) {
            TutLauncher.LOGGER.error(e);
        }
        return null;
    }
}
