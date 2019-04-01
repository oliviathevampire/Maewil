package coffeecatteam.theultimatetile.state;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.TutLauncher;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlinkCopyright;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class StateAbstractMenu extends State {

    public StateAbstractMenu(TutEngine tutEngine, Tile[] centre) {
        this(tutEngine, centre, true);
    }

    public StateAbstractMenu(TutEngine tutEngine, Tile[] centre, boolean initUI) {
        super(tutEngine, centre);

        if (initUI) {
            uiManager.addObject(new UIButton(tutEngine, new Vector2D(15, TutLauncher.HEIGHT - 95), "Main Menu", new ClickListener() {
                @Override
                public void onClick() {
                    initBack();
                    StateManager.setCurrentState(tutEngine.stateMenu);

                    DiscordHandler.INSTANCE.updatePresence("Main Menu");
                }

                @Override
                public void update(GameContainer container, StateBasedGame game, int delta) {
                }
            }));

            uiManager.addObject(new UIHyperlinkCopyright(new Vector2D(5, TutLauncher.HEIGHT - 20)));
        }
    }

    public void initBack() {
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        uiManager.update(container, game, delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        this.renderBG(container, game, g);
        uiManager.render(container, game, g);
    }
}
