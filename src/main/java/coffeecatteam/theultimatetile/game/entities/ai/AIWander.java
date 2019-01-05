package coffeecatteam.theultimatetile.game.entities.ai;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import org.newdawn.slick.GameContainer;

public class AIWander extends AI {

    private long lastWanderTimer, wanderCooldown = 800, wanderTimer = wanderCooldown;
    private float speed;
    private int dir;

    public AIWander(Engine engine, EntityCreature entity, float speed) {
        super(engine, entity);
        this.speed = speed;
        dir = 0;
    }

    @Override
    public boolean update(GameContainer container, int delta) {
        wanderTimer += System.currentTimeMillis() - lastWanderTimer;
        lastWanderTimer = System.currentTimeMillis();

        if (wanderTimer > wanderCooldown) {
            dir = NumberUtils.getRandomInt(8);
            if (StateOptions.OPTIONS.debugMode())
                engine.getLogger().print("Wander DIR for entity [" + entity.getId() + "]: " + dir);
            wanderCooldown = NumberUtils.getRandomBoolean() ? 1600 : 3200;
            wanderTimer = 0;
        }

        if (dir == 0) { // STOP
            entity.setxMove(0);
            entity.setyMove(0);
        }

        if (dir == 1) // RIGHT
            entity.setxMove(speed);
        if (dir == 2) // LEFT
            entity.setxMove(-speed);
        if (dir == 3) // DOWN
            entity.setyMove(speed);
        if (dir == 4) // UP
            entity.setyMove(-speed);

        if (dir == 5) { // RIGHT - DOWN
            entity.setxMove(speed);
            entity.setyMove(speed);
        }
        if (dir == 6) { // RIGHT - UP
            entity.setxMove(speed);
            entity.setyMove(-speed);
        }

        if (dir == 7) { // LEFT - DOWN
            entity.setxMove(-speed);
            entity.setyMove(speed);
        }
        if (dir == 8) { // LEFT - UP
            entity.setxMove(-speed);
            entity.setyMove(-speed);
        }

        return true;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
