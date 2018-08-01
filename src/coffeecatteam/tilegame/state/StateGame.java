package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.creatures.Player;
import coffeecatteam.tilegame.worlds.World;

import java.awt.Graphics;

public class StateGame extends State {

    private Player player;
    private World world;

    public StateGame(Handler handler) {
        super(handler);
        world = new World(handler, "res/assets/worlds/world2.wd");
        handler.setWorld(world);
        player = new Player(handler, 100, 100);
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
