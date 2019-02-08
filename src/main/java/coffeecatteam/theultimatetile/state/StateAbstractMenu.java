package coffeecatteam.theultimatetile.state;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlinkCopyright;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class StateAbstractMenu extends State {

    public StateAbstractMenu(TutEngine tutEngine, Tile[] centre) {
        this(tutEngine, centre, true);
    }

    public StateAbstractMenu(TutEngine tutEngine, Tile[] centre, boolean initUI) {
        super(tutEngine, centre);

        if (initUI) {
            uiManager.addObject(new UIButton(tutEngine, new Vector2D(15, tutEngine.getHeight() - 95), "Main Menu", new ClickListener() {
                @Override
                public void onClick() {
                    initBack();
                    StateManager.setCurrentState(((TutEngine) tutEngine).stateMenu);

                    DiscordHandler.INSTANCE.updatePresence("Main Menu");
                }

                @Override
                public void update(GameContainer container, int delta) {
                }
            }));

            uiManager.addObject(new UIHyperlinkCopyright(new Vector2D(5, tutEngine.getHeight() - 20)));
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
