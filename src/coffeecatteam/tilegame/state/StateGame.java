package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.worlds.World;

import java.awt.*;

public class StateGame extends State {

    private World world;

    public StateGame(Handler handler) {
        super(handler);
        world = new World(handler, "res/assets/worlds/world2.wd");
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
