package coffeecatteam.theultimatetile.objs.tiles;

import java.awt.Color;

public class TileSettings {

    private Color mapColor;
    private boolean isSolid;
    private boolean isAir;
    private boolean unbreakable;
    private String id;
    private Tile.TileType tileType;

    public TileSettings(Color mapColor, boolean isSolid, boolean isAir, boolean unbreakable, String id, Tile.TileType tileType) {
        this.mapColor = mapColor;
        this.isSolid = isSolid;
        this.isAir = isAir;
        this.unbreakable = unbreakable;
        this.id = id;
        this.tileType = tileType;
    }

    public Color getMapColor() {
        return mapColor;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public boolean isAir() {
        return isAir;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public String getId() {
        return id;
    }

    public Tile.TileType getTileType() {
        return tileType;
    }

    public static class Builder {

        private Color mapColor;
        private boolean isSolid;
        private boolean isAir;
        private boolean unbreakable;
        private String id;
        private Tile.TileType tileType;

        public static Builder create() {
            return new Builder();
        }

        public Builder mapColor(Color mapColor) {
            this.mapColor = mapColor;
            return this;
        }

        public Builder solid(boolean solid) {
            isSolid = solid;
            return this;
        }

        public Builder air(boolean air) {
            isAir = air;
            return this;
        }

        public Builder unbreakable(boolean unbreakable) {
            this.unbreakable = unbreakable;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder tileType(Tile.TileType tileType) {
            this.tileType = tileType;
            return this;
        }

        public TileSettings build() {
            return new TileSettings(mapColor, isSolid, isAir, unbreakable, id, tileType);
        }

    }

}