package io.github.vampirestudios.tdg.manager;

import io.github.vampirestudios.tdg.gfx.ui.WidgetObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class UIManager {

    private ArrayList<WidgetObject> objects;

    public UIManager() {
        objects = new ArrayList<>();
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        for (WidgetObject obj : objects)
            obj.update(container, game, delta);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        for (WidgetObject obj : objects) {
            obj.render(container, game, g);
        }
    }

    public void postRender(GameContainer container, StateBasedGame game, Graphics g) {
        for (WidgetObject obj : objects) {
            obj.postRender(container, game, g);
        }
    }

    public void addObject(WidgetObject o) {
        objects.add(o);
    }

    public void removeObject(WidgetObject o) {
        objects.remove(o);
    }
}
