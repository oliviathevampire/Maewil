package io.github.vampirestudios.tdg.objs;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.io.FileUtils;
import coffeecatteam.coffeecatutils.logger.CatLogger;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.Identifier;
import io.github.vampirestudios.tdg.utils.JsonUtils;

import java.io.IOException;

public abstract class DataParser<E extends IHasData<E>> {

    protected String dataFolderName;
    protected CatLogger logger;

    public DataParser(String dataFolderName) {
        this.dataFolderName = dataFolderName;
        String loggerName = this.dataFolderName.substring(0, 1).toUpperCase() + this.dataFolderName.substring(1);
        this.logger = new CatLogger("TUT-" + loggerName + "-Parser");
    }

    public E loadData(E obj) throws IOException {
        JsonObject data = getData(new Identifier("maewil", dataFolderName + "/" + obj.getId()), true);

        E custom = customLoadData(data, obj);
        if (custom != null) {
            return custom;
        } else {
            DataTypes.TileItemTexture type = DataTypes.TileItemTexture.getByName(String.valueOf(data.get("type")));
            Identifier texturePath = new Identifier("maewil", "textures/" + data.get("texture").getAsString().replace("\"'", ""));
            int spriteSize = NumberUtils.parseInt(data.get("size"));
            logger.info("Loading an object with the type [" + type + "-" + type.typeName + "] with an id of [" + obj.getName().replace("\"", "") + "]");

            switch (type) {
                default:
                case SINGLE:
                    obj = singleData(data, obj, texturePath, spriteSize);
                    break;
                case MULTIPLE:
                    obj = multipleData(data, obj, texturePath, spriteSize);
                    break;
                case ANIMATED:
                    obj = animatedData(data, obj, texturePath, spriteSize);
                    break;
            }

            return obj.newCopy();
        }
    }

    E customLoadData(JsonObject data, E obj) {
        return null;
    }

    E singleData(JsonObject data, E obj, Identifier texturePath, int spriteSize) {
        return null;
    }

    E multipleData(JsonObject data, E obj, Identifier texturePath, int spriteSize) {
        return null;
    }

    E animatedData(JsonObject data, E obj, Identifier texturePath, int spriteSize) {
        return null;
    }

    private static JsonObject getData(Identifier fileName, boolean inJar) throws IOException {
        Identifier path = new Identifier("maewil", fileName.getPath() + ".json");
        if (inJar) {
            return JsonUtils.GSON.fromJson(FileUtils.loadFileInSideJar(path.toDataString()), JsonObject.class);
        } else {
            return JsonUtils.GSON.fromJson(FileUtils.loadFileOutSideJar(path.toDataString()), JsonObject.class);
        }
    }

    static JsonObject getDataCatchException(Identifier fileName, boolean inJar) {
        try {
            return getData(fileName, inJar);
        } catch (IOException e) {
            MaewilLauncher.LOGGER.error(e.getMessage());
            return new JsonObject();
        }
    }
}
