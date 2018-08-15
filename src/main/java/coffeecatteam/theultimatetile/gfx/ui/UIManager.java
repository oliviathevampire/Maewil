package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {

    private Handler handler;
    private ArrayList<UIObject> objects;

    public UIManager(Handler handler) {
        this.handler = handler;
        objects = new ArrayList<>();
    }

    public void tick() {
        for (UIObject o : objects)
            o.tick();
    }

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

    public void addObject(UIObject o) {
        objects.add(o);
    }

    public void removeObject(UIObject o) {
        objects.remove(o);
    }
}
