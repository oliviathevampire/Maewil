package coffeecatteam.theultimatetile.objs.entities.ai;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.state.StateOptions;
import org.newdawn.slick.GameContainer;

public class AIWander extends AI {

    private long lastWanderTimer, wanderCooldown = 800, wanderTimer = wanderCooldown;
    private float speed;
    private MoveDirection direction;

    public AIWander(TutEngine tutEngine, EntityCreature entity, float speed) {
        super(tutEngine, entity);
        this.speed = speed;
        direction = MoveDirection.STOP;
    }

    @Override
    public boolean update(GameContainer container, int delta) {
        wanderTimer += System.currentTimeMillis() - lastWanderTimer;
        lastWanderTimer = System.currentTimeMillis();

        if (wanderTimer > wanderCooldown) {
            direction = MoveDirection.random();
            if (StateOptions.OPTIONS.debugMode())
                tutEngine.getLogger().warn("Wander DIR for entity [" + entity.getId() + "]: " + direction);
            wanderCooldown = NumberUtils.getRandomBoolean() ? 1600 : 3200;
            wanderTimer = 0;
        }


        switch (direction) {
            case STOP:
                entity.setxMove(0);
                entity.setyMove(0);
                break;

            case RIGHT:
                entity.setxMove(speed);
                break;
            case LEFT:
                entity.setxMove(-speed);
                break;
            case DOWN:
                entity.setyMove(speed);
                break;
            case UP:
                entity.setyMove(-speed);
                break;

            case RIGHT_DOWN:
                entity.setxMove(speed);
                entity.setyMove(speed);
                break;
            case RIGHT_UP:
                entity.setxMove(speed);
                entity.setyMove(-speed);
                break;
            case LEFT_DOWN:
                entity.setxMove(-speed);
                entity.setyMove(speed);
                break;
            case LEFT_UP:
                entity.setxMove(-speed);
                entity.setyMove(-speed);
                break;
        }

        return true;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    enum MoveDirection {
        STOP, RIGHT, LEFT, DOWN, UP, RIGHT_DOWN, RIGHT_UP, LEFT_DOWN, LEFT_UP;

        public static MoveDirection opposet(MoveDirection direction) {
            switch (direction) {
                case STOP:
                default:
                    return STOP;

                case RIGHT:
                    return LEFT;
                case LEFT:
                    return RIGHT;
                case DOWN:
                    return UP;
                case UP:
                    return DOWN;

                case RIGHT_DOWN:
                    return LEFT_UP;
                case RIGHT_UP:
                    return LEFT_DOWN;
                case LEFT_DOWN:
                    return RIGHT_UP;
                case LEFT_UP:
                    return RIGHT_DOWN;
            }
        }

        public static MoveDirection random() {
            switch (NumberUtils.getRandomInt(8)) {
                case 0:
                default:
                    return STOP;

                case 1:
                    return LEFT;
                case 2:
                    return RIGHT;
                case 3:
                    return UP;
                case 4:
                    return DOWN;

                case 5:
                    return RIGHT_DOWN;
                case 6:
                    return RIGHT_UP;
                case 7:
                    return LEFT_DOWN;
                case 8:
                    return LEFT_UP;
            }
        }
    }
}
