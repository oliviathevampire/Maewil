package coffeecatteam.theultimatetile.world.colormap;

import coffeecatteam.theultimatetile.TutLauncher;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 18/01/2019
 */
public class Biomes {

    private static List<Biome> biomes = new ArrayList<>();

    public static final Biome FOREST = new Biome("forest", false, 0, 116, 0);
    public static final Biome PLAINS = new Biome("plains", true, 0, 185, 0);
    public static final Biome CAVE = new Biome("cave", true, 50, 50, 50);
    public static final Biome UNDERGROUND = new Biome("underground", false, 75, 75, 75);

    public static final Biome NONE = new Biome("none", false, 255, 255, 255, 0);

    public static class Biome {

        private String id;
        private Color color;
        private boolean canSpawnEntities;

        Biome(String id, boolean canSpawnEntities, int... color) {
            int[] finalColor = new int[4];
            checkColor(id, color);
            if (color.length == 3) {
                finalColor[3] = 255;
            } else if (color.length < 3 || color.length > 4) {
                throwInvalidColor(id, color);
            }
            this.id = id;
            this.color = new Color(finalColor[0], finalColor[1], finalColor[2], finalColor[3]);
            this.canSpawnEntities = canSpawnEntities;
            biomes.add(this);
        }

        public String getId() {
            return id;
        }

        public Color getColor() {
            return color;
        }

        public boolean canSpawnEntities() {
            return canSpawnEntities;
        }

        private void throwInvalidColor(String id, int... color) {
            try {
                StringBuilder msg = new StringBuilder("Invalid biome color [");
                for (int c : color) {
                    msg.append(c).append(",");
                }
                msg.append("] with id [").append(id).append("]");
                throw new Exception(msg.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void checkColor(String id, int... color) {
            for (int i = 0; i < color.length; i++) {
                Exception incorrectValue = new Exception("Biome with id [" + id + "] has incorrect value: " + color[i]);

                if (color[i] < 0) {
                    TutLauncher.LOGGER.error(incorrectValue);
                    color[i] = 0;
                } else if (color[i] > 255) {
                    TutLauncher.LOGGER.error(incorrectValue);
                    color[i] = 255;
                } else
                    color[i] = color[i];
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Biome) {
                return ((Biome) obj).getId().equals(this.id);
            } else
                return false;
        }
    }

    public static Biome getBiomeAt(BufferedImage biomeMap, int x, int y) {
        for (Biome biome : Biomes.biomes) {
            int rgba = biomeMap.getRGB(x, y);
            if (biome.getColor().getRGB() == rgba)
                return biome;
        }
        return Biomes.NONE;
    }
}
