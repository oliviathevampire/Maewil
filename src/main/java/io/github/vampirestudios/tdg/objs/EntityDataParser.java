package io.github.vampirestudios.tdg.objs;

import coffeecatteam.coffeecatutils.NumberUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.vampirestudios.tdg.gfx.Animation;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.image.SpriteSheet;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.tiles.Tiles;
import io.github.vampirestudios.tdg.utils.Identifier;
import org.newdawn.slick.Image;

import java.util.HashMap;
import java.util.Map;

public class EntityDataParser extends DataParser<Entity> {

    private Map<String, HashMap<String, Animation>> LOADED = new HashMap<>();

    public EntityDataParser() {
        super("entities");
    }

    @Override
    public Entity customLoadData(JsonObject data, Entity obj) {
        Entity entity = obj.newCopy();

        if (!LOADED.containsKey(entity.getId())) {
            DataTypes.Entity type = DataTypes.Entity.getByName(String.valueOf(data.get("type")));
            SpriteSheet texture = getTexture(data);
            logger.info("Loading entity of type [" + type + "-" + type.typeName + "] with id [" + entity.getName() + "]");

            HashMap<String, Animation> textures = new HashMap<>();
            switch (type) {
                case STATIC:
                    if (data.has("sprites")) {
                        JsonObject sprites = (JsonObject) data.get("sprites");
                        for (String key : sprites.keySet()) {
                            JsonObject sprite = (JsonObject) sprites.get(key);
                            SpriteSheet spriteTexture;
                            if (sprite.has("texture")) spriteTexture = getTexture(sprite).copy();
                            else spriteTexture = texture.copy();

                            System.out.println(key);
                            if (sprite.has("length")) textures.put(key, getAnimation(spriteTexture.getSheet(), sprite));
                            else textures.put(key, getAnimation(spriteTexture.getSheet(), sprite, false, "spritePos"));
                        }
                    }
                    break;
                case CREATURE:
                    JsonObject animations = (JsonObject) data.get("animations");
                    JsonArray size = (JsonArray) data.get("spriteSize");
                    int width = NumberUtils.parseInt(size.get(0));
                    int height = NumberUtils.parseInt(size.get(1));
                    for (Object key : animations.keySet()) {
                        JsonObject anim = (JsonObject) animations.get((String) key);
                        textures.put(String.valueOf(key), getAnimation(texture.getSheet().copy(), anim, width, height));
                    }
                    break;
                case CUSTOM:
                    textures.put(entity.getId(), new Animation(Tiles.AIR.getTexture()));
                    break;
            }

            entity.setTextures(textures);
            LOADED.put(entity.getId(), textures);
        } else {
            logger.info("Getting textures for entity with an id of [" + entity.getName().replace("\"", "") + "]");
            entity.setTextures(LOADED.get(entity.getId()));
        }

        return entity.newCopy();
    }

    private SpriteSheet getTexture(JsonObject data) {
        SpriteSheet texture = new SpriteSheet(Assets.MISSING_TEXTURE);
        if (data.has("texture")) {
            String texturePath = "textures/";
            System.out.println(data.get("texture").getAsString());

            if (data.get("texture") instanceof JsonArray) {
                JsonArray texturePaths = (JsonArray) data.get("texture");
                texturePath += String.valueOf(texturePaths.get(NumberUtils.getRandomInt(0, texturePaths.size() - 1))).replace("\"", "");
            } else {
                texturePath += data.get("texture").getAsString();
            }

            texture = new SpriteSheet(new Identifier("maewil", texturePath));
        }
        return texture;
    }

    private Animation getAnimation(Image texture, JsonObject data) {
        JsonArray size = (JsonArray) data.get("spriteSize");
        int width = NumberUtils.parseInt(size.get(0));
        int height = NumberUtils.parseInt(size.get(1));
        return getAnimation(texture, data, width, height);
    }

    private Animation getAnimation(Image texture, JsonObject data, int width, int height) {
        return getAnimation(texture, data, width, height, true, "startPos");
    }

    private Animation getAnimation(Image texture, JsonObject data, boolean xyExact, String posKey) {
        JsonArray size = (JsonArray) data.get("spriteSize");
        int width = NumberUtils.parseInt(size.get(0));
        int height = NumberUtils.parseInt(size.get(1));
        return getAnimation(texture, data, width, height, xyExact, posKey);
    }

    private Animation getAnimation(Image texture, JsonObject data, int width, int height, boolean xyExact, String posKey) {
        int length = 1;
        if (data.has("length"))
            length = NumberUtils.parseInt(data.get("length"));

        int speed = Animation.DEFAULT_SPEED;
        if (data.has("speed"))
            speed = NumberUtils.parseInt(data.get("speed"));

        JsonArray pos = (JsonArray) data.get(posKey);
        int startPosX = NumberUtils.parseInt(pos.get(0));
        int startPosY = NumberUtils.parseInt(pos.get(1));

        Image[] images = new Image[length];
        for (int i = 0; i < length; i++) {
            int x = startPosX;
            int y = startPosY;
            if (xyExact) {
                x = startPosX * width + width * i;
                y = startPosY * height;
            }

            Image img = texture.getSubImage(x, y, width, height);
            images[i] = img.copy();
        }

        return new Animation(speed, images);
    }
}
