package coffeecatteam.theultimatetile.entities.ai;

import coffeecatteam.theultimatetile.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.utils.Logger;

import java.util.Random;

public class AIWander extends AI {

    private long lastWanderTimer, wanderCooldown = 800, wanderTimer = wanderCooldown;
    private float speed;
    private int dir;

    public AIWander(EntityCreature entity, float speed) {
        super(entity);
        this.speed = speed;
        dir = 0;
    }

    @Override
    public boolean tick() {
        wanderTimer += System.currentTimeMillis() - lastWanderTimer;
        lastWanderTimer = System.currentTimeMillis();

        if (wanderTimer > wanderCooldown) {
            dir = new Random().nextInt(9);
            if (StateOptions.DEBUG)
                Logger.print(dir);
            wanderCooldown = new Random().nextInt(1) == 0 ? 1600 : 3200;
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
