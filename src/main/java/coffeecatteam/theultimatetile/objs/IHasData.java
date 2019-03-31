package coffeecatteam.theultimatetile.objs;

import coffeecatteam.theultimatetile.TutEngine;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * @author CoffeeCatRailway
 * Created: 22/02/2019
 */
public interface IHasData<E> {

    default String getName() {
        try {
            JSONObject lang = DataParser.getData("lang", true);
            switch (getType()) {
                case ITEM:
                    lang = (JSONObject) lang.get("items");
                    break;
                case TILE:
                    lang = (JSONObject) lang.get("tiles");
                    break;
                case ENTITY:
                    lang = (JSONObject) lang.get("entities");
                    break;
            }

            if (lang.containsKey(getUnlocalizedName()))
                return String.valueOf(lang.get(getUnlocalizedName()));
        } catch (IOException | ParseException e) {
            TutEngine.getTutEngine().getLogger().error(e.getMessage());
        }
        return getUnlocalizedName();
    }

    LangType getType();

    String getId();

    default String getUnlocalizedName() {
        return getId();
    }

    E newCopy();

    <T extends E> T newCopy(T obj);

    enum LangType {
        ITEM, TILE, ENTITY
    }
}
