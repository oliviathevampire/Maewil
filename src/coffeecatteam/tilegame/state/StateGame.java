package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Game;
import coffeecatteam.tilegame.entities.creatures.Player;

import java.awt.Graphics;

public class StateGame extends State {

    private Player player;

    public StateGame(Game game) {
        super(game);
        player = new Player(game, 100, 100);
    }

    @Override
    public void tick() {
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        player.render(g);
    }
}
