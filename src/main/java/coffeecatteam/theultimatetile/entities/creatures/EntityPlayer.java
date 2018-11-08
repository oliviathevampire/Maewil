package coffeecatteam.theultimatetile.entities.creatures;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.audio.AudioMaster;
import coffeecatteam.theultimatetile.gfx.audio.Sound;
import coffeecatteam.theultimatetile.inventory.InventoryPlayer;
import coffeecatteam.theultimatetile.inventory.Slot;
import coffeecatteam.theultimatetile.inventory.items.IInteractable;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.inventory.items.ItemTool;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.tiles.IDamageableTile;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.util.List;

public class EntityPlayer extends EntityCreature {

    private Animation animDead;

    private Animation sprintEffect;

    private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;
    private float maxSprintTimer = 100f, sprintTimer = maxSprintTimer, sprintStartOver = maxSprintTimer;
    private long lastWalkSoundTimer, walkSoundCooldown = 1100, walkSoundTimer = walkSoundCooldown;
    private boolean isAttacking = false;

    private InventoryPlayer inventoryPlayer;
    private ItemStack equippedItem;
    private int extraDmg = 0;
    private int glubel = 0, maxGludel = 100, lvl = 1;
    private String username;

    public boolean isDead, guiOpen = false;

    private float prevX, prevY;

