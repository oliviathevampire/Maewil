package io.github.vampirestudios.tdg.objs;

import io.github.vampirestudios.tdg.utils.Identifier;
import org.json.simple.JSONObject;

public interface IHasData<E> {

    default String getName() {
        JSONObject lang = DataParser.getDataCatchException(new Identifier("maewil", "lang"), true);

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
