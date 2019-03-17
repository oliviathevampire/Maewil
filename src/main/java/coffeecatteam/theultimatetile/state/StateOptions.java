package coffeecatteam.theultimatetile.state;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UICheckBox;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.OptionsJsonParser;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.*;

import java.io.IOException;

public class StateOptions extends StateAbstractMenu {

    public static OptionsJsonParser OPTIONS;
    public static final Tile[] BG = new Tile[]{Tiles.STONE, Tiles.GLITCH, Tiles.BROKEN_STONE};

    private UICheckBox debug, fps;

    public StateOptions(TutEngine tutEngine, boolean initUI) {
        super(tutEngine, BG, initUI);

        OPTIONS = new OptionsJsonParser("./options.json", tutEngine);
        try {
            OPTIONS.load();
        } catch (IOException | ParseException e) {
            tutEngine.getLogger().error(e);
        }

        if (initUI) {
            uiManager.addObject(debug = new UICheckBox(new Vector2D(tutEngine.getWidth() / 2f + 120, 20), 70, OPTIONS.debugMode()));
            uiManager.addObject(fps = new UICheckBox(new Vector2D(tutEngine.getWidth() / 2f + 100, 90), 70, OPTIONS.fpsCounter()));

            uiManager.addObject(new UIButton(tutEngine, true, 173, "Controls", new ClickListener() {
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
                public void update(GameContainer container, int delta) {
                }
            }));

            uiManager.addObject(new UIButton(tutEngine, true, 252, "Sounds", new ClickListener() {
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
            logger.error(e);
        }
    }

    @Override
    public void update(GameContainer container, int delta) {
        super.update(container, delta);
        OPTIONS.setDebugMode(debug.isChecked());
        OPTIONS.setFpsCounter(fps.isChecked());
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        Font font = Assets.FONTS.get("40");
        String debugText = "Debug Mode: ";
        Text.drawString(g, debugText, (int) (debug.getPosition().x - Text.getWidth(debugText, font)), (int) (debug.getPosition().y + Text.getHeight(debugText, font)) + 25, false, Color.white, font);
        String fpsText = "Show FPS: ";
        Text.drawString(g, fpsText, (int) (fps.getPosition().x - Text.getWidth(fpsText, font)), (int) (fps.getPosition().y + Text.getHeight(fpsText, font)) + 25, false, Color.white, font);

        Text.drawString(g, "EXPERIMENTAL!", tutEngine.getWidth() / 2, tutEngine.getHeight() / 2, true, true, Color.red, Assets.FONTS.get("80"));
    }
}
