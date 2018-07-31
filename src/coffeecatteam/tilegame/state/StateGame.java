package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Game;
import coffeecatteam.tilegame.entities.creatures.Player;
import coffeecatteam.tilegame.worlds.World;

import java.awt.Graphics;

public class StateGame extends State {

    private Player player;
    private World world;

    public StateGame(Game game) {
        super(game);
        player = new Player(game, 100, 100);
        world = new World("res/assets/worlds/world1.wd");
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
