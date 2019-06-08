package coffeecatteam.theultimatetile.objs;

import coffeecatteam.theultimatetile.utils.Identifier;
import org.json.simple.JSONObject;

/**
 * @author CoffeeCatRailway
 * Created: 22/02/2019
 */
public interface IHasData<E> {

    default String getName() {
        JSONObject lang = DataParser.getDataCatchException(new Identifier("tut", "lang"), true);

        if (lang.containsKey(getUnlocalizedName()))
            return String.valueOf(lang.get(getUnlocalizedName()));
        else
            return getUnlocalizedName();
    }

    LangType getType();

    String getId();

    default String getUnlocalizedName() {
        return getType().getSuffix() + getId();
    }

    E newCopy();

    <T extends E> T newCopy(T obj);

    enum LangType {
        ITEM("item"), TILE("tile"), ENTITY("entity");

        private String suffix;

        LangType(String suffix) {
            this.suffix = suffix + ".";
        }

        public String getSuffix() {
            return suffix;
        }
    }
}
