package coffeecatteam.theultimatetile.game.state.options;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.State;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlinkCopyright;
import coffeecatteam.theultimatetile.utils.DiscordHandler;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
            public void update(GameContainer container, int delta) {
            }
        }));

        uiManager.addObject(new UIHyperlinkCopyright(new Vector2D(5, engine.getHeight() - 20)));
    }

    @Override
    public void update(GameContainer container, int delta) {
        uiManager.update(container, delta);
    }

    @Override
    public void render(Graphics g) {
        Assets.BACKGROUND.draw(0, 0, engine.getWidth(), engine.getHeight());
        uiManager.render(g);
    }
}
