package coffeecatteam.theultimatetile.entities.creatures;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.Inventory;
import coffeecatteam.theultimatetile.items.IInteractable;
import coffeecatteam.theultimatetile.items.ItemStack;
import coffeecatteam.theultimatetile.items.ItemTool;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.tiles.Tiles;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class EntityPlayer extends EntityCreature {

    private Animation animIdle, animUp, animDown, animLeft, animRight, animDead;
    private Animation currentAnim;

    private Animation sprintEffect;
    private Animation splashEffect;

    private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;

    private Inventory inventory;
    private ItemStack equippedItem;
    private int extraDmg = 0;

    private float maxSprintTimer = 100f, sprintTimer = maxSprintTimer, sprintStartOver = maxSprintTimer;

    public EntityPlayer(Handler handler, String id) {
        super(handler, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);

        bounds.x = 13;
        bounds.y = 34;
        bounds.width = 34;
        bounds.height = 28;

        // Animations - 500 = 0.5 second
        int speed = 135;
        int upDownSpeed = speed + 115;
        animIdle = new Animation(speed, Assets.PLAYER_IDLE);
        animUp = new Animation(upDownSpeed, Assets.PLAYER_UP);
        animDown = new Animation(upDownSpeed, Assets.PLAYER_DOWN);
        animLeft = new Animation(speed, Assets.PLAYER_LEFT);
        animRight = new Animation(speed, Assets.PLAYER_RIGHT);
        animDead = new Animation(speed, Assets.PLAYER_DEAD);

        currentAnim = animIdle;

        int effectSpeed = 50;
        sprintEffect = new Animation(effectSpeed, Assets.SPRINT_EFFECT);
        splashEffect = new Animation(effectSpeed, Assets.SPLASH_EFFECT);

        inventory = new Inventory(handler, this);
    }

    @Override
    public void tick() {
        if (isActive()) {
            if (!inventory.isActive()) {
                // Movement
                getInput();
                move();

                // Attack
                checkAttacks();

                // Interact
                tileInteract();
                tickEquippedItem();

                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) {
                    handler.getGame().gameState.setWorld("/assets/worlds/starter/final");
                    inventory.resetAll();
                }
            }

            handler.getCamera().centerOnEntity(this);
            inventory.tick();
        }

        // Animation
        currentAnim.tick();
        sprintEffect.tick();
        splashEffect.tick();
    }

    private void checkAttacks() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown)
            return;

        Rectangle cb = getCollisionBounds(0, 0);
        Rectangle ar = new Rectangle();
        int arSize = 20;
        ar.width = arSize;
        ar.height = arSize;

        if (handler.getKeyManager().up && handler.getKeyManager().attack) {
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y - arSize;
        } else if (handler.getKeyManager().down && handler.getKeyManager().attack) {
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y + cb.height;
        } else if (handler.getKeyManager().left && handler.getKeyManager().attack) {
            ar.x = cb.x - arSize;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        } else if (handler.getKeyManager().right && handler.getKeyManager().attack) {
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        } else {
            return;
        }

        attackTimer = 0;

        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0, 0).intersects(ar)) {
                e.hurt(Utils.getRandomInt(5, 10) + extraDmg);
                return;
            }
        }
    }

    private void tickEquippedItem() {
        if (equippedItem != null) {
            if (inventory.getSelectedHotbarItemStack() == equippedItem) {
                if (equippedItem.getItem() instanceof ItemTool)
                    extraDmg = ((ItemTool) equippedItem.getItem()).getDamage();
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_R))
                    if (equippedItem.getItem() instanceof IInteractable)
                        if (((IInteractable) equippedItem.getItem()).onInteracted(this))
                            equippedItem.setCount(equippedItem.getCount() - 1);
            }
        }
    }

    @Override
    public void die(Iterator<Entity> it) {
        currentAnim = animDead;
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (inWater())
            speed = EntityCreature.DEFAULT_SPEED * 0.65f;
        else {
            if (sprintTimer <= 0)
                sprintStartOver -= 0.25f;
            if (sprintStartOver <= 0) {
                sprintTimer = maxSprintTimer;
                sprintStartOver = maxSprintTimer;
            }
            if (canSprint()) {
                speed = EntityCreature.DEFAULT_SPEED * 2f;
                sprintTimer -= 0.25f;
            } else {
                speed = EntityCreature.DEFAULT_SPEED;
            }
            if (!handler.getKeyManager().sprint)
                sprintTimer = maxSprintTimer;
        }

        if (handler.getKeyManager().up) {
            yMove = -speed;
            currentAnim = animUp;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
            currentAnim = animDown;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
            currentAnim = animLeft;
        }
        if (handler.getKeyManager().right) {
            xMove = speed;
            currentAnim = animRight;
        }
        if (xMove == 0 && yMove == 0)
            currentAnim = animIdle;
    }

    private boolean inWater() {
        int x = (int) this.x / Tile.TILE_WIDTH;
        int y = (int) this.y / Tile.TILE_HEIGHT;
        Tile t = handler.getWorld().getTile(x, y);
        if (t.getId() == Tiles.WATER.getId()) {
            float nx = x + Tile.TILE_WIDTH / 2;
            float ny = y + Tile.TILE_HEIGHT / 2;
            if (t.getBounds().contains(nx, ny))
                return true;
        }

        return false;
    }

    private void tileInteract() {
        int x = (int) this.x / Tile.TILE_WIDTH;
        int y = (int) this.y / Tile.TILE_HEIGHT;
        Tile t = handler.getWorld().getTile(x, y);
//        if (t.getId() == Tiles.DIRT.getId()) {
//            handler.getWorld().setTile(x, y, Tiles.AIR);
//        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnim.getCurrentFrame(), (int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()), width, height, null);
        if (canSprint())
            g.drawImage(sprintEffect.getCurrentFrame(), (int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()), width, height, null);

        if (inWater())
            g.drawImage(splashEffect.getCurrentFrame(), (int) (x - handler.getCamera().getxOffset()), (int) (y - handler.getCamera().getyOffset()), width, height, null);
    }

    public void postRender(Graphics g) {
        inventory.render(g);
        inventory.renderHotbar(g);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ItemStack getEquippedItem() {
        return equippedItem;
    }

    public void setEquippedItem(ItemStack equippedItem) {
        this.equippedItem = equippedItem;
    }

    public float getSprintTimer() {
        return sprintTimer;
    }

    public boolean canSprint() {
        return handler.getKeyManager().sprint && !inWater() && currentAnim != animIdle && sprintTimer > 0;
    }
}
