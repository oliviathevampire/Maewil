package coffeecatteam.theultimatetile.objs;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * @author CoffeeCatRailway
 * Created: 22/02/2019
 */
public abstract class DataParser<E extends IHasData<E>> {

    private String dataFolderName;

    public DataParser(String dataFolderName) {
        this.dataFolderName = dataFolderName;
    }

    public E loadData(E obj) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject data = (JSONObject) parser.parse(FileUtils.loadFileInSideJar("/assets/data/" + dataFolderName + "/" + obj.getId() + ".json"));

        TextureType type = TextureType.getByName(String.valueOf(data.get("type")));
        String texturePath = "/assets/textures/" + data.get("texture");
        int spriteSize = NumberUtils.parseInt(data.get("size"));

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

    abstract E singleData(JSONObject data, E obj, String texturePath, int spriteSize);
    abstract E multipleData(JSONObject data, E obj, String texturePath, int spriteSize);
    abstract E animatedData(JSONObject data, E obj, String texturePath, int spriteSize);

    enum TextureType {
        SINGLE("normal"), MULTIPLE("alts"), ANIMATED("animated");

        String typeName;

        TextureType(String typeName) {
            this.typeName = typeName;
        }

        public static TextureType getByName(String typeName) {
            for (TextureType type : values())
                if (type.typeName.equals(typeName))
                    return type;
            return SINGLE;
        }
    }
}
