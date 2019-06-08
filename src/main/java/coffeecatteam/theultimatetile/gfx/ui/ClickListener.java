package coffeecatteam.theultimatetile.gfx.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface ClickListener {

    void onClick();

    void update(GameContainer container, StateBasedGame game, int delta);
}
