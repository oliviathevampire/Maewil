package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.Tiles;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.OptionsJsonParser;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.io.IOException;

public class StateOptions extends StateAbstractMenu {

    public static OptionsJsonParser OPTIONS;
    public static final Tile[] BG = new Tile[]{Tiles.STONE, Tiles.GLITCH};

    public StateOptions(Engine engine, boolean initUI) {
        super(engine, BG, initUI);

        OPTIONS = new OptionsJsonParser("./options.json", engine);
        try {
            OPTIONS.load();
        } catch (IOException | ParseException e) {
            engine.getLogger().print(e);
        }

        if (initUI) {
            uiManager.addObject(new UIButton(engine, new Vector2D(15, 15), "Debug Info", new ClickListener() {
                @Override
                public void onClick() {
                    OPTIONS.setDebugMode(!OPTIONS.debugMode());
                    logger.print("Degbug info mode " + (OPTIONS.debugMode() ? "enabled" : "disabled"));
                }

                @Override
                public void update(GameContainer container, int delta) {
                }
            }));

            uiManager.addObject(new UIButton(engine, new Vector2D(15, 94), "FPS counter", new ClickListener() {
                @Override
                public void onClick() {
                    OPTIONS.setFpsCounter(!OPTIONS.fpsCounter());
                    logger.print("FPS counter " + (OPTIONS.fpsCounter() ? "enabled" : "disabled"));
                }

                @Override
                public void update(GameContainer container, int delta) {
                }
            }));

            uiManager.addObject(new UIButton(engine, new Vector2D(15, 173), "Controls", new ClickListener() {
                @Override
                public void onClick() {
                    StateManager.setCurrentState(((GameEngine) engine).optionsControls);
                    try {
                        OPTIONS.load();
                    } catch (IOException | ParseException e) {
                        logger.print(e);
                    }
                    setPresence("Controls");
                }

                @Override
                public void update(GameContainer container, int delta) {
                }
            }));

            uiManager.addObject(new UIButton(engine, new Vector2D(15, 252), "Sounds", new ClickListener() {
                @Override
                public void onClick() {
                    StateManager.setCurrentState(((GameEngine) engine).optionsSpounds);
                    try {
                        OPTIONS.load();
                    } catch (IOException | ParseException e) {
                        logger.print(e);
                    }
                    setPresence("Sounds");
                }

                @Override
                public void update(GameContainer container, int delta) {
                }
            }));
        }
    }

    private void setPresence(String presence) {
        DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options/" + presence);
    }

    @Override
    public void initBack() {
        try {
            OPTIONS.save();
        } catch (IOException e) {
            logger.print(e);
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        Image ico = OPTIONS.debugMode() ? Assets.ICON_ON : Assets.ICON_OFF;
        ico.draw(15 + 6 * 64, 15, 64, 64);

        Text.drawString(g, "EXPERIMENTAL!", engine.getWidth() / 2, engine.getHeight() / 2, true, true, Color.red, Assets.FONTS.get("80"));
    }
}
