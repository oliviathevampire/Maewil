package coffeecatteam.theultimatetile.entities;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.player.EntityPlayer;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

public class EntityManager {

    // EntityManager
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

        /* Post Render */
        player.postRender(g);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void addEntity(Entity e, float x, float y, boolean atTile) {
        e.setX(x * Tile.TILE_WIDTH);
        e.setY(y * Tile.TILE_HEIGHT);
        if (!atTile) {
            e.setX(e.getX() / Tile.TILE_WIDTH);
            e.setY(e.getY() / Tile.TILE_HEIGHT);
        }
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
        entities.set(0, player);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public void reset() {
        entities.removeIf(entity -> !entity.getId().equals(player.getId()));
    }

    public void removePlayerMP(String username) {
        int index = 0;
        for (Entity e : entities) {
            if (e instanceof EntityPlayerMP && ((EntityPlayerMP) e).getUsername().equals(username)) {
                break;
            }
            index++;
        }
        entities.remove(index);
    }

    private int getPlayerMPIndex(String username) {
        int index = 0;
        for (Entity e : this.entities) {
            if (e instanceof EntityPlayerMP && ((EntityPlayerMP) e).getUsername().equals(username))
                break;
            index++;
        }
        return index;
    }

    public void movePlayer(String username, float x, float y) {
        int index = getPlayerMPIndex(username);
        entities.get(index).setX(x);
        entities.get(index).setY(y);
    }
}
