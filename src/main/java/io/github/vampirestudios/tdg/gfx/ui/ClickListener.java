package io.github.vampirestudios.tdg.gfx.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface ClickListener {

    void onClick();

    void update(GameContainer container, StateBasedGame game, int delta);
}
