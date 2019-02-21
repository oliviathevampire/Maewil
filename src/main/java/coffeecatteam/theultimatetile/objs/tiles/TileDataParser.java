package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.io.FileUtils;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.image.SpriteSheet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Image;

import java.io.IOException;

/**
 * @author CoffeeCatRailway
 * Created: 11/02/2019
 */
public class TileDataParser {

    public static <T extends Tile> T loadTileData(T tile) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject data = (JSONObject) parser.parse(FileUtils.loadFileInSideJar("/assets/data/tiles/" + tile.getId() + ".json"));

        String type = String.valueOf(data.get("type"));
        String texturePath = "/assets/textures/" + data.get("texture");
        int spriteSize = NumberUtils.parseInt(data.get("size"));

        if (type.equals("normal")) {
            Image texture = Assets.getSpriteExact(texturePath, 0, 0, spriteSize, spriteSize);
            tile.setTexture(texture);
        }

        if (type.equals("alts")) {
            JSONArray textures = (JSONArray) data.get("textures");
            Image[] alts = new Image[textures.size()];
            SpriteSheet altSheet = new SpriteSheet(texturePath);

            for (int i = 0; i < alts.length; i++) {
                int textureIndex = NumberUtils.parseInt(textures.get(i));
                alts[i] = altSheet.crop(textureIndex * spriteSize, 0, spriteSize, spriteSize);
            }

            tile.setHasAlts(true);
            tile.setTextureAlts(alts);
            if (data.containsKey("altChance"))
                tile.setAltChance(NumberUtils.parseInt(data.get("altChance")));
            else
                tile.setAltChance(Tile.DEFAULT_ALT_CHANCE);
        }

        if (type.equals("animated")) {
            int animSpeed = NumberUtils.parseInt(data.get("speed"));
            JSONArray frameIndexes = (JSONArray) data.get("frames");
            SpriteSheet animSheet = new SpriteSheet(texturePath);
            Image[] frames = new Image[frameIndexes.size()];

            for (int i = 0; i < frames.length; i++) {
                Image frame = animSheet.crop(i * spriteSize, 0, spriteSize, spriteSize);
                frames[i] = frame.copy();
            }

            Animation animation = new Animation(animSpeed, frames);
            tile.setAnimation(animation);
        }

        return tile.newTile();
    }
}
