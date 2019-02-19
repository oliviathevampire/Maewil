package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.entities.creatures.passive.EntityCow;
import coffeecatteam.theultimatetile.entities.creatures.passive.EntityFox;
import coffeecatteam.theultimatetile.entities.creatures.passive.EntityPig;
import coffeecatteam.theultimatetile.entities.creatures.passive.EntitySheep;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityBouncer;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntitySkeleton;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityThing;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityZombie;
import coffeecatteam.theultimatetile.entities.statics.EntityExtraLife;
import coffeecatteam.theultimatetile.entities.statics.EntityUltimateTile;
import coffeecatteam.theultimatetile.entities.statics.interactable.EntityCampfire;
import coffeecatteam.theultimatetile.entities.statics.interactable.EntityShopStall;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityBush;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityCrop;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityRock;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityTree;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {

    /*
     * EntityLoader
     */
    public static Entity loadEntity(TutEngine tutEngine, String id) {
        switch (id) {
            /*
             * Nature / Statics
             */
            case "tree_small":
                return new EntityTree(tutEngine, id, EntityTree.TreeType.SMALL);
            case "tree_medium":
                return new EntityTree(tutEngine, id, EntityTree.TreeType.MEDIUM);
            case "tree_large":
                return new EntityTree(tutEngine, id, EntityTree.TreeType.LARGE);
            case "tree_extra_large":
                return new EntityTree(tutEngine, id, EntityTree.TreeType.EXTRA_LARGE);
            case "rock_medium":
                return new EntityRock(tutEngine, id, Assets.ROCK_V1);
            case "rock_small":
                return new EntityRock(tutEngine, id, Assets.ROCK_V2);
            case "bush_small":
                return new EntityBush(tutEngine, id, Assets.BUSH_SMALL, Entity.DEFAULT_WIDTH);
            case "bush_large":
                return new EntityBush(tutEngine, id, Assets.BUSH_LARGE, Entity.DEFAULT_WIDTH * 2);

            /*
             * Crops
             */
            case "crop_carrot":
                return new EntityCrop(tutEngine, id, Assets.CROP_CARROT, ItemManager.CARROT);
            case "crop_wheat":
                return new EntityCrop(tutEngine, id, Assets.CROP_WHEAT, ItemManager.WHEAT);
            case "crop_potato":
                return new EntityCrop(tutEngine, id, Assets.CROP_POTATO, ItemManager.POTATO);
            case "crop_tomato":
                return new EntityCrop(tutEngine, id, Assets.CROP_TOMATO, ItemManager.TOMATO);
            case "crop_corn":
                return new EntityCrop(tutEngine, id, Assets.CROP_CORN, ItemManager.CORN);

            /*
             * Undead
             */
            case "zombie":
                return new EntityZombie(tutEngine, id);
            case "skeleton":
                return new EntitySkeleton(tutEngine, id);
            case "bouncer":
                return new EntityBouncer(tutEngine, id);
            case "thing":
                return new EntityThing(tutEngine, id);

            /*
             * Passive
             */
            case "pig":
                return new EntityPig(tutEngine, id);
            case "cow":
                return new EntityCow(tutEngine, id);
            case "sheep":
                return new EntitySheep(tutEngine, id);
            case "fox":
                return new EntityFox(tutEngine, id);

            /*
             * Other
             */
            case "ultimate":
                return new EntityUltimateTile(tutEngine, id);
            case "extra_life":
                return new EntityExtraLife(tutEngine, id);

            case "shop":
                return new EntityShopStall(tutEngine, id);
            case "campfire":
                return new EntityCampfire(tutEngine, id);
        }
        return new EntityRock(tutEngine, "rock", Assets.ROCK_V1);
    }

    /*
     * EntityManager
     */
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
            e.setShowHitbox(StateOptions.OPTIONS.debugMode());
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
