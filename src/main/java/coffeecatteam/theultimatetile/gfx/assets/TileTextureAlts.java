package coffeecatteam.theultimatetile.gfx.assets;

import coffeecatteam.theultimatetile.gfx.image.SpriteSheet;
import org.newdawn.slick.Image;

/**
 * @author CoffeeCatRailway
 * Created: 26/12/2018
 */
public class TileTextureAlts {

    public static Image[] getTextureAlts(int tileAlts, int tileOverlaps, SpriteSheet sheet, int tileWidth, int tileHeight) {
        Image[] textures = new Image[tileAlts + tileOverlaps + 1];

        for (int i = 0; i < tileAlts; i++)
            textures[i] = Assets.getSpriteInd(sheet, i, 3, tileWidth, tileHeight);

        // Grass overlay
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int index = (x * 3) + y;
                textures[index + tileAlts] = Assets.getSpriteInd(sheet, x, y, tileWidth, tileHeight);
            }
        }
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                int index = (x * 2) + y;
                textures[index + tileAlts + 9] = Assets.getSpriteInd(sheet, x + 3, y, tileWidth, tileHeight);
            }
        }
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                int index = (x * 2) + y;
                textures[index + tileAlts + 13] = Assets.getSpriteInd(sheet, x + 9, y, tileWidth, tileHeight);
            }
        }
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                int index = (x * 2) + y;
                textures[index + tileAlts + 17] = Assets.getSpriteInd(sheet, x + 6, y + 1, tileWidth, tileHeight);
            }
        }
        textures[tileAlts + 21] = Assets.getSpriteInd(sheet, 8, 1, tileWidth, tileHeight);
        for (int i = 0; i < 3; i++) {
            textures[i + tileAlts + 22] = Assets.getSpriteInd(sheet, 5, i, tileWidth, tileHeight);
        }
        for (int i = 0; i < 3; i++) {
            textures[i + tileAlts + 25] = Assets.getSpriteInd(sheet, i + 6, 0, tileWidth, tileHeight);
        }
        textures[tileAlts + 28] = Assets.getSpriteInd(sheet, 8, 2, tileWidth, tileHeight);
        for (int i = 0; i < 2; i++) {
            textures[i + tileAlts + 29] = Assets.getSpriteInd(sheet, i + 3, 2, tileWidth, tileHeight);
        }
        for (int i = 0; i < 3; i++) {
            textures[i + tileAlts + 31] = Assets.getSpriteInd(sheet, 11, i, tileWidth, tileHeight);
        }
        for (int i = 0; i < 3; i++) {
            textures[i + tileAlts + 34] = Assets.getSpriteInd(sheet, 12, i, tileWidth, tileHeight);
        }
        for (int i = 0; i < 3; i++) {
            textures[i + tileAlts + 37] = Assets.getSpriteInd(sheet, 13, i, tileWidth, tileHeight);
        }
        for (int i = 0; i < 3; i++) {
            textures[i + tileAlts + 40] = Assets.getSpriteInd(sheet, 14, i, tileWidth, tileHeight);
        }
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                int index = (x * 2) + y;
                textures[index + tileAlts + 43] = Assets.getSpriteInd(sheet, x + 15, y, tileWidth, tileHeight);
            }
        }
        textures[tileAlts + 47] = Assets.getSpriteInd(sheet, 8, 2, tileWidth, tileHeight);

        return textures;
    }
}
