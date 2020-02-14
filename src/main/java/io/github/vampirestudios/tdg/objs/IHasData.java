package io.github.vampirestudios.tdg.objs;

import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.utils.Identifier;
public interface IHasData<E> {

    default String getName() {
        JsonObject lang = DataParser.getDataCatchException(new Identifier("maewil", "lang"), true);

        if (lang.has(getUnlocalizedName()))
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
