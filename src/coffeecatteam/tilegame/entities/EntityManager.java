package coffeecatteam.tilegame.entities;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.creatures.EntityPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {

    private Handler handler;
    private EntityPlayer player;
    private ArrayList<Entity> entities;
    private Comparator<Entity> renderSorter = (Entity a, Entity b) -> {
        if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
            return -1;
        return 1;
    };

    public EntityManager(Handler handler, EntityPlayer player) {
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<>();
        addEntity(this.player);
    }

    public void tick() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
        }
        entities.sort(renderSorter);
    }

    public void render(Graphics g) {
        for (Entity e : entities)
            e.render(g);
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
