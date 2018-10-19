package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.entities.creatures.passive.EntityCow;
import coffeecatteam.theultimatetile.entities.creatures.passive.EntityPig;
import coffeecatteam.theultimatetile.entities.creatures.passive.EntitySheep;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityBouncer;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntitySkeleton;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityThing;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityZombie;
import coffeecatteam.theultimatetile.entities.statics.interactable.EntityCampfire;
import coffeecatteam.theultimatetile.entities.statics.interactable.EntityShopStall;
import coffeecatteam.theultimatetile.entities.statics.EntityUltimateTile;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityBush;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityCrop;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityRock;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityTree;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.iinterface.IRenderabelManager;
import coffeecatteam.theultimatetile.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager implements IRenderabelManager {

    /*
     * EntityLoader
     */
    public static Entity loadEntity(TheUltimateTile theUltimateTile, String id) {
        switch (id) {
            /*
             * Nature / Statics
             */
            case "tree_small":
                return new EntityTree(theUltimateTile, id, EntityTree.TreeType.SMALL);
            case "tree_medium":
                return new EntityTree(theUltimateTile, id, EntityTree.TreeType.MEDIUM);
            case "tree_large":
                return new EntityTree(theUltimateTile, id, EntityTree.TreeType.LARGE);
            case "rock_medium":
                return new EntityRock(theUltimateTile, id, Assets.ROCK_V1);
            case "rock_small":
                return new EntityRock(theUltimateTile, id, Assets.ROCK_V2);
            case "bush_small":
                return new EntityBush(theUltimateTile, id, Assets.BUSH_SMALL, Entity.DEFAULT_WIDTH);
            case "bush_large":
                return new EntityBush(theUltimateTile, id, Assets.BUSH_LARGE, Entity.DEFAULT_WIDTH * 2);

            /*
             * Crops
             */
            case "crop_carrot":
                return new EntityCrop(theUltimateTile, id, Assets.CROP_CARROT, ItemManager.CARROT);
            case "crop_wheat":
                return new EntityCrop(theUltimateTile, id, Assets.CROP_WHEAT, ItemManager.WHEAT);
            case "crop_potato":
                return new EntityCrop(theUltimateTile, id, Assets.CROP_POTATO, ItemManager.POTATO);
            case "crop_tomato":
                return new EntityCrop(theUltimateTile, id, Assets.CROP_TOMATO, ItemManager.TOMATO);
            case "crop_corn":
                return new EntityCrop(theUltimateTile, id, Assets.CROP_CORN, ItemManager.CORN);

            /*
             * Undead
             */
            case "zombie":
                return new EntityZombie(theUltimateTile, id);
            case "skeleton":
                return new EntitySkeleton(theUltimateTile, id);
            case "bouncer":
                return new EntityBouncer(theUltimateTile, id);
            case "thing":
                return new EntityThing(theUltimateTile, id);

            /*
             * Passive
             */
            case "pig":
                return new EntityPig(theUltimateTile, id);
            case "cow":
                return new EntityCow(theUltimateTile, id);
            case "sheep":
                return new EntitySheep(theUltimateTile, id);

            /*
             * Other
             */
            case "ultimate":
                return new EntityUltimateTile(theUltimateTile, id);

            case "shop":
                return new EntityShopStall(theUltimateTile, id);
            case "campfire":
                return new EntityCampfire(theUltimateTile, id);
        }
        return new EntityRock(theUltimateTile, "rock", Assets.ROCK_V1);
    }

    /*
     * EntityManager
     */
    private TheUltimateTile theUltimateTile;
    private EntityPlayer player;
    private ArrayList<Entity> entities;
    private Comparator<Entity> renderSorter = (Entity a, Entity b) -> {
        if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
            return -1;
        return 1;
    };

    public EntityManager(TheUltimateTile theUltimateTile, EntityPlayer player) {
        this.theUltimateTile = theUltimateTile;
        this.player = player;
        entities = new ArrayList<>();
        addEntity(this.player);
    }

    @Override
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

    @Override
    public void render(Graphics g) {
        for (Entity e : entities)
            e.preRender(g);

        for (Entity e : entities)
            e.render(g);

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

    public TheUltimateTile getTheUltimateTile() {
        return theUltimateTile;
    }

    public void setTheUltimateTile(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
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
