package coffeecatteam.theultimatetile.objs;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.io.FileUtils;
import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.start.TutLauncher;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * @author CoffeeCatRailway
 * Created: 22/02/2019
 */
public abstract class DataParser<E extends IHasData<E>> {

    protected String dataFolderName;
    protected CatLogger logger;

    public DataParser(String dataFolderName) {
        this.dataFolderName = dataFolderName;
        String loggerName = this.dataFolderName.substring(0, 1).toUpperCase() + this.dataFolderName.substring(1);
        this.logger = new CatLogger("TUT-" + loggerName + "-Parser");
    }

    public E loadData(E obj) throws IOException, ParseException {
        JSONObject data = getData(dataFolderName + "/" + obj.getId(), true);

        E custom = customLoadData(data, obj);
        if (custom != null) {
            return custom;
        } else {
            DataTypes.TileItemTexture type = DataTypes.TileItemTexture.getByName(String.valueOf(data.get("type")));
            String texturePath = "/assets/textures/" + data.get("texture");
            int spriteSize = NumberUtils.parseInt(data.get("size"));
            logger.info("Loading object of type [" + type + "-" + type.typeName + "] with id [" + obj.getName() + "]");

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

    E customLoadData(JSONObject data, E obj) {
        return null;
    }

    E singleData(JSONObject data, E obj, String texturePath, int spriteSize) {
        return null;
    }

    E multipleData(JSONObject data, E obj, String texturePath, int spriteSize) {
        return null;
    }

    E animatedData(JSONObject data, E obj, String texturePath, int spriteSize) {
        return null;
    }

    public static JSONObject getData(String fileName, boolean inJar) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String path = "/assets/data/" + fileName + ".json";
        if (inJar) {
            return (JSONObject) parser.parse(FileUtils.loadFileInSideJar(path));
        } else {
            return (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path));
        }
    }

    public static JSONObject getDataCatchException(String fileName, boolean inJar) {
        try {
            return getData(fileName, inJar);
        } catch (IOException | ParseException e) {
            TutLauncher.LOGGER.error(e.getMessage());
            return new JSONObject();
        }
    }
}
