package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.game.entities.creatures.passive.EntityCow;
import coffeecatteam.theultimatetile.game.entities.creatures.passive.EntityFox;
import coffeecatteam.theultimatetile.game.entities.creatures.passive.EntityPig;
import coffeecatteam.theultimatetile.game.entities.creatures.passive.EntitySheep;
import coffeecatteam.theultimatetile.game.entities.creatures.undead.EntityBouncer;
import coffeecatteam.theultimatetile.game.entities.creatures.undead.EntitySkeleton;
import coffeecatteam.theultimatetile.game.entities.creatures.undead.EntityThing;
import coffeecatteam.theultimatetile.game.entities.creatures.undead.EntityZombie;
import coffeecatteam.theultimatetile.game.entities.statics.EntityUltimateTile;
import coffeecatteam.theultimatetile.game.entities.statics.interactable.EntityCampfire;
import coffeecatteam.theultimatetile.game.entities.statics.interactable.EntityShopStall;
import coffeecatteam.theultimatetile.game.entities.statics.nature.EntityBush;
import coffeecatteam.theultimatetile.game.entities.statics.nature.EntityCrop;
import coffeecatteam.theultimatetile.game.entities.statics.nature.EntityRock;
import coffeecatteam.theultimatetile.game.entities.statics.nature.EntityTree;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.game.tiles.Tile;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {

    /*
     * EntityLoader
     */
    public static Entity loadEntity(Engine engine, String id) {
        switch (id) {
            /*
             * Nature / Statics
             */
            case "tree_small":
                return new EntityTree(engine, id, EntityTree.TreeType.SMALL);
            case "tree_medium":
                return new EntityTree(engine, id, EntityTree.TreeType.MEDIUM);
            case "tree_large":
                return new EntityTree(engine, id, EntityTree.TreeType.LARGE);
            case "rock_medium":
                return new EntityRock(engine, id, Assets.ROCK_V1);
            case "rock_small":
                return new EntityRock(engine, id, Assets.ROCK_V2);
            case "bush_small":
                return new EntityBush(engine, id, Assets.BUSH_SMALL, Entity.DEFAULT_WIDTH);
            case "bush_large":
                return new EntityBush(engine, id, Assets.BUSH_LARGE, Entity.DEFAULT_WIDTH * 2);

            /*
             * Crops
             */
            case "crop_carrot":
                return new EntityCrop(engine, id, Assets.CROP_CARROT, ItemManager.CARROT);
            case "crop_wheat":
                return new EntityCrop(engine, id, Assets.CROP_WHEAT, ItemManager.WHEAT);
            case "crop_potato":
                return new EntityCrop(engine, id, Assets.CROP_POTATO, ItemManager.POTATO);
            case "crop_tomato":
                return new EntityCrop(engine, id, Assets.CROP_TOMATO, ItemManager.TOMATO);
            case "crop_corn":
                return new EntityCrop(engine, id, Assets.CROP_CORN, ItemManager.CORN);

            /*
             * Undead
             */
            case "zombie":
                return new EntityZombie(engine, id);
            case "skeleton":
                return new EntitySkeleton(engine, id);
            case "bouncer":
                return new EntityBouncer(engine, id);
            case "thing":
                return new EntityThing(engine, id);

            /*
             * Passive
             */
            case "pig":
                return new EntityPig(engine, id);
            case "cow":
                return new EntityCow(engine, id);
            case "sheep":
                return new EntitySheep(engine, id);
            case "fox":
                return new EntityFox(engine, id);

            /*
             * Other
             */
            case "ultimate":
                return new EntityUltimateTile(engine, id);

            case "shop":
                return new EntityShopStall(engine, id);
            case "campfire":
                return new EntityCampfire(engine, id);
        }
        return new EntityRock(engine, "rock", Assets.ROCK_V1);
    }

    /*
     * EntityManager
     */
    private Engine engine;
    private EntityPlayer player;
    private ArrayList<Entity> entities;
    private Comparator<Entity> renderSorter = (Entity a, Entity b) -> {
        if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
            return -1;
        return 1;
    };

    public EntityManager(Engine engine, EntityPlayer player) {
        this.engine = engine;
        this.player = player;
        entities = new ArrayList<>();
        addEntity(this.player);
    }

    public void tick() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tickA();
            if (!e.isActive()) {
                e.die(entities, i);
                if (!(e instanceof EntityPlayer))
                    i--;
            }
        }
        entities.sort(renderSorter);
    }

    public void render(Graphics2D g) {
        for (Entity e : entities)
            e.preRender(engine.newGraphics());

        for (Entity e : entities) {
            e.setShowHitbox(StateOptions.OPTIONS.debugMode());
            e.render(engine.newGraphics());
        }

        for (Entity e : entities)
            e.postRender(engine.newGraphics());
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
