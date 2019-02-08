package coffeecatteam.theultimatetile.game.entities.creatures;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.inventory.InventoryPlayer;
import coffeecatteam.theultimatetile.inventory.Slot;
import coffeecatteam.theultimatetile.inventory.items.IInteractable;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.inventory.items.ItemTool;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.utils.Utils;
import org.newdawn.slick.*;

import java.util.List;

public class EntityPlayer extends EntityCreature {

    private Animation animDead;

    private Animation sprintEffect;

    private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;
    private float maxSprintTimer = 100f, sprintTimer = maxSprintTimer, sprintStartOver = maxSprintTimer, sprintDecent = 0.15f;
    private long lastWalkSoundTimer, walkSoundCooldown = 1100, walkSoundTimer = walkSoundCooldown;
    private boolean isAttacking = false;

    private InventoryPlayer inventoryPlayer;
    private ItemStack equippedItem;
    private int extraDmg = 0;
    private int glubel = 0, maxGludel = 100, lvl = 1;
    private String username;

    public boolean isDead, guiOpen = false;

    private float prevX, prevY;

    public EntityPlayer(TutEngine tutEngine, String username) {
        super(tutEngine, "player", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        this.username = username;
        isDead = false;

        inventoryPlayer = new InventoryPlayer(tutEngine, this);
    }

    @Override
    protected void init() {
        bounds.x = width / 4f;
        bounds.y = height - height / 2f;
        bounds.width = width / 2;
        bounds.height = height / 2;

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

        sprintEffect = new Animation(50, Assets.SPRINT_EFFECT);
    }

    @Override
    public void update(GameContainer container, int delta) {
        this.prevX = (float) position.x;
        this.prevY = (float) position.y;

        if (isActive()) {
            if (!guiOpen) {
                // Movement
                getInput(container, delta);
                move();

                // Interact
                tileInteract();
                tickEquippedItem();
            }

            // Attack
            checkAttacks();

            // Open/close inventory
            if (TutEngine.keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.E).getKeyCode())) {
                if (guiOpen && !inventoryPlayer.isActive())
                    ((TutEngine) TutEngine).getInventoryManager().closeAllInventories();
                else
                    ((TutEngine) TutEngine).getInventoryManager().openCloseInventory(inventoryPlayer);
            }
            if (TutEngine.keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && inventoryPlayer.isActive())
                ((TutEngine) TutEngine).getInventoryManager().closeAllInventories();

            ((TutEngine) TutEngine).getCamera().centerOnEntity(this);
        }

        inventoryPlayer.update(container, delta);

