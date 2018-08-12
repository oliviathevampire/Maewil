package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.worlds.World;

import java.awt.*;

public class StateGame extends State {

    private World world;

    public StateGame(Handler handler) {
        super(handler);
        world = new World(handler, "/assets/worlds/test_world2");
        handler.setWorld(world);
    }

    @Override
    public void tick() {
        world.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
    }
}
