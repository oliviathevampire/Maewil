package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.ui.UIObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {

    private Engine engine;
    private ArrayList<UIObject> objects;

    public UIManager(Engine engine) {
        this.engine = engine;
        objects = new ArrayList<>();
    }

    public void update(GameContainer container, int delta) {
        for (UIObject obj : objects)
            obj.update(container, delta);
    }

    public void render(Graphics g) {
        for (UIObject obj : objects) {
            obj.render(g);
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
