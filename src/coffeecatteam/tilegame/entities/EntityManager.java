package coffeecatteam.tilegame.entities;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.creatures.EntityPlayer;
import coffeecatteam.tilegame.gfx.overlays.Overlay;
import coffeecatteam.tilegame.gfx.overlays.OverlayPlayerHealth;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class EntityManager {

    private Handler handler;
    private EntityPlayer player;
    private ArrayList<Entity> entities;
    private Comparator<Entity> renderSorter = (Entity a, Entity b) -> {
        if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
            return -1;
        return 1;
    };

    /* Overlays */
    private Overlay healthOverlay;

    public EntityManager(Handler handler, EntityPlayer player) {
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<>();
        addEntity(this.player);

        /* Overlays */
        healthOverlay = new OverlayPlayerHealth(handler, player);
    }

    public void tick() {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            e.tick();
            if (!e.isActive())
                e.die(it);
        }
        entities.sort(renderSorter);
    }

    public void render(Graphics g) {
        for (Entity e : entities)
            e.renderA(g);

        /* Overlays */
        healthOverlay.render(g);

        /* Post Render */
        player.postRender(g);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}
