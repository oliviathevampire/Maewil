package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityBouncer;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntitySkeleton;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityThing;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityZombie;
import coffeecatteam.theultimatetile.entities.player.EntityPlayer;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.entities.statics.*;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityBush;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityCrop;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityRock;
import coffeecatteam.theultimatetile.entities.statics.nature.EntityTree;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Items;
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

            case "carrot_crop":
                return new EntityCrop(theUltimateTile, id, Assets.CARROT_CROP, Items.CARROT);

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
             * Other
             */
            case "ultimate":
                return new EntityUltimateTile(theUltimateTile, id);

            case "shop":
                return new EntityShopStall(theUltimateTile, id);
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
            e.tick();
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
        for (Entity e : entities) {
            e.renderA(g);
        }

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

    public void removePlayerMP(String username) {
        int index = 0;
        for (Entity e : entities) {
            if (e instanceof EntityPlayerMP)
                if (((EntityPlayerMP) e).getUsername().equals(username))
                    break;
            index++;
        }
        entities.remove(index);
    }

    private int getPlayerMPIndex(String username) {
        int index = 0;
        for (Entity e : entities) {
            if (e instanceof EntityPlayerMP)
                if (((EntityPlayerMP) e).getUsername().equals(username))
                    break;
            index++;
        }
        return index;
    }

    public void movePlayer(String username, float x, float y) {
        int index = getPlayerMPIndex(username);
        entities.get(index).setX(x);
        entities.get(index).setY(y);

//        EntityPlayerMP player = (EntityPlayerMP) entities.get(index);
//        player.setxMove(x);
//        player.setyMove(y);
        //player.move();
    }
}
