package coffeecatteam.theultimatetile.objs;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.image.ImageLoader;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Image;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CoffeeCatRailway
 * Created: 24/02/2019
 */
public class EntityDataParser extends DataParser<Entity> {

    public EntityDataParser() {
        super("entities");
    }

    public Entity loadData(Entity obj) throws IOException, ParseException {
        JSONObject data = DataParser.getData(dataFolderName + "/" + obj.getId(), true);

        EntityType type = EntityType.getByName(String.valueOf(data.get("type")));
        Image texture = Assets.MISSING_TEXTURE;
        if (data.containsKey("texture")) {
            String texturePath = "/assets/textures/";
            if (data.get("texture") instanceof JSONArray) {
                JSONArray texturePaths = (JSONArray) data.get("texture");
                texturePath += String.valueOf(texturePaths.get(NumberUtils.getRandomInt(0, texturePaths.size() - 1)));
            } else {
                texturePath += data.get("texture");
            }
            texture = ImageLoader.loadImage(texturePath);
        }

        Map<String, Animation> textures = new HashMap<>();
        switch (type) {
            default:
            case STATIC:
                JSONObject sprites = (JSONObject) data.get("sprites");

                for (Object key : sprites.keySet()) {
                    JSONObject sprite = (JSONObject) sprites.get(key);

                    if (sprite.containsKey("length")) {
                        textures.put(String.valueOf(key), getAnimation(texture, sprite));
                    } else {
                        int tlX = NumberUtils.parseInt(((JSONArray) sprite.get("topLeft")).get(0));
                        int tlY = NumberUtils.parseInt(((JSONArray) sprite.get("topLeft")).get(1));

                        int brX = NumberUtils.parseInt(((JSONArray) sprite.get("bottomRight")).get(0));
                        int brY = NumberUtils.parseInt(((JSONArray) sprite.get("bottomRight")).get(1));

                        Animation st = new Animation(texture.getSubImage(tlX, tlY, brX - tlX + 1, brY - tlY + 1));
                        textures.put(String.valueOf(key), st);
                    }
                }
                break;

            case LIVING:
                JSONObject animations = (JSONObject) data.get("animations");
                int sizeX = NumberUtils.parseInt(((JSONArray) data.get("spriteSize")).get(0));
                int sizeY = NumberUtils.parseInt(((JSONArray) data.get("spriteSize")).get(1));

                for (Object key : animations.keySet()) {
                    JSONObject anim = (JSONObject) animations.get(key);
                    textures.put(String.valueOf(key), getAnimation(texture, anim, sizeX, sizeY));
                }
                break;

            case CUSTOM:
                textures.put(obj.getId(), new Animation(Tiles.AIR.getTexture()));
                break;
        }
        obj.setTextures(textures);

        return obj.newCopy();
    }

    private Animation getAnimation(Image texture, JSONObject data) {
        int sizeX = NumberUtils.parseInt(((JSONArray) data.get("spriteSize")).get(0));
        int sizeY = NumberUtils.parseInt(((JSONArray) data.get("spriteSize")).get(1));
        return getAnimation(texture, data, sizeX, sizeY);
    }

    private Animation getAnimation(Image texture, JSONObject data, int sizeX, int sizeY) {
        int length = NumberUtils.parseInt(data.get("length"));
        int speed;
        if (data.containsKey("speed"))
            speed = NumberUtils.parseInt(data.get("speed"));
        else
            speed = Animation.DEFAULT_SPEED;

        int startPosX = NumberUtils.parseInt(((JSONArray) data.get("startPos")).get(0));
        int startPosY = NumberUtils.parseInt(((JSONArray) data.get("startPos")).get(1));

        Image[] images = new Image[length];
        for (int i = 0; i < length; i++) {
            int x = startPosX * sizeX + sizeX * i;
            int y = startPosY * sizeY;
            Image img = texture.getSubImage(x, y, sizeX, sizeY);
            images[i] = img.copy();
        }
        Animation animation = new Animation(speed, images);
        return animation;
    }

    enum EntityType {
        STATIC("static"), LIVING("creature"), CUSTOM("custom");

        String typeName;

        EntityType(String typeName) {
            this.typeName = typeName;
        }

        public static EntityType getByName(String typeName) {
            for (EntityType type : values())
                if (type.typeName.equals(typeName))
                    return type;
            return STATIC;
        }
    }

    @Override
    Entity singleData(JSONObject data, Entity obj, String texturePath, int spriteSize) {
        return null;
    }

    @Override
    Entity multipleData(JSONObject data, Entity obj, String texturePath, int spriteSize) {
        return null;
    }

    @Override
    Entity animatedData(JSONObject data, Entity obj, String texturePath, int spriteSize) {
        return null;
    }
}
