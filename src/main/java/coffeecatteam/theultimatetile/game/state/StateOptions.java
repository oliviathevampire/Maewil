package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.OptionsJsonParser;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import coffeecatteam.coffeecatutils.Logger;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

public class StateOptions extends StateAbstractMenu {

    public static OptionsJsonParser OPTIONS;

    public StateOptions(Engine engine, boolean initUI) {
        super(engine, initUI);
        init();

        OPTIONS = new OptionsJsonParser("./options.json", engine);
        try {
            OPTIONS.load();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        if (initUI) {
            uiManager.addObject(new UIButton(engine, 15, 15, "Debug Info", new ClickListener() {
                @Override
                public void onClick() {
                    OPTIONS.setDebugMode(!OPTIONS.debugMode());
                    Logger.print("Degbug info mode " + (OPTIONS.debugMode() ? "enabled" : "disabled"));
                }

                @Override
                public void tick() {
                }
            }));

            uiManager.addObject(new UIButton(engine, 15, 94, "FPS counter", new ClickListener() {
                @Override
                public void onClick() {
                    OPTIONS.setFpsCounter(!OPTIONS.fpsCounter());
                    Logger.print("FPS counter " + (OPTIONS.fpsCounter() ? "enabled" : "disabled"));
                }

                @Override
                public void tick() {
                }
            }));

            uiManager.addObject(new UIButton(engine, 15, 173, "Controls", new ClickListener() {
                @Override
                public void onClick() {
                    State.setState(((GameEngine) engine).optionsControls);
                    try {
                        OPTIONS.load();
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    setPresence("Controls");
                }

                @Override
                public void tick() {
                }
            }));

            uiManager.addObject(new UIButton(engine, 15, 252, "Sounds", new ClickListener() {
                @Override
                public void onClick() {
                    State.setState(((GameEngine) engine).optionsSpounds);
                    try {
                        OPTIONS.load();
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    setPresence("Sounds");
                }

                @Override
                public void tick() {
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
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.drawImage(OPTIONS.debugMode() ? Assets.ICON_ON : Assets.ICON_OFF, 15 + 6 * 64, 15, 64, 64, null);

        Text.drawString(g, "EXPERIMENTAL!", engine.getWidth() / 2, engine.getHeight() / 2, true, true, Color.red, Assets.FONT_80);
    }
}
