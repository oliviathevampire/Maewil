package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.ui.UIObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class UIManager {

    private TutEngine tutEngine;
    private ArrayList<UIObject> objects;

    public UIManager(TutEngine tutEngine) {
        this.tutEngine = tutEngine;
        objects = new ArrayList<>();
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        for (UIObject obj : objects)
            obj.update(container, game, delta);
    }

    public void render(Graphics g) {
        for (UIObject obj : objects) {
            obj.render(g);
        }
    }

    public void postRender(Graphics g) {
        for (UIObject obj : objects) {
            obj.postRender(g);
        }
    }

    public void addObject(UIObject o) {
        objects.add(o);
    }

    public void removeObject(UIObject o) {
        objects.remove(o);
    }
}
