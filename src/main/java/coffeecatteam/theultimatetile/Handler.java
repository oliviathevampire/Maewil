package coffeecatteam.theultimatetile;

import coffeecatteam.theultimatetile.entities.EntityManager;
import coffeecatteam.theultimatetile.gfx.Camera;
import coffeecatteam.theultimatetile.input.KeyManager;
import coffeecatteam.theultimatetile.input.MouseManager;
import coffeecatteam.theultimatetile.worlds.World;

public class Handler {

    private Game game;
    private World world;

    public Handler(Game game) {
        this.game = game;
    }

    public Camera getCamera() {
        return game.getCamera();
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public EntityManager getEntityManager() { return game.getEntityManager(); }

    public int getWidth() {
        return game.getWidth();
    }

    public int getHeight() {
        return game.getHeight();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
