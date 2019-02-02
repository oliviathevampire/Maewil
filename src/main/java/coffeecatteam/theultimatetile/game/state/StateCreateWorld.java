package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.game.StateGame;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.tiles.TileAir;
import coffeecatteam.theultimatetile.game.tile.tiles.TileDirt;
import coffeecatteam.theultimatetile.game.tile.tiles.TileGrass;
import coffeecatteam.theultimatetile.game.tile.tiles.TileSand;
import coffeecatteam.theultimatetile.game.world.World;
import coffeecatteam.theultimatetile.game.world.WorldGenerator;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author CoffeeCatRailway
 * Created: 19/01/2019
 */
public class StateCreateWorld extends StateAbstractMenu {

    private String worldName;
    private long seed;
    private float sizeMod;
    private int minWorldSize = 400, worldSize;

    private Tile[][] bgTiles, fgTiles;
    private boolean generating = false;

    public StateCreateWorld(Engine engine) {
        super(engine, DEFAULT_CENTRE);

        worldName = "Test World";
        long range = 1000000000L;
        seed = generateSeed(-range, range);
        sizeMod = NumberUtils.getRandomFloat(0.0f, 50.0f);

        int sizeMin = Math.min((int) -(sizeMod * 2), (int) (sizeMod * 2));
        int sizeMax = Math.max((int) -(sizeMod * 2), (int) (sizeMod * 2));
        worldSize = minWorldSize + NumberUtils.getRandomInt(sizeMin, sizeMax);

        uiManager.addObject(new UIButton(engine, true, "Create World", new ClickListener() {
            @Override
            public void onClick() {
                generateWorld();
            }

            @Override
            public void update(GameContainer container, int delta) {
            }
        }));
    }

    private long generateSeed(long min, long max) {
        long s1 = randomLong(min, max);
        long s2 = randomLong(min, max);

        while (s2 <= s1)
            s2 = randomLong(min, max);

        return randomLong(s1, s2);
    }

    private long randomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(max - min + 1) + min;
    }

    @Override
    public void render(Graphics g) {
        Color c = Color.white;
        Font f = Assets.FONTS.get("40");

        if (generating) {
            this.renderBG(g);
            Text.drawStringCentered(g, "Generating world...", c, f);
        } else {
            super.render(g);
            Text.drawStringCenteredX(g, worldName, engine.getHeight() / 2 - Text.getHeight(worldName, f) / 2 - 90, c, f);

            String seedS = "Seed: " + String.valueOf(seed);
            Text.drawStringCenteredX(g, seedS, engine.getHeight() / 2 - Text.getHeight(seedS, f) / 2 - 55, c, f);

            String worldSizeS = "Size: " + String.valueOf(worldSize);
            Text.drawStringCenteredX(g, worldSizeS, engine.getHeight() / 2 - Text.getHeight(worldSizeS, f) / 2 - 20, c, f);
        }
    }

    private void generateWorld() {
        logger.print("Generating world...");
//        Tiles.init(engine);

        double blendSize = 25.0d + sizeMod;
        WorldGenerator generator = new WorldGenerator(seed, worldSize);
        generator.setBlendSize(blendSize);
        generator.generate();

        generating = true;
        while (generator.isGenerating()) {
            if (generator.isGenerated()) {
                bgTiles = generator.getBgTiles();
                fgTiles = generator.getFgTiles();
            }
        }

        World world = new World(engine, worldName, worldSize, worldSize, getPlayerSpawn(), bgTiles, fgTiles).setSeed(seed);
        State.setState(new StateGame(engine, "./saves/Test_World", worldName, world));
        ((GameEngine) engine).setUsername("TEST");

        generating = false;
    }

    private Vector2D getPlayerSpawn() {
        int x = NumberUtils.getRandomInt(worldSize - 1);
        int y = NumberUtils.getRandomInt(worldSize - 1);

        if (fgTiles[x][y].isSolid() || fgTiles[x][y].isUnbreakable())
            return getPlayerSpawn();
        else {
            if (fgTiles[x][y] instanceof TileSand || fgTiles[x][y] instanceof TileGrass || fgTiles[x][y] instanceof TileDirt || fgTiles[x][y] instanceof TileAir)
                return new Vector2D(x, y);
            else
                return getPlayerSpawn();
        }
    }
}
