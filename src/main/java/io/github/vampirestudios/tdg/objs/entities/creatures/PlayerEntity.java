package io.github.vampirestudios.tdg.objs.entities.creatures;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import io.github.vampirestudios.tdg.gfx.Animation;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.assets.Sounds;
import io.github.vampirestudios.tdg.inventory.InventoryPlayer;
import io.github.vampirestudios.tdg.inventory.Slot;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.items.IInteractable;
import io.github.vampirestudios.tdg.objs.items.ItemStack;
import io.github.vampirestudios.tdg.objs.items.tool.ToolItem;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.objs.tiles.TilePos;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import io.github.vampirestudios.tdg.screen.options.controls.Keybind;
import io.github.vampirestudios.tdg.utils.UtilsIdk;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerEntity extends LivingEntity {

    private Animation sprintEffect;

    private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;
    private long lastWalkSoundTimer, walkSoundCooldown = 1100, walkSoundTimer = walkSoundCooldown;
    private boolean isAttacking = false;

    private InventoryPlayer inventoryPlayer;
    private ItemStack equippedItem;
    private int extraDmg = 0;
    private String username;

    public boolean isDead, guiOpen = false;

    private float prevX, prevY;

    public PlayerEntity(MaewilEngine maewilEngine, String username) {
        super(maewilEngine, "player", 34, 62);
        this.username = username;
        isDead = false;

        inventoryPlayer = new InventoryPlayer(maewilEngine, this);
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
    public void update(GameContainer container, StateBasedGame game, int delta) {
        this.prevX = (float) position.x;
        this.prevY = (float) position.y;

        if (isActive()) {
            if (!guiOpen) {
                // Movement
                getInput();
                move();

                // Interact
                tileInteract();
                updateEquippedItem();
            }

            // Attack
            checkAttacks();

            // Open/close inventory
            if (maewilEngine.keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.E).getKeyCode())) {
                if (guiOpen && !inventoryPlayer.isActive())
                    maewilEngine.getInventoryManager().closeAllInventories();
                else
                    maewilEngine.getInventoryManager().openCloseInventory(inventoryPlayer);
            }
            if (maewilEngine.keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && inventoryPlayer.isActive())
                maewilEngine.getInventoryManager().closeAllInventories();
        }

        inventoryPlayer.update(container, game, delta);
    }

    @Override
    public void move() {
        super.move();

        Sound stepSound = null;
        int tileX = (int) (position.x + 0.5f) / Tile.TILE_SIZE;
        int tileY = (int) (position.y + 0.5f) / Tile.TILE_SIZE;

        Tile bgTile = maewilEngine.getWorld().getBackgroundTile(tileX, tileY);
        Tile fgTile = maewilEngine.getWorld().getForegroundTile(tileX, tileY);
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

            Sounds.play(stepSound, (float) position.x, (float) position.y, canSprint() ? 1.5f : 1f, OptionsScreen.OPTIONS.getVolumeOther());

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

        if (maewilEngine.getKeyManager().moveUp && maewilEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width / 2f - arSize / 2f;
            ar.y = cb.y - arSize;
        } else if (maewilEngine.getKeyManager().moveDown && maewilEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width / 2f - arSize / 2f;
            ar.y = cb.y + cb.height;
        } else if (maewilEngine.getKeyManager().moveLeft && maewilEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x - arSize;
            ar.y = cb.y + cb.height / 2f - arSize / 2f;
        } else if (maewilEngine.getKeyManager().moveRight && maewilEngine.getKeyManager().useAttack) {
            isAttacking = true;
            ar.x = cb.x + cb.width;
            ar.y = cb.y + cb.height / 2f - arSize / 2f;
        } else {
            isAttacking = false;
            return;
        }

        attackTimer = 0;

        for (Entity e : maewilEngine.getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }

            if (e.getCollisionBounds(0, 0).intersects(ar)) {
                e.isInteracted(extraDmg);
                Sound hitSound = switch (e.getHitType()) {
                    case CREATURE -> Sounds.PUNCH;
                    case WOOD -> Sounds.STEP_WOOD;
                    case STONE -> Sounds.STEP_STONE;
                    case BUSH -> Sounds.BUSH;
                    case NONE -> null;
                };
                Sounds.play(hitSound, OptionsScreen.OPTIONS.getVolumePlayer(), (float) e.getPosition().x, (float) e.getPosition().y, 1f);
                return;
            }
        }
    }

    private void updateEquippedItem() {
        if (equippedItem != null) {
            if (inventoryPlayer.getSelectedHotbarItemStack() == equippedItem) {
                if (equippedItem.getItem() instanceof ToolItem)
                    extraDmg = ((ToolItem) equippedItem.getItem()).getDamage();
                else
                    extraDmg = 0;
                if (maewilEngine.getKeyManager().keyJustPressed(OptionsScreen.OPTIONS.controls().get(Keybind.R).getKeyCode()))
                    if (equippedItem.getItem() instanceof IInteractable)
                        if (((IInteractable) equippedItem.getItem()).onInteracted(this))
                            equippedItem.setCount(equippedItem.getCount() - 1);
            }
        } else
            extraDmg = 0;
    }

    @Override
    public void die() {
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
        maewilEngine.getEntityManager().addItem(stack.copy(), (x) / Tile.TILE_SIZE, (y) / Tile.TILE_SIZE);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (!this.inWater() || !this.inLava()) {
            if (canSprint()) {
                speed = LivingEntity.DEFAULT_SPEED * 6f;
            } else {
                speed = LivingEntity.DEFAULT_SPEED;
            }
        }

        if (maewilEngine.getKeyManager().moveUp)
            yMove = -speed;
        if (maewilEngine.getKeyManager().moveDown)
            yMove = speed;
        if (maewilEngine.getKeyManager().moveLeft)
            xMove = -speed;
        if (maewilEngine.getKeyManager().moveRight)
            xMove = speed;
    }

    private void tileInteract() {
        TilePos tilePos = getTilePosAtMid();
        if (maewilEngine.getKeyManager().moveUp)
            tilePos = getTilePosAtMid().up();
        if (maewilEngine.getKeyManager().moveDown)
            tilePos = getTilePosAtMid().down();
        if (maewilEngine.getKeyManager().moveLeft)
            tilePos = getTilePosAtMid().left();
        if (maewilEngine.getKeyManager().moveRight)
            tilePos = getTilePosAtMid().right();

        Tile tile = maewilEngine.getWorld().getForegroundTile(tilePos.getX(), tilePos.getY());
        if (isAttacking) {
            int dmg = 2;
            if (equippedItem != null) {
                if (equippedItem.getItem() instanceof ToolItem) {
                    ToolItem tool = (ToolItem) equippedItem.getItem();
                    if (((tool.getToolType() == ToolItem.ToolType.SHOVEL && tile.getTileType() == Tile.TileType.GROUND) ||
                            (tool.getToolType() == ToolItem.ToolType.PICKAXE && tile.getTileType() == Tile.TileType.STONE) ||
                            (tool.getToolType() == ToolItem.ToolType.AXE && tile.getTileType() == Tile.TileType.WOOD)) ||
                            tool.getToolType() == ToolItem.ToolType.MULTI)
                        dmg += extraDmg;
                }
            }
            tile.damage(dmg);
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        if (UtilsIdk.isDate(3, 31) || UtilsIdk.isDate(4, (1 | 2)))
            getCurrentTexture().getCurrentFrame().rotate((float) Math.toRadians(180d)); // BROKEN, FIX ON A LATER DATE
        getCurrentTexture().getCurrentFrame().draw(this.renderX, this.renderY, width, height);

        this.renderEffect(container, game, g);
    }

    @Override
    public void renderEffect(GameContainer container, StateBasedGame game, Graphics g) {
        super.renderEffect(container, game, g);

        if (canSprint())
            sprintEffect.getCurrentFrame().draw(this.renderX, this.renderY, width, height);
        super.postRender(container, game, g);
    }

    @Override
    public void postRender(GameContainer container, StateBasedGame game, Graphics g) {
        Font font = Assets.FONTS.get("20");
        if (username != null) {
            float nameWidth = Text.getWidth(username, font);
            float nameHeight = Text.getHeight(username, font);
            int add = 8;
            Color tint = new Color(96, 96, 96, 127);
            g.setColor(tint);

            float xOff = nameWidth / 2 - width / 2f;
            int yOff = height / 2;

            g.fillRect(this.renderX - xOff - add / 2f, this.renderY - yOff - add / 2f, nameWidth + add, nameHeight + add);
            Text.drawString(g, username, this.renderX - xOff + nameWidth / 2, this.renderY - yOff - nameHeight / 2 - add / 2f, true, Color.white, font);
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

    public boolean canSprint() {
        return maewilEngine.getKeyManager().useSprint && !inWater() && getCurrentTexture() != getTextures().get("idle");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isMoving() {
        return (this.xMove != 0 || this.yMove != 0) && (this.prevX != position.x || this.prevY != position.y);
    }

    public void reset() {
        inventoryPlayer.clearInventory();
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

    @Override
    public PlayerEntity newCopy() {
        return super.newCopy(new PlayerEntity(maewilEngine, username));
    }
}
