package io.github.vampirestudios.tdg.manager;

import io.github.vampirestudios.tdg.gfx.ui.WidgetObject;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;

public class UIManager {

    private ArrayList<WidgetObject> objects;

    public UIManager() {
        objects = new ArrayList<>();
    }

    public void update(GameContainer container, float delta) {
        for (WidgetObject obj : objects)
            obj.update(container, delta);
    }

    public void render(org.mini2Dx.core.game.GameContainer container, Graphics g) {
        for (WidgetObject obj : objects) {
            obj.render(container, g);
        }
    }

    public void postRender(org.mini2Dx.core.game.GameContainer container, org.mini2Dx.core.graphics.Graphics g) {
        for (WidgetObject obj : objects) {
            obj.postRender(container, g);
        }
    }

    public void addObject(WidgetObject o) {
        objects.add(o);
    }

    public void removeObject(WidgetObject o) {
        objects.remove(o);
    }
}
