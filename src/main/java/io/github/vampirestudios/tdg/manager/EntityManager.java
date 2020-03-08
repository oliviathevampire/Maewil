package io.github.vampirestudios.tdg.manager;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.ItemEntity;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.objs.items.ItemStack;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {

    public static int RENDER_VIEW;

    private MaewilEngine maewilEngine;
    private PlayerEntity player;
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

    public EntityManager(MaewilEngine maewilEngine) {
        this.maewilEngine = maewilEngine;
        entities = new ArrayList<>();
        entities.add(null);

        if (MaewilLauncher.WIDTH > MaewilLauncher.HEIGHT) {
            RENDER_VIEW = MaewilLauncher.WIDTH / Tile.TILE_SIZE + 1;
        } else {
            RENDER_VIEW = MaewilLauncher.HEIGHT / Tile.TILE_SIZE + 1;
        }
    }

    public void update(GameContainer container, float delta) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (isEntityInView(entity, (int) (RENDER_VIEW * 1.5f))) {
                entity.updateA(container, delta);

                if (!entity.isActive()) {
                    entity.die();
                    if (!(entity instanceof PlayerEntity)) {
                        entities.remove(i);
                        i--;
                    }
                }
            }
        }

        entities.sort(renderSorter);
    }

    public void render(org.mini2Dx.core.game.GameContainer container, Graphics g) {
        for (Entity entity : entities)
            if (isEntityInView(entity, RENDER_VIEW))
                entity.render(container, g);

        for (Entity entity : entities)
            if (isEntityInView(entity, RENDER_VIEW))
                entity.postRender(container, g);
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
        addEntity(new ItemEntity(maewilEngine, item), x, y, true);
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
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
