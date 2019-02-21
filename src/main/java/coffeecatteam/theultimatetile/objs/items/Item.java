package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.inventory.Slot;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Item {

    public static final int WIDTH = 32, HEIGHT = 32;

    protected TutEngine tutEngine;

    protected Animation texture;
    protected ArrayList<Image> textureAlts = new ArrayList<>();
    private int altAmt;
    private boolean hasAlts = false;

    protected final String id;
    protected AABB bounds;

    protected Vector2D position = new Vector2D();
    protected boolean pickedUp = false;
    protected boolean stackable = true;

    public Item(TutEngine tutEngine, String id) {
        this.tutEngine = tutEngine;
        this.id = id;

        this.bounds = new AABB(this.position, WIDTH, HEIGHT);
    }

    protected void chooseAltTexture() {
        if (hasAlts)
            this.texture = new Animation(textureAlts.get(NumberUtils.getRandomInt(0, textureAlts.size() - 1)));
    }

    public void update(int count) {
        if (this.tutEngine.getEntityManager().getPlayer().isActive()) {
            if (this.tutEngine.getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(this.bounds)) {
                if (!this.tutEngine.getEntityManager().getPlayer().getInventoryPlayer().isFull()) {
                    this.pickedUp = true;
                } else {
                    int hotbar = 0;
                    for (Slot slot : this.tutEngine.getEntityManager().getPlayer().getInventoryPlayer().getSlots()) {
                        if (slot.getStack() != null) {
                            if (slot.getStack().getId().equals(this.id)) {
                                if (slot.getStack().getItem().isStackable()) {
                                    this.pickedUp = true;
                                }
                            } else {
                                hotbar++;
                            }
                        }
                    }

                    if (hotbar >= this.tutEngine.getEntityManager().getPlayer().getInventoryPlayer().size()) {
                        if (!this.tutEngine.getEntityManager().getPlayer().getInventoryPlayer().isHotbarFull()) {
                            this.pickedUp = true;
                        } else {
                            for (Slot slot : this.tutEngine.getEntityManager().getPlayer().getInventoryPlayer().getSlots()) {
                                if (slot.getStack() != null) {
                                    if (slot.getStack().getId().equals(this.id)) {
                                        if (slot.getStack().getItem().isStackable()) {
                                            this.pickedUp = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.pickedUp) {
            if (!this.tutEngine.getEntityManager().getPlayer().getInventoryPlayer().isFull())
                this.tutEngine.getEntityManager().getPlayer().getInventoryPlayer().addItem(new ItemStack(this, count));
            else
                this.tutEngine.getEntityManager().getPlayer().getInventoryPlayer().addStackToHotbar(new ItemStack(this, count));
        }

        this.bounds = new AABB(this.position, WIDTH, HEIGHT);
    }

    public void render(Graphics g) {
        render(g, (int) (this.position.x - this.tutEngine.getCamera().getxOffset()), (int) (this.position.y - this.tutEngine.getCamera().getyOffset()));
    }

    public void render(Graphics g, int x, int y) {
        getTexture().draw(x, y, WIDTH, HEIGHT);
    }

    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
        this.bounds.x = x;
        this.bounds.y = y;
    }

    public Image getTexture() {
        this.texture.update();
        return this.texture.getCurrentFrame();
    }

    public void setTexture(Image texture) {
        this.texture = new Animation(texture);
    }

    public void setAnimation(Animation texture) {
        this.texture = texture;
    }

    public boolean hasAlts() {
        return hasAlts;
    }

    public void setHasAlts(boolean hasAlts) {
        this.hasAlts = hasAlts;
    }

    public ArrayList<Image> getTextureAlts() {
        return textureAlts;
    }

    public void setTextureAlts(Image[] textureAlts) {
        setTextureAlts(Arrays.asList(textureAlts), textureAlts.length);
    }

    public void setTextureAlts(Image[] textureAlts, int altAmt) {
        setTextureAlts(Arrays.asList(textureAlts), altAmt);
    }

    public void setTextureAlts(List<Image> textureAlts) {
        setTextureAlts(textureAlts, textureAlts.size());
    }

    public void setTextureAlts(List<Image> textureAlts, int altAmt) {
        this.textureAlts = new ArrayList<>();
        for (int i = 0; i < altAmt; i++)
            this.textureAlts.add(textureAlts.get(i));
        this.altAmt = altAmt;
        chooseAltTexture();
    }

    public String getId() {
        return this.id;
    }

    public AABB getBounds() {
        return bounds;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public boolean isPickedUp() {
        return this.pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public abstract <T extends Item> T newItem();

    protected <T extends Item> T newItem(T item) {
        T i = item;
        i.setAnimation(texture);
        i.setHasAlts(hasAlts);
        i.setTextureAlts(textureAlts);
        i.setPosition(position);
        i.setPickedUp(pickedUp);
        i.setStackable(stackable);
        return i;
    }
}