    public EntityPlayer(TheUltimateTile theUltimateTile, String username) {
        super(theUltimateTile, "player", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        this.username = username;
        isDead = false;

        inventoryPlayer = new InventoryPlayer(theUltimateTile, this);
    }

    @Override
    protected void init() {
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

        sprintEffect = new Animation(50, Assets.SPRINT_EFFECT);
    }

    @Override
    public void tick() {
        this.prevX = this.x;
        this.prevY = this.y;

        if (isActive()) {
            if (!guiOpen) {
                // Movement
                getInput();
                move();

                // Interact
                tileInteract();
                tickEquippedItem();
            }

            // Attack
            checkAttacks();

            // Open/close inventory
            if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.E).getKeyCode())) {
                if (guiOpen && !inventoryPlayer.isActive())
                    theUltimateTile.getInventoryManager().closeAllInventories();
                else
                    theUltimateTile.getInventoryManager().openCloseInventory(inventoryPlayer);
            }
            if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && inventoryPlayer.isActive())
                theUltimateTile.getInventoryManager().closeAllInventories();

            theUltimateTile.getCamera().centerOnEntity(this);
            AudioMaster.setListenerData(this.x, this.y, 0f);
        }

        inventoryPlayer.tick();

        // Animation
        currentAnim.tick();
        sprintEffect.tick();
    }

    @Override
    public void move() {
        super.move();

        int stepSound = -1;
        int tileX = (int) (x + 0.5f) / Tile.TILE_WIDTH;
        int tileY = (int) (y + 0.5f) / Tile.TILE_HEIGHT;

        if (theUltimateTile.getWorld().getFGTile(tileX, tileY).getTileType() == Tile.TileType.AIR) {
            switch (theUltimateTile.getWorld().getBGTile(tileX, tileY).getTileType()) {
                case GROUND:
                    stepSound = Sound.STEP_GROUND;
                    break;
                case STONE:
                    stepSound = Sound.STEP_STONE;
                    break;
                case WOOD:
                    stepSound = Sound.STEP_WOOD;
                    break;
            }
        } else if (theUltimateTile.getWorld().getFGTile(tileX, tileY).getTileType() == Tile.TileType.FLUID) {
            stepSound = Sound.SPLASH;
        }

        if (isMoving()) {
            walkSoundTimer += System.currentTimeMillis() - lastWalkSoundTimer;
            lastWalkSoundTimer = System.currentTimeMillis();
            if (walkSoundTimer < walkSoundCooldown)
                return;

            if (stepSound != -1)
                Sound.play(stepSound, StateOptions.OPTIONS.getVolumeOther(), this.x, this.y, 0f, (canSprint() ? 1.5f : 1f));

            walkSoundTimer = 0;
        }
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

        if (theUltimateTile.getKeyManager().moveUp && theUltimateTile.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y - arSize;
        } else if (theUltimateTile.getKeyManager().moveDown && theUltimateTile.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width / 2 - arSize / 2;
            ar.y = cb.y + cb.height;
        } else if (theUltimateTile.getKeyManager().moveLeft && theUltimateTile.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x - arSize;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        } else if (theUltimateTile.getKeyManager().moveRight && theUltimateTile.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2 - arSize / 2;
        } else {
            isAttacking = false;
            return;
        }

        attackTimer = 0;

        for (Entity e : theUltimateTile.getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }

            if (e.getCollisionBounds(0, 0).intersects(ar)) {
                e.isInteracted(extraDmg);
                int hitSound = -1;
                switch (e.getEntityHitType()) {
                    case CREATURE:
                        hitSound = (Utils.getRandomInt(10) > 5 ? Sound.PUNCH_LEFT : Sound.PUNCH_RIGHT);
                        break;
                    case WOOD:
                        hitSound = Sound.STEP_WOOD;
                        break;
                    case STONE:
                        hitSound = Sound.STEP_STONE;
                        break;
                    case BUSH:
                        hitSound = (Utils.getRandomInt(10) > 5 ? Sound.BUSH_LEFT : Sound.BUSH_RIGHT);
                        break;
                }
                if (hitSound != -1)
                    Sound.play(hitSound, StateOptions.OPTIONS.getVolumePlayer(), e.getX(), e.getY(), 1f);
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
                if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.R).getKeyCode()))
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
                            dropItem(new ItemStack(slot.getStack().getItem(), 1), x + Utils.getRandomInt(-width, width * 2), y + Utils.getRandomInt(-height, height * 2));
                    else
                        dropItem(slot.getStack(), x + Utils.getRandomInt(-width, width * 2), y + Utils.getRandomInt(-height, height * 2));
                }
            }

            this.inventoryPlayer.clearInventory();
            isDead = true;
        }
    }

    private void dropItem(ItemStack stack, float x, float y) {
        stack.setPickedUp(false);
        theUltimateTile.getItemManager().addItem(stack, x, y);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (!inWater()) {
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
            if (!theUltimateTile.getKeyManager().useSprint)
                sprintTimer = maxSprintTimer;
        }

        if (theUltimateTile.getKeyManager().moveUp) {
            yMove = -speed;
            currentAnim = animUp;
        }
        if (theUltimateTile.getKeyManager().moveDown) {
            yMove = speed;
            currentAnim = animDown;
        }
        if (theUltimateTile.getKeyManager().moveLeft) {
            xMove = -speed;
            currentAnim = animLeft;
        }
        if (theUltimateTile.getKeyManager().moveRight) {
            xMove = speed;
            currentAnim = animRight;
        }
        if (xMove == 0 && yMove == 0)
            currentAnim = animIdle;
    }

    private void tileInteract() {
        int x = (int) this.x / Tile.TILE_WIDTH;
        int y = (int) this.y / Tile.TILE_HEIGHT;
        Tile t = theUltimateTile.getWorld().getFGTile(x, y);
        if (t instanceof IDamageableTile) {
            if (isAttacking) {
                int dmg = Utils.getRandomInt(1, 5);
                if (equippedItem != null) {
                    if (equippedItem.getItem() instanceof ItemTool) {
                        ItemTool tool = (ItemTool) equippedItem.getItem();
                        if (tool.getToolType() == ItemTool.ToolType.PICKAXE)
                            dmg += extraDmg;
                    }
                }
                ((IDamageableTile) t).damage(dmg);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnim.getCurrentFrame(), this.renderX, this.renderY, width, height, null);

        this.renderEffect(g);
    }

    @Override
    public void renderEffect(Graphics g) {
        super.renderEffect(g);

        if (canSprint())
            g.drawImage(sprintEffect.getCurrentFrame(), this.renderX, this.renderY, width, height, null);
    }

    @Override
    public void postRender(Graphics g) {
        Font font = Assets.FONT_20;
        if (username != null) {
            int nameWidth = Text.getWidth(g, username, font);
            int nameHeight = Text.getHeight(g, font);
            int add = 6;
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);

            int xOff = nameWidth / 2 - width / 2;
            int yOff = height / 2;

            g.fillRect(this.renderX - xOff - add / 2, this.renderY - yOff - add / 2, nameWidth + add, nameHeight + add);
            Text.drawString(g, username, this.renderX - xOff, this.renderY - yOff + nameHeight - add / 2, false, false, Color.white, font);
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
        return theUltimateTile.getKeyManager().useSprint && !inWater() && currentAnim != animIdle && sprintTimer > 0;
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
        return (this.xMove != 0 || this.yMove != 0) && (this.prevX != this.x || this.prevY != this.y);
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
}
