package coffeecatteam.theultimatetile.manager;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.EntityItem;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {

    public static int RENDER_VIEW;

    private TutEngine tutEngine;
    private EntityPlayer player;
    private ArrayList<Entity> entities;
    private Comparator<Entity> renderSorter = (a, b) -> {
        float y1 = a.getY() + a.getHeight();
        float y2 = b.getY() + b.getHeight();
        if (y1 == y2)
            return 0;
        else {
            if (y1 < y2)
                return -1;
            return 1;
        }
    };

    public EntityManager(TutEngine tutEngine) {
        this.tutEngine = tutEngine;
        entities = new ArrayList<>();
        entities.add(null);

        if (TutLauncher.WIDTH > TutLauncher.HEIGHT) {
            RENDER_VIEW = TutLauncher.WIDTH / Tile.TILE_SIZE + 1;
        } else {
            RENDER_VIEW = TutLauncher.HEIGHT / Tile.TILE_SIZE + 1;
        }
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (isEntityInView(entity, (int) (RENDER_VIEW * 1.5f))) {
                entity.updateA(container, game, delta);

                if (!entity.isActive()) {
                    entity.die();
                    if (!(entity instanceof EntityPlayer)) {
                        entities.remove(i);
                        i--;
                    }
                }
            }
        }

        entities.sort(renderSorter);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        for (Entity entity : entities)
            if (isEntityInView(entity, RENDER_VIEW))
                entity.preRender(container, game, g);

        for (Entity entity : entities)
            if (isEntityInView(entity, RENDER_VIEW))
                entity.render(container, game, g);

        for (Entity entity : entities)
            if (isEntityInView(entity, RENDER_VIEW))
                entity.postRender(container, game, g);
    }

    public boolean isEntityInView(Entity entity, int view) {
        return isEntityInView(entity.getPosition(), player.getPosition(), view);
    }

    public boolean isEntityInView(Vector2D origin, Vector2D destination, int view) {
        return origin.getDistanceFrom(destination) / Tile.TILE_SIZE < view;
    }

    public void addEntity(Entity entity) {
        entities.add(entity.newCopy());
    }

    public void addEntity(Entity entity, float x, float y, boolean atTile) {
        Entity newEntity = entity.newCopy();
        if (atTile) {
            newEntity.setPosition(new Vector2D(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE));
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
