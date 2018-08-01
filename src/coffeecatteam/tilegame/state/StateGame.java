package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.creatures.Player;
import coffeecatteam.tilegame.tiles.Tile;
import coffeecatteam.tilegame.worlds.World;

import java.awt.Graphics;

public class StateGame extends State {

    private Player player;
    private World world;

    public StateGame(Handler handler) {
        super(handler);
        world = new World(handler, "res/assets/worlds/world2.wd");
        handler.setWorld(world);
        player = new Player(handler, world.getSpawnX() * Tile.TILE_WIDTH, world.getSpawnY() * Tile.TILE_HEIGHT);
    }

    @Override
    public void tick() {
        world.tick();
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
    }
}
