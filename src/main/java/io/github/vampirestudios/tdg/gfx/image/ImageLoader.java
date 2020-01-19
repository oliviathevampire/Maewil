package io.github.vampirestudios.tdg.gfx.image;

import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.Identifier;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.InputStream;

public class ImageLoader {

    public static Image loadImage(Identifier identifier) {
        try {
            InputStream file = ImageLoader.class.getResourceAsStream(identifier.toAssetsString());
            if (file == null) {
                file = ImageLoader.class.getResourceAsStream("/assets/maewil/textures/missing.png");
                MaewilLauncher.LOGGER.error(new Exception("Can't find texture: " + identifier.toAssetsString()));
            }

            Image image = new Image(file, "/assets/" + identifier.getNamespace() + "/" + identifier.getPath(), false);
            image.setFilter(Image.FILTER_NEAREST);
            return image;
        } catch (SlickException e) {
            System.out.println("Can't find " + identifier.toAssetsString());
            MaewilLauncher.LOGGER.error(e);
            return null;
        }
    }

    public static Image loadImage(String path) {
        try {
            InputStream file = ImageLoader.class.getResourceAsStream(path);
            if (file == null) {
                file = ImageLoader.class.getResourceAsStream("/assets/maewil/textures/missing.png");
                MaewilLauncher.LOGGER.error(new Exception("Can't find texture: " + path));
            }

            Image image = new Image(file, path, false);
            image.setFilter(Image.FILTER_NEAREST);
            return image;
        } catch (SlickException e) {
            MaewilLauncher.LOGGER.error(e);
            return null;
        }
    }

}
