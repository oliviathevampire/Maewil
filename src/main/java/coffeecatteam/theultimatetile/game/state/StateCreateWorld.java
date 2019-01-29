package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.game.StateGame;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.Tiles;
import coffeecatteam.theultimatetile.game.tile.tiles.TileLava;
import coffeecatteam.theultimatetile.game.tile.tiles.TileWater;
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

    public StateCreateWorld(Engine engine) {
        super(engine);

        worldName = "Test World";
        long range = 1000000000L;
        seed = generateSeed(-range, range);
        sizeMod = NumberUtils.getRandomFloat(0.0f, 50.0f);
        worldSize = minWorldSize + NumberUtils.getRandomInt((int) -(sizeMod * 2), (int) (sizeMod * 2));

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
        super.render(g);
        Color c = Color.white;
        Font f = Assets.FONTS.get("40");

        Text.drawStringCenteredX(g, worldName, engine.getHeight() / 2 - Text.getHeight(worldName, f) / 2 - 90, c, f);

        String seedS = "Seed: " + String.valueOf(seed);
        Text.drawStringCenteredX(g, seedS, engine.getHeight() / 2 - Text.getHeight(seedS, f) / 2 - 55, c, f);

        String worldSizeS = "Size: " + String.valueOf(worldSize);
        Text.drawStringCenteredX(g, worldSizeS, engine.getHeight() / 2 - Text.getHeight(worldSizeS, f) / 2 - 20, c, f);
    }

    private void generateWorld() {
        double blendSize = 25.0d + sizeMod;
        WorldGenerator generator = new WorldGenerator(seed, worldSize).setBlendSize(blendSize);
        generator.generate();

        Tiles.init(engine);
        bgTiles = generator.getBGTiles();
        fgTiles = generator.getFGTiles();

        World world = new World(engine, worldName, worldSize, worldSize, getPlayerSpawn(), bgTiles, fgTiles);
        State.setState(new StateGame(engine, "./saves/Test_World", worldName, world));
        ((GameEngine) engine).setUsername("TEST");
    }

    private Vector2D getPlayerSpawn() {
        int x = NumberUtils.getRandomInt(1, worldSize - 3);
        int y = NumberUtils.getRandomInt(1, worldSize - 3);

        if (fgTiles[x][y].isSolid() || fgTiles[x][y] instanceof TileWater || fgTiles[x][y] instanceof TileLava)
            return getPlayerSpawn();
        else
            return new Vector2D(x, y);
    }
}
