package io.github.vampirestudios.tdg.world.colormap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Biomes {

    private static List<Biome> biomes = new ArrayList<>();

    public static final Biome FOREST = new Biome(Biome.Properties.create("forest").color(new Color(0, 116, 0)));
    public static final Biome PLAINS = new Biome(Biome.Properties.create("plains").color(new Color(0, 185, 0)));
    public static final Biome OCEAN = new Biome(Biome.Properties.create("ocean").color(new Color(3, 0, 166)));
    public static final Biome SAVANNA = new Biome(Biome.Properties.create("savanna").color(new Color(166, 86, 0)));
    public static final Biome DESERT = new Biome(Biome.Properties.create("desert").color(new Color(166, 124, 0)));
    public static final Biome RED_DESERT = new Biome(Biome.Properties.create("red_desert").color(new Color(166, 124, 0)));
    public static final Biome CAVE = new Biome(Biome.Properties.create("cave").color(new Color(50, 50, 50)));
    public static final Biome UNDERGROUND = new Biome(Biome.Properties.create("underground").color(new Color(75, 75, 75)));
    public static final Biome DARK_PLAINS = new Biome(Biome.Properties.create("dark_plains").color(new Color(182, 30, 24)));
    public static final Biome DARK_FOREST = new Biome(Biome.Properties.create("dark_forest").color(new Color(182, 30, 32)));

    public static final Biome NONE = new Biome(Biome.Properties.create("none").color(new Color(255, 255, 255, 0)));

    public static class Biome {

        private String name;
        private Color color;
        private int temperature;
        private int humidity;
        private String versions;

        Biome(Biome.Properties properties) {
            this.name = properties.getName();
            this.color = properties.getColor();
            this.temperature = properties.getTemperature();
            this.humidity = properties.getHumidity();
            this.versions = properties.getVersions();
            biomes.add(this);
        }

        public int getTemperature() {
            return temperature;
        }

        public int getHumidity() {
            return humidity;
        }

        public String getVersions() {
            return versions;
        }

        public String getName() {
            return name;
        }

        public Color getColor() {
            return color;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Biome) {
                return ((Biome) obj).getName().equals(this.name);
            } else
                return false;
        }

        public static class Properties {

            private String name;
            private Color color;
            private int temperature;
            private int humidity;
            private String versions;

            public static Properties create(String nameIn) {
                return new Properties().name(nameIn);
            }

            private Properties name(String name) {
                this.name = name;
                return this;
            }

            public Properties color(Color color) {
                this.color = color;
                return this;
            }

            public Properties temperature(int temperature) {
                this.temperature = temperature;
                return this;
            }

            public Properties humidity(int humidity) {
                this.humidity = humidity;
                return this;
            }

            public Properties versions(String versions) {
                this.versions = versions;
                return this;
            }

            public String getName() {
                return name;
            }

            public Color getColor() {
                return color;
            }

            public int getTemperature() {
                return temperature;
            }

            public int getHumidity() {
                return humidity;
            }

            public String getVersions() {
                return versions;
            }
        }

    }

    public static Biome getBiomeAt(BufferedImage biomeMap, int x, int y) {
        for (Biome biome : Biomes.biomes) {
            int rgba = biomeMap.getRGB(x, y);
            if (biome.getColor().getRGB() == rgba)
                return biome;
            else
                return Biomes.PLAINS;
        }
        return Biomes.NONE;
    }
}
