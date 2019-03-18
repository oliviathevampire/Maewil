package coffeecatteam.theultimatetile.objs.entities.ai;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityCreature;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.TilePos;
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
                tutEngine.getLogger().warn("Wander DIR for entity [" + entity.getId() + "] is [" + direction + "]");
            wanderCooldown = NumberUtils.getRandomBoolean() ? 1600 : 3200;
            wanderTimer = 0;
        }

        if (nextTileIsSolid()) {
            switch (NumberUtils.getRandomInt(0, 2)) {
                case 0:
                    direction = MoveDirection.opposet(direction);
                    break;
                case 1:
                    direction = MoveDirection.left(direction);
                    break;
                case 2:
                    direction = MoveDirection.right(direction);
                    break;
            }
            if (StateOptions.OPTIONS.debugMode())
                tutEngine.getLogger().warn("Wander DIR for entity [" + entity.getId() + "] was swapped to [" + direction + "]");
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

    private boolean nextTileIsSolid() {
        TilePos currentTile = entity.getTilePosAtMid();
        switch (direction) {
            case STOP:
                break;

            case RIGHT:
                currentTile = currentTile.right();
                break;
            case LEFT:
                currentTile = currentTile.left();
                break;
            case DOWN:
                currentTile = currentTile.down();
                break;
            case UP:
                currentTile = currentTile.up();
                break;

            case RIGHT_DOWN:
                currentTile = currentTile.downRight();
                break;
            case RIGHT_UP:
                currentTile = currentTile.upRight();
                break;
            case LEFT_DOWN:
                currentTile = currentTile.downLeft();
                break;
            case LEFT_UP:
                currentTile = currentTile.upLeft();
                break;
        }
        return tutEngine.getWorld().getFGTile(currentTile).isSolid();
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    enum MoveDirection {
        STOP, RIGHT, LEFT, DOWN, UP, RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN;

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

                case RIGHT_UP:
                    return LEFT_DOWN;
                case RIGHT_DOWN:
                    return LEFT_UP;
                case LEFT_UP:
                    return RIGHT_DOWN;
                case LEFT_DOWN:
                    return RIGHT_UP;
            }
        }

        public static MoveDirection right(MoveDirection direction) {
            switch (direction) {
                case STOP:
                default:
                    return STOP;

                case RIGHT_UP:
                    return RIGHT;
                case RIGHT:
                    return RIGHT_DOWN;
                case RIGHT_DOWN:
                    return DOWN;
                case DOWN:
                    return LEFT_DOWN;
                case LEFT_DOWN:
                    return LEFT;
                case LEFT:
                    return LEFT_UP;
                case LEFT_UP:
                    return UP;
                case UP:
                    return RIGHT_UP;
            }
        }

        public static MoveDirection left(MoveDirection direction) {
            switch (direction) {
                case STOP:
                default:
                    return STOP;

                case RIGHT_UP:
                    return UP;
                case RIGHT:
                    return RIGHT_UP;
                case RIGHT_DOWN:
                    return RIGHT;
                case DOWN:
                    return RIGHT_DOWN;
                case LEFT_DOWN:
                    return DOWN;
                case LEFT:
                    return LEFT_DOWN;
                case LEFT_UP:
                    return LEFT;
                case UP:
                    return LEFT_UP;
            }
        }

        public static MoveDirection random() {
            switch (NumberUtils.getRandomInt(values().length - 1)) {
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
            }
        }
    }
}
