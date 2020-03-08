package io.github.vampirestudios.tdg.screen;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.objs.tiles.*;
import io.github.vampirestudios.tdg.screen.game.GameScreen;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.world.TileList;
import io.github.vampirestudios.tdg.world.World;
import io.github.vampirestudios.tdg.world.WorldGenerator;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

import java.util.concurrent.ThreadLocalRandom;

public class GenerateRandomWorldScreen extends AbstractMenuScreen {

    private String worldName;
    private long seed;
    private float sizeMod;
    private int worldSize;

    private TileList bgTiles, fgTiles;
    private boolean generating = false;

    public GenerateRandomWorldScreen(MaewilEngine maewilEngine) {
        super(maewilEngine, TileBackgrounds.GRASS.getTiles());

        worldName = getWorldName();
        long range = 1000000000L;
        seed = generateSeed(-range, range);
        sizeMod = NumberUtils.getRandomFloat(0.0f, 50.0f);

        int sizeMin = Math.min((int) -(sizeMod * 2), (int) (sizeMod * 2));
        int sizeMax = Math.max((int) -(sizeMod * 2), (int) (sizeMod * 2));
        int sizeModExtra;
        /*if (sizeMin < sizeMax)
            sizeModExtra = NumberUtils.getRandomInt(sizeMin, sizeMax);
        else
            sizeModExtra = NumberUtils.getRandomInt(sizeMax, sizeMin);*/
        sizeModExtra = NumberUtils.getRandomInt(sizeMin, sizeMax);
        int minWorldSize = 400;
        worldSize = minWorldSize + sizeModExtra;

        uiManager.addObject(new WidgetButton(maewilEngine, true, "Generate World", new ClickListener() {
            @Override
            public void onClick() {
                generateWorld();
            }

            @Override
            public void update(org.mini2Dx.core.game.GameContainer container, float delta) {

            }
        }));
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        super.update(container, delta);
        uiManager.update(container, delta);
    }

    private String getWorldName() {
        String defaultName = "New World" + "_" + NumberUtils.getRandomInt(1000);
        return defaultName.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_]+", "");
    }

    private void generateWorld() {
        /*
         * Tiles, entities, etc..
         */
        logger.info("Generating world with a name of " + worldName);
        maewilEngine.getPlayer().reset();

        double blendSize = 25.0d + sizeMod;
        WorldGenerator generator = new WorldGenerator(maewilEngine, seed, worldSize);
        generator.setBlendSize(blendSize);
        generator.generate();

        generating = true;
        while (generator.isGenerating()) {
            if (generator.isGenerated()) {
                bgTiles = generator.getBackgroundTiles();
                fgTiles = generator.getForegroundTiles();
            }
        }

        /*
         * Set world & username
         */
        World world = new World(maewilEngine, worldName, worldSize, worldSize, getPlayerSpawn(), bgTiles, fgTiles);
        ScreenManager.setCurrentScreen(new GameScreen(maewilEngine, "./data/saves/" + worldName, worldName, world));

        if (ArgUtils.hasArgument("-username"))
            maewilEngine.setUsername(ArgUtils.getArgument("-username"));
        else
            maewilEngine.setUsername("TEST"); // TODO: Add text box to enter username on startup

        generating = false;
    }

    private Vector2D getPlayerSpawn() {
        int x = NumberUtils.getRandomInt(worldSize - 1);
        int y = NumberUtils.getRandomInt(worldSize - 1);
        Tile tile = fgTiles.getTileAtPos(x, y);

        for (Entity entity : maewilEngine.getEntityManager().getEntities()) {
            if (!(entity instanceof PlayerEntity)) {
                int nx = x * Tile.TILE_SIZE;
                int ny = y * Tile.TILE_SIZE;
                if (maewilEngine.getEntityManager().isEntityInView(new Vector2D(nx, ny), entity.getPosition(), 2))
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
    public void render(GameContainer container, Graphics g) {
        Color c = Color.white;
        Font f = Assets.FONTS.get("40");

        if (generating) {
            this.renderBG(container, g);
            Text.drawStringCentered(g, "Generating world...", c, f);
        } else {
            super.render(container, g);
            uiManager.render(container, g);
            Text.drawStringCenteredX(g, worldName, MaewilLauncher.HEIGHT / 2f - Text.getHeight(worldName, f) / 2 - 90, c, f);

            String seedS = "Seed: " + seed;
            Text.drawStringCenteredX(g, seedS, MaewilLauncher.HEIGHT / 2f - Text.getHeight(seedS, f) / 2 - 55, c, f);

            String worldSizeS = "Size: " + worldSize;
            Text.drawStringCenteredX(g, worldSizeS, MaewilLauncher.HEIGHT / 2f - Text.getHeight(worldSizeS, f) / 2 - 20, c, f);
        }
    }
}
