package io.github.vampirestudios.tdg.gfx.image;

import io.github.vampirestudios.tdg.start.TutLauncher;
import io.github.vampirestudios.tdg.utils.Identifier;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.InputStream;

public class ImageLoader {

    public static Image loadImage(Identifier identifier) {
        try {
            InputStream file = ImageLoader.class.getResourceAsStream(identifier.toAssetsString());
            if (file == null) {
                file = ImageLoader.class.getResourceAsStream(new Identifier("tut", "textures/missing.png").toAssetsString());
                TutLauncher.LOGGER.error(new Exception("Can't find texture: " + identifier.toAssetsString()));
            }

            Image image = new Image(file, identifier.toAssetsString(), false);
            image.setFilter(Image.FILTER_NEAREST);
            return image;
        } catch (SlickException e) {
            TutLauncher.LOGGER.error(e);
        }
        return null;
    }
}
