package coffeecatteam.theultimatetile.screen;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.WidgetTextBox;
import coffeecatteam.theultimatetile.gfx.ui.button.WidgetButton;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.PlayerEntity;
import coffeecatteam.theultimatetile.objs.tiles.*;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.screen.game.GameScreen;
import coffeecatteam.theultimatetile.world.TileList;
import coffeecatteam.theultimatetile.world.World;
import coffeecatteam.theultimatetile.world.WorldGenerator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author CoffeeCatRailway
 * Created: 19/01/2019
 */
public class CreateWorldScreen extends AbstractMenuScreen {

    private String worldName;
    private long seed;
    private float sizeMod;
    private int worldSize;

    private TileList bgTiles, fgTiles;
    private boolean generating = false;

    public CreateWorldScreen(TutEngine tutEngine) {
        super(tutEngine, CENTRE_GRASS);

        worldName = "ADD TEXT BOX";
        long range = 1000000000L;
        seed = generateSeed(-range, range);
        sizeMod = NumberUtils.getRandomFloat(0.0f, 50.0f);

        int sizeMin = Math.min((int) -(sizeMod * 2), (int) (sizeMod * 2));
        int sizeMax = Math.max((int) -(sizeMod * 2), (int) (sizeMod * 2));
        int sizeModExtra;
        if (sizeMin < sizeMax)
            sizeModExtra = NumberUtils.getRandomInt(sizeMin, sizeMax);
        else
            sizeModExtra = NumberUtils.getRandomInt(sizeMax, sizeMin);
        int minWorldSize = 400;
        worldSize = minWorldSize + sizeModExtra;

        uiManager.addObject(new WidgetTextBox());
        uiManager.addObject(new WidgetButton(tutEngine, true, "Create World", new ClickListener() {
            @Override
            public void onClick() {
                generateWorld();
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));
    }

    private void generateWorld() {
        /*
         * Tiles, entities, etc..
         */
        logger.info("Generating world...");
        tutEngine.getPlayer().reset();

        double blendSize = 25.0d + sizeMod;
        WorldGenerator generator = new WorldGenerator(tutEngine, seed, worldSize);
        generator.setBlendSize(blendSize);
        generator.generate();

        generating = true;
        while (generator.isGenerating()) {
            if (generator.isGenerated()) {
                bgTiles = generator.getBgTiles();
                fgTiles = generator.getFgTiles();
            }
        }

        /*
         * Set world & username
         */
        World world = new World(tutEngine, worldName, worldSize, worldSize, getPlayerSpawn(), bgTiles, fgTiles);
        ScreenManager.setCurrentScreen(new GameScreen(tutEngine, "./data/saves/Test_World", worldName, world));

        if (ArgUtils.hasArgument("-username"))
            tutEngine.setUsername(ArgUtils.getArgument("-username"));
        else
            tutEngine.setUsername("TEST"); // TODO: Add text box to enter username on startup

        generating = false;
    }

    private Vector2D getPlayerSpawn() {
        int x = NumberUtils.getRandomInt(worldSize - 1);
        int y = NumberUtils.getRandomInt(worldSize - 1);
        Tile tile = fgTiles.getTileAtPos(x, y);

        for (Entity entity : tutEngine.getEntityManager().getEntities()) {
            if (!(entity instanceof PlayerEntity)) {
                int nx = x * Tile.TILE_SIZE;
                int ny = y * Tile.TILE_SIZE;
                if (tutEngine.getEntityManager().isEntityInView(new Vector2D(nx, ny), entity.getPosition(), 2))
                    return getPlayerSpawn();
            }
        }

        if (tile.isSolid() || tile.isUnbreakable())
            return getPlayerSpawn();
        else {
            if (tile instanceof SandTile || tile instanceof GrassTile || tile instanceof DirtTile || tile instanceof AirTile)
                return new Vector2D(x, y);
            else
                return getPlayerSpawn();
        }
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
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        Color c = Color.white;
        Font f = Assets.FONTS.get("40");

        if (generating) {
            this.renderBG(container, game, g);
            Text.drawStringCentered(g, "Generating world...", c, f);
        } else {
            super.render(container, game, g);
            Text.drawStringCenteredX(g, worldName, TutLauncher.HEIGHT / 2f - Text.getHeight(worldName, f) / 2 - 90, c, f);

            String seedS = "Seed: " + seed;
            Text.drawStringCenteredX(g, seedS, TutLauncher.HEIGHT / 2f - Text.getHeight(seedS, f) / 2 - 55, c, f);

            String worldSizeS = "Size: " + worldSize;
            Text.drawStringCenteredX(g, worldSizeS, TutLauncher.HEIGHT / 2f - Text.getHeight(worldSizeS, f) / 2 - 20, c, f);
        }
    }
}
