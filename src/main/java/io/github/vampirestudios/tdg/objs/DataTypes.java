package io.github.vampirestudios.tdg.objs;

/**
 * @author CoffeeCatRailway
 * Created: 11/03/2019
 */
public class DataTypes {

    enum TileItemTexture {
        SINGLE("normal"), MULTIPLE("alts"), ANIMATED("animated");

        String typeName;

        TileItemTexture(String typeName) {
            this.typeName = typeName;
        }

        public static TileItemTexture getByName(String typeName) {
            for (TileItemTexture type : values())
                if (type.typeName.equals(typeName))
                    return type;
            return SINGLE;
        }
    }

    enum Entity {
        STATIC("static"), LIVING("creature"), CUSTOM("custom");

        String typeName;

        Entity(String typeName) {
            this.typeName = typeName;
        }

        public static Entity getByName(String typeName) {
            for (Entity type : values())
                if (type.typeName.equals(typeName))
                    return type;
            return STATIC;
        }
    }
}
