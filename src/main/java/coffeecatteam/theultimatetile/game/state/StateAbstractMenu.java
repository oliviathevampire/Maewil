package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlinkCopyright;
import coffeecatteam.theultimatetile.utils.DiscordHandler;

import java.awt.*;

public class StateAbstractMenu extends State {

    public StateAbstractMenu(Engine engine) {
        this(engine, true);
    }

    public StateAbstractMenu(Engine engine, boolean initUI) {
        super(engine);

        if (initUI) {
            uiManager.addObject(new UIButton(engine, new Vector2D(15, engine.getHeight() - 95), "Main Menu", new ClickListener() {
                @Override
                public void onClick() {
                    initBack();
                    State.setState(((GameEngine) engine).stateMenu);

                    DiscordHandler.INSTANCE.updatePresence("Main Menu");
                }

                @Override
                public void tick() {
                }
            }));

            uiManager.addObject(new UIHyperlinkCopyright(new Vector2D(5, engine.getHeight() - 10), true));
        }
    }

    public void initBack() {
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
