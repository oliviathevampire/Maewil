package coffeecatteam.theultimatetile.gfx.image;

import coffeecatteam.theultimatetile.Engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            URL file = ImageLoader.class.getResource(path);
            if (file == null)
                file = ImageLoader.class.getResource("/assets/textures/missing.png");
            return ImageIO.read(file);
        } catch (IOException e) {
            Engine.getEngine().getLogger().print(e);
        }
        return null;
    }
}
