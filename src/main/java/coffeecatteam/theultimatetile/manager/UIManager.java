package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.gfx.ui.UIObject;
import coffeecatteam.theultimatetile.manager.iinterface.IRenderableManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager implements IRenderableManager {

    private GameEngine gameEngine;
    private ArrayList<UIObject> objects;

    public UIManager(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        objects = new ArrayList<>();
    }

    @Override
    public void tick() {
        for (UIObject o : objects)
            o.tick();
    }

    @Override
    public void render(Graphics g) {
        for (UIObject o : objects)
            o.render(g);
    }

    public void onMouseMoved(MouseEvent e) {
        for (UIObject o : objects)
            o.onMouseMoved(e);
    }

    public void onMouseRelease(MouseEvent e) {
        for (UIObject o : objects)
            o.onMouseRelease(e);
    }

    public void mouseDragged(MouseEvent e) {
        for (UIObject o : objects)
            o.onMouseDragged(e);
    }

    public void addObject(UIObject o) {
        objects.add(o);
    }

    public void removeObject(UIObject o) {
        objects.remove(o);
    }
}
