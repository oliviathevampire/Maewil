package coffeecatteam.theultimatetile.game.state.options;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.State;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
import coffeecatteam.theultimatetile.utils.DiscordHandler;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public abstract class StateAbstractOptions extends State {

    public StateAbstractOptions(GameEngine gameEngineIn) {
        super(gameEngineIn);

        uiManager.addObject(new UIButton(gameEngineIn, new Vector2D(15, engine.getHeight() - 95), "Back", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(engine.stateOptions);
                try {
                    StateOptions.OPTIONS.save();
                } catch (IOException e) {
                    Logger.print(e);
                }

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void tick() {
            }
        }));

        Font font = Assets.FONT_20;
        String crText = "Copyright (C) CoffeeCatTeam 2018";
        int crWidth = Text.getWidth((Graphics2D) engine.getGraphics(), crText, font);
        int crHeight = Text.getHeight((Graphics2D) engine.getGraphics(), font);
        int crx = 5;
        int cry = engine.getHeight() - 10;
        uiManager.addObject(new UIHyperlink(new Vector2D(crx, cry), crWidth, crHeight, crText, true, font, new ClickListener() {
            @Override
            public void onClick() {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI link = new URI("https://github.com/CoffeeCatRailway/TheUltimateTile/blob/master/LICENSE.md");
                    desktop.browse(link);
                } catch (Exception e) {
                    Logger.print(e);
                }
            }

            @Override
            public void tick() {
            }
        }));
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, engine.getWidth(), engine.getHeight(), null);
        uiManager.render(g);
    }
}