        // Animation
        currentAnim.update(container, delta);
        sprintEffect.update(container, delta);
    }

    @Override
    public void move() {
        super.move();

        Sound stepSound = null;
        int tileX = (int) (position.x + 0.5f) / Tile.TILE_WIDTH;
        int tileY = (int) (position.y + 0.5f) / Tile.TILE_HEIGHT;

        Tile bgTile = ((TutEngine) TutEngine).getWorld().getBGTile(tileX, tileY);
        Tile fgTile = ((TutEngine) TutEngine).getWorld().getFGTile(tileX, tileY);
        if (fgTile.getTileType() == Tile.TileType.AIR) {
            switch (bgTile.getTileType()) {
                default:
                case GROUND:
                    stepSound = Sounds.STEP_GROUND;
                    break;
                case STONE:
                    stepSound = Sounds.STEP_STONE;
                    break;
                case WOOD:
                    stepSound = Sounds.STEP_WOOD;
                    break;
                case FLUID:
                    stepSound = Sounds.SPLASH;
                    break;
            }
        }
        if (bgTile.getTileType() == Tile.TileType.AIR) {
            switch (fgTile.getTileType()) {
                default:
                case GROUND:
                    stepSound = Sounds.STEP_GROUND;
                    break;
                case STONE:
                    stepSound = Sounds.STEP_STONE;
                    break;
                case WOOD:
                    stepSound = Sounds.STEP_WOOD;
                    break;
                case FLUID:
                    stepSound = Sounds.SPLASH;
                    break;
            }
        }

        if (isMoving()) {
            walkSoundTimer += System.currentTimeMillis() - lastWalkSoundTimer;
            lastWalkSoundTimer = System.currentTimeMillis();
            if (walkSoundTimer < walkSoundCooldown)
                return;

            Sounds.play(stepSound, (float) position.x, (float) position.y, canSprint() ? 1.5f : 1f, StateOptions.OPTIONS.getVolumeOther());

            walkSoundTimer = 0;
        }
    }

    private void checkAttacks() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown)
            return;

        AABB cb = getCollisionBounds(0, 0);
        AABB ar = new AABB();
        int arSize = 20;
        ar.width = arSize;
        ar.height = arSize;

        if (TutEngine.getKeyManager().moveUp && TutEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width / 2f - arSize / 2f;
            ar.y = cb.y - arSize;
        } else if (TutEngine.getKeyManager().moveDown && TutEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width / 2f - arSize / 2f;
            ar.y = cb.y + cb.height;
        } else if (TutEngine.getKeyManager().moveLeft && TutEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x - arSize;
            ar.y = cb.y + cb.height / 2f - arSize / 2f;
        } else if (TutEngine.getKeyManager().moveRight && TutEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2f - arSize / 2f;
        } else {
            isAttacking = false;
            return;
        }

        attackTimer = 0;

        for (Entity e : ((TutEngine) TutEngine).getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }

            if (e.getCollisionBounds(0, 0).intersects(ar)) {
                e.isInteracted(extraDmg);
                Sound hitSound;
                switch (e.getEntityHitType()) {
                    default:
                    case CREATURE:
                        hitSound = Sounds.PUNCH;
                        break;
                    case WOOD:
                        hitSound = Sounds.STEP_WOOD;
                        break;
                    case STONE:
                        hitSound = Sounds.STEP_STONE;
                        break;
                    case BUSH:
                        hitSound = Sounds.BUSH;
                        break;
                }
                Sounds.play(hitSound, StateOptions.OPTIONS.getVolumePlayer(), (float) e.getPosition().x, (float) e.getPosition().y, 1f);
                return;
            }
        }
    }

    private void tickEquippedItem() {
        if (equippedItem != null) {
            if (inventoryPlayer.getSelectedHotbarItemStack() == equippedItem) {
                if (equippedItem.getItem() instanceof ItemTool)
                    extraDmg = ((ItemTool) equippedItem.getItem()).getDamage();
                else
                    extraDmg = 0;
                if (TutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.R).getKeyCode()))
                    if (equippedItem.getItem() instanceof IInteractable)
                        if (((IInteractable) equippedItem.getItem()).onInteracted(this))
                            equippedItem.setCount(equippedItem.getCount() - 1);
            }
        } else
            extraDmg = 0;
    }

    @Override
    public void die(List<Entity> entities, int index) {
        currentAnim = animDead;

        if (!isDead) {
            // Drop items in inventory
            for (Slot slot : this.inventoryPlayer.getSlots()) {
                // Check if the stack is bigger than 1
                if (slot.getStack() != null) {
                    if (slot.getStack().getCount() > 1)
                        for (int i = 0; i < slot.getStack().getCount(); i++)
                            dropItem(new ItemStack(slot.getStack().getItem(), 1), (float) position.x + NumberUtils.getRandomInt(-width, width * 2), (float) position.y + NumberUtils.getRandomInt(-height, height * 2));
                    else
                        dropItem(slot.getStack(), (float) position.x + NumberUtils.getRandomInt(-width, width * 2), (float) position.y + NumberUtils.getRandomInt(-height, height * 2));
                }
            }

            this.inventoryPlayer.clearInventory();
            isDead = true;
        }
    }

    private void dropItem(ItemStack stack, float x, float y) {
        stack.setPickedUp(false);
        ((TutEngine) TutEngine).getItemManager().addItem(stack, x, y);
    }

    private void getInput(GameContainer container, int delta) {
        xMove = 0;
        yMove = 0;

        if (!this.inWater() || !this.inLava()) {
            if (sprintTimer <= 0)
                sprintStartOver -= sprintDecent;
            if (sprintStartOver <= 0) {
                sprintTimer = maxSprintTimer;
                sprintStartOver = maxSprintTimer;
            }
            if (canSprint()) {
                speed = EntityCreature.DEFAULT_SPEED * 2f;
                sprintTimer -= sprintDecent;
            } else {
                speed = EntityCreature.DEFAULT_SPEED;
            }
            if (!TutEngine.getKeyManager().useSprint)
                sprintTimer = maxSprintTimer;
        }

        if (TutEngine.getKeyManager().moveUp) {
            yMove = -speed;
            currentAnim = animUp;
        }
        if (TutEngine.getKeyManager().moveDown) {
            yMove = speed;
            currentAnim = animDown;
        }
        if (TutEngine.getKeyManager().moveLeft) {
            xMove = -speed;
            currentAnim = animLeft;
        }
        if (TutEngine.getKeyManager().moveRight) {
            xMove = speed;
            currentAnim = animRight;
        }
        if (xMove == 0 && yMove == 0)
            currentAnim = animIdle;
    }

    private void tileInteract() {
        int x = (int) position.x / Tile.TILE_WIDTH;
        int y = (int) position.y / Tile.TILE_HEIGHT;
        Tile t = ((TutEngine) TutEngine).getWorld().getFGTile(x, y);
        if (isAttacking) {
            int dmg = NumberUtils.getRandomInt(1, 5);
            if (equippedItem != null) {
                if (equippedItem.getItem() instanceof ItemTool) {
                    ItemTool tool = (ItemTool) equippedItem.getItem();
                    if (tool.getToolType() == ItemTool.ToolType.PICKAXE)
                        dmg += extraDmg;
                }
            }
            t.damage(dmg);
        }
    }

    @Override
    public void render(Graphics g) {
        if (Utils.isDate(4, 1))
            currentAnim.getCurrentFrame().rotate((float) Math.toRadians(180d));
        currentAnim.getCurrentFrame().draw(this.renderX, this.renderY, width, height);

        this.renderEffect(g);
    }

    @Override
    public void renderEffect(Graphics g) {
        super.renderEffect(g);

        if (canSprint())
            sprintEffect.getCurrentFrame().draw(this.renderX, this.renderY, width, height);
        super.postRender(g);
    }

    @Override
    public void postRender(Graphics g) {
        Font font = Assets.FONTS.get("20");
        if (username != null) {
            int nameWidth = Text.getWidth(username, font);
            int nameHeight = Text.getHeight(username, font);
            int add = 8;
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);

            int xOff = nameWidth / 2 - width / 2;
            int yOff = height / 2;

            g.fillRect(this.renderX - xOff - add / 2, this.renderY - yOff - add / 2, nameWidth + add, nameHeight + add);
            Text.drawString(g, username, this.renderX - xOff + nameWidth / 2, this.renderY - yOff - nameHeight / 2 - add / 2, true, Color.white, font);
        }

        inventoryPlayer.render(g);
        inventoryPlayer.renderHotbar(g);
    }

    public InventoryPlayer getInventoryPlayer() {
        return inventoryPlayer;
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

    public float getMaxSprintTimer() {
        return maxSprintTimer;
    }

    public boolean canSprint() {
        return TutEngine.getKeyManager().useSprint && !inWater() && currentAnim != animIdle && sprintTimer > 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGlubel() {
        return glubel;
    }

    public void setGlubel(int glubel) {
        this.glubel = glubel;
        if (this.glubel >= maxGludel) {
            this.glubel = 0;
            lvl++;
        }
    }

    public int getMaxGludel() {
        return maxGludel;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public boolean isMoving() {
        return (this.xMove != 0 || this.yMove != 0) && (this.prevX != position.x || this.prevY != position.y);
    }

    public void reset() {
        inventoryPlayer.clearInventory();
        glubel = 0;
        lvl = 0;
        equippedItem = null;
        extraDmg = 0;
    }

    public boolean isGuiOpen() {
        return guiOpen;
    }

    public void setGuiOpen(boolean guiOpen) {
        this.guiOpen = guiOpen;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsDead(boolean dead) {
        isDead = dead;
    }
}
