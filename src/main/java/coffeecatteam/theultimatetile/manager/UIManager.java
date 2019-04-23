package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.gfx.ui.UIObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class UIManager {

    private ArrayList<UIObject> objects;

    public UIManager() {
        objects = new ArrayList<>();
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        for (UIObject obj : objects)
            obj.update(container, game, delta);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        for (UIObject obj : objects) {
            obj.render(container, game, g);
        }
    }

    public void postRender(GameContainer container, StateBasedGame game, Graphics g) {
        for (UIObject obj : objects) {
            obj.postRender(container, game, g);
        }
    }

    public void addObject(UIObject o) {
        objects.add(o);
    }

    public void removeObject(UIObject o) {
        objects.remove(o);
    }
}
