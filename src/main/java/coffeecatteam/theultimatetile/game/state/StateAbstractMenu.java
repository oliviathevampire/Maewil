package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlinkCopyright;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class StateAbstractMenu extends State {

    public StateAbstractMenu(Engine engine, Tile[] centre) {
        this(engine, centre, true);
    }

    public StateAbstractMenu(Engine engine, Tile[] centre, boolean initUI) {
        super(engine, centre);

        if (initUI) {
            uiManager.addObject(new UIButton(engine, new Vector2D(15, engine.getHeight() - 95), "Main Menu", new ClickListener() {
                @Override
                public void onClick() {
                    initBack();
                    StateManager.setCurrentState(((GameEngine) engine).stateMenu);

                    DiscordHandler.INSTANCE.updatePresence("Main Menu");
                }

                @Override
                public void update(GameContainer container, int delta) {
                }
            }));

            uiManager.addObject(new UIHyperlinkCopyright(new Vector2D(5, engine.getHeight() - 20)));
        }
    }

    public void initBack() {
    }

    @Override
    public void update(GameContainer container, int delta) {
        uiManager.update(container, delta);
    }

    @Override
    public void render(Graphics g) {
        this.renderBG(g);
        uiManager.render(g);
    }
}
