package coffeecatteam.theultimatetile.game.state.options;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.State;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlinkCopyright;
import coffeecatteam.theultimatetile.utils.DiscordHandler;

import java.awt.*;
import java.io.IOException;

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
                    logger.print(e);
                }

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void tick() {
            }
        }));

        uiManager.addObject(new UIHyperlinkCopyright(new Vector2D(5, engine.getHeight() - 10), true));
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
