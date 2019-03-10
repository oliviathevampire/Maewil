package coffeecatteam.theultimatetile.objs.entities.creatures;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.inventory.InventoryPlayer;
import coffeecatteam.theultimatetile.inventory.Slot;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.items.IInteractable;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.items.ItemTool;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.utils.Utils;
import org.newdawn.slick.*;

import java.util.List;

public class EntityPlayer extends EntityCreature {

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
        sprintEffect = new Animation(50, Assets.SPRINT_EFFECT);
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(width / 4f, height - height / 2f, width / 2, height / 2);
    }

    @Override
    public AABB getEntityBounds() {
        return new AABB((float) position.x, (float) (position.y + height / 2f), width, height / 2);
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
                updateEquippedItem();
            }

            // Attack
            checkAttacks();

            // Open/close inventory
            if (tutEngine.keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.E).getKeyCode())) {
                if (guiOpen && !inventoryPlayer.isActive())
                    tutEngine.getInventoryManager().closeAllInventories();
                else
                    tutEngine.getInventoryManager().openCloseInventory(inventoryPlayer);
            }
            if (tutEngine.keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && inventoryPlayer.isActive())
                tutEngine.getInventoryManager().closeAllInventories();
        }

        inventoryPlayer.update(container, delta);

        // Animation
        sprintEffect.update();
    }

    @Override
    public void move() {
        super.move();

        Sound stepSound = null;
        int tileX = (int) (position.x + 0.5f) / Tile.TILE_WIDTH;
        int tileY = (int) (position.y + 0.5f) / Tile.TILE_HEIGHT;

        Tile bgTile = tutEngine.getWorld().getBGTile(tileX, tileY);
        Tile fgTile = tutEngine.getWorld().getFGTile(tileX, tileY);
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

        if (tutEngine.getKeyManager().moveUp && tutEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width / 2f - arSize / 2f;
            ar.y = cb.y - arSize;
        } else if (tutEngine.getKeyManager().moveDown && tutEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width / 2f - arSize / 2f;
            ar.y = cb.y + cb.height;
        } else if (tutEngine.getKeyManager().moveLeft && tutEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x - arSize;
            ar.y = cb.y + cb.height / 2f - arSize / 2f;
        } else if (tutEngine.getKeyManager().moveRight && tutEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2f - arSize / 2f;
        } else {
            isAttacking = false;
            return;
        }

        attackTimer = 0;

        for (Entity e : tutEngine.getEntityManager().getEntities()) {
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

    private void updateEquippedItem() {
        if (equippedItem != null) {
            if (inventoryPlayer.getSelectedHotbarItemStack() == equippedItem) {
                if (equippedItem.getItem() instanceof ItemTool)
                    extraDmg = ((ItemTool) equippedItem.getItem()).getDamage();
                else
                    extraDmg = 0;
                if (tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.R).getKeyCode()))
                    if (equippedItem.getItem() instanceof IInteractable)
                        if (((IInteractable) equippedItem.getItem()).onInteracted(this))
                            equippedItem.setCount(equippedItem.getCount() - 1);
            }
        } else
            extraDmg = 0;
    }

    @Override
    public void die(List<Entity> entities, int index) {
        setCurrentTexture("dead");

        if (!isDead) {
            // Drop ITEMS in inventory
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
        tutEngine.getEntityManager().addItem(stack.copy(), (x) / Tile.TILE_WIDTH, (y) / Tile.TILE_HEIGHT);
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
            if (!tutEngine.getKeyManager().useSprint)
                sprintTimer = maxSprintTimer;
        }

        if (tutEngine.getKeyManager().moveUp) {
            yMove = -speed;
//            setCurrentTexture("walkUp");
        }
        if (tutEngine.getKeyManager().moveDown) {
            yMove = speed;
//            setCurrentTexture("walkDown");
        }
        if (tutEngine.getKeyManager().moveLeft) {
            xMove = -speed;
//            setCurrentTexture("walkLeft");
        }
        if (tutEngine.getKeyManager().moveRight) {
            xMove = speed;
//            setCurrentTexture("walkRight");
        }
//        if (xMove == 0 && yMove == 0)
//            setCurrentTexture("idle");
    }

    private void tileInteract() {
        int x = (int) position.x / Tile.TILE_WIDTH;
        int y = (int) position.y / Tile.TILE_HEIGHT;
        Tile t = tutEngine.getWorld().getFGTile(x, y);
        if (isAttacking) {
            int dmg = NumberUtils.getRandomInt(1, 3);
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
            getCurrentTexture().getCurrentFrame().rotate((float) Math.toRadians(180d));
        getCurrentTexture().getCurrentFrame().draw(this.renderX, this.renderY, width, height);

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
        return tutEngine.getKeyManager().useSprint && !inWater() && getCurrentTexture() != getTextures().get("idle") && sprintTimer > 0;
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
        currentHealth = maxHealth;
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

    @Override
    public EntityPlayer newCopy() {
        return super.newCopy(new EntityPlayer(tutEngine, username));
    }
}
