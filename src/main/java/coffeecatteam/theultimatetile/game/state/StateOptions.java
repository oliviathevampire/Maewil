package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.OptionsJsonParser;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import coffeecatteam.theultimatetile.utils.Logger;
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
            int diBtnWidth = 6 * 64;
            int diBtnHeight = 64;
            uiManager.addObject(new UIButton(15, 15, diBtnWidth, diBtnHeight, "Debug Info", new ClickListener() {
                @Override
                public void onClick() {
                    OPTIONS.setDebugMode(!OPTIONS.debugMode());
                    Logger.print("Degbug info mode " + (OPTIONS.debugMode() ? "enabled" : "disabled"));
                }

                @Override
                public void tick() {
                }
            }));

            int fpsBtnWidth = 6 * 64;
            int fpsBtnHeight = 64;
            uiManager.addObject(new UIButton(15, 94, fpsBtnWidth, fpsBtnHeight, "FPS counter", new ClickListener() {
                @Override
                public void onClick() {
                    OPTIONS.setFpsCounter(!OPTIONS.fpsCounter());
                    Logger.print("FPS counter " + (OPTIONS.fpsCounter() ? "enabled" : "disabled"));
                }

                @Override
                public void tick() {
                }
            }));

            int coBtnWidth = 6 * 64;
            int coBtnHeight = 64;
            uiManager.addObject(new UIButton(15, 173, coBtnWidth, coBtnHeight, "Controls", new ClickListener() {
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

            int soBtnWidth = 6 * 64;
            int soBtnHeight = 64;
            uiManager.addObject(new UIButton(15, 252, soBtnWidth, soBtnHeight, "Sounds", new ClickListener() {
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
    public void render(Graphics g) {
        super.render(g);
        g.drawImage(OPTIONS.debugMode() ? Assets.ICON_ON : Assets.ICON_OFF, 15 + 6 * 64, 15, 64, 64, null);

        Text.drawString(g, "EXPERIMENTAL!", engine.getWidth() / 2, engine.getHeight() / 2, true, true, Color.red, Assets.FONT_80);
    }
}
