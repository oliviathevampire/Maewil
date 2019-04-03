package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.TutLauncher;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UICheckBox;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.OptionsJsonParser;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;
import coffeecatteam.theultimatetile.state.StateAbstractMenu;
import coffeecatteam.theultimatetile.state.StateManager;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

public class StateOptions extends StateAbstractMenu {

    public static OptionsJsonParser OPTIONS;
    static final Tile[] BG = new Tile[]{
            Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE, Tiles.STONE,
            Tiles.BROKEN_STONE, Tiles.BROKEN_STONE, Tiles.BROKEN_STONE, Tiles.BROKEN_STONE, Tiles.BROKEN_STONE, Tiles.BROKEN_STONE, Tiles.BROKEN_STONE,
            Tiles.COAL_ORE, Tiles.IRON_ORE, Tiles.GOLD_ORE, Tiles.DIAMOND_ORE
    };

    private UICheckBox debug, fps, vsync;

    public StateOptions(TutEngine tutEngine, boolean initUI) {
        super(tutEngine, BG, initUI);

        OPTIONS = new OptionsJsonParser("./data/options.json");
        try {
            OPTIONS.load();
        } catch (IOException | ParseException e) {
            TutLauncher.LOGGER.error(e);
        }

        if (initUI) {
            uiManager.addObject(debug = new UICheckBox(new Vector2D(TutLauncher.WIDTH / 2f + 120, 20), 70, OPTIONS.debugMode()));
            uiManager.addObject(fps = new UICheckBox(new Vector2D(TutLauncher.WIDTH / 2f + 100, 90), 70, OPTIONS.fpsCounter()));
            uiManager.addObject(vsync = new UICheckBox(new Vector2D(TutLauncher.WIDTH / 2f + 100, 160), 70, OPTIONS.vSync()));

            uiManager.addObject(new UIButton(tutEngine, true, 243, "Controls", new ClickListener() {
                @Override
                public void onClick() {
                    StateManager.setCurrentState(tutEngine.optionsControls);
                    try {
                        OPTIONS.load();
                    } catch (IOException | ParseException e) {
                        logger.error(e);
                    }
                    setPresence("Controls");
                }

                @Override
                public void update(GameContainer container, StateBasedGame game, int delta) {
                }
            }));

            uiManager.addObject(new UIButton(tutEngine, true, 322, "Sounds", new ClickListener() {
                @Override
                public void onClick() {
                    StateManager.setCurrentState(tutEngine.optionsSpounds);
                    try {
                        OPTIONS.load();
                    } catch (IOException | ParseException e) {
                        logger.error(e);
                    }
                    setPresence("Sounds");
                }

                @Override
                public void update(GameContainer container, StateBasedGame game, int delta) {
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
            logger.error(e);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
        OPTIONS.setDebugMode(debug.isChecked());
        OPTIONS.setFpsCounter(fps.isChecked());
        OPTIONS.setVSync(vsync.isChecked());
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);
        Font font = Assets.FONTS.get("40");
        String debugText = "Debug Mode: ";
        Text.drawString(g, debugText, (int) (debug.getPosition().x - Text.getWidth(debugText, font)), (int) (debug.getPosition().y + Text.getHeight(debugText, font)) + 25, false, Color.white, font);
        String fpsText = "Show FPS: ";
        Text.drawString(g, fpsText, (int) (fps.getPosition().x - Text.getWidth(fpsText, font)), (int) (fps.getPosition().y + Text.getHeight(fpsText, font)) + 25, false, Color.white, font);
        String vSyncText = "Use VSync: ";
        Text.drawString(g, vSyncText, (int) (vsync.getPosition().x - Text.getWidth(fpsText, font)), (int) (vsync.getPosition().y + Text.getHeight(fpsText, font)) + 25, false, Color.white, font);

        Text.drawString(g, "EXPERIMENTAL!", TutLauncher.WIDTH / 2f, TutLauncher.HEIGHT / 2f, true, true, Color.red, Assets.FONTS.get("80"));
    }
}
