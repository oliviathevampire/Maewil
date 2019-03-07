package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.EntityItem;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {

    private TutEngine tutEngine;
    private EntityPlayer player;
    private ArrayList<Entity> entities;
    private Comparator<Entity> renderSorter = (Entity a, Entity b) -> {
        if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
            return -1;
        return 1;
    };

    public EntityManager(TutEngine tutEngine, EntityPlayer player) {
        this.tutEngine = tutEngine;
        this.player = player;
        entities = new ArrayList<>();
        addEntity(this.player);
    }

    public void update(GameContainer container, int delta) {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.updateA(container, delta);
            if (!e.isActive()) {
                e.die(entities, i);
                if (!(e instanceof EntityPlayer))
                    i--;
            }
        }
        entities.sort(renderSorter);
    }

    public void render(Graphics g) {
        for (Entity e : entities)
            e.preRender(g);

        for (Entity e : entities) {
            e.render(g);
        }

        for (Entity e : entities)
            e.postRender(g);
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

    public void addItem(ItemStack item, float x, float y, boolean atTile) {
        addEntity(new EntityItem(tutEngine, item), x, y, atTile);
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
}
