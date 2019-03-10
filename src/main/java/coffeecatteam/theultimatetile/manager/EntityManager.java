package coffeecatteam.theultimatetile.manager;

import coffeecatteam.coffeecatutils.position.Vector2D;
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

    public EntityManager(TutEngine tutEngine) {
        this.tutEngine = tutEngine;
        entities = new ArrayList<>();
        entities.add(null);
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

    public void addEntity(Entity entity) {
        entities.add(entity.newCopy());
    }

    public void addEntity(Entity entity, float x, float y, boolean atTile) {
        Entity newEntity = entity.newCopy();
        if (atTile) {
            newEntity.setPosition(new Vector2D(x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT));
        } else {
            newEntity.setPosition(new Vector2D(x, y));
        }
        entities.add(newEntity);
    }

    public void addItem(ItemStack item, float x, float y) {
        addEntity(new EntityItem(tutEngine, item), x, y, true);
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
