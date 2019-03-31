package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.objs.IHasData;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Item implements IHasData<Item> {

    public static final int WIDTH = 32, HEIGHT = 32;

    protected TutEngine tutEngine;

    protected Animation texture;
    protected ArrayList<Image> textureAlts = new ArrayList<>();
    private boolean hasAlts = false;

    protected final String id;
    protected boolean stackable = true;

    public Item(TutEngine tutEngine, String id) {
        this.tutEngine = tutEngine;
        this.id = id;
        Items.UPDATABLE_TIEMS.add(this);
    }

    protected void chooseAltTexture() {
        if (hasAlts)
            this.texture = new Animation(textureAlts.get(NumberUtils.getRandomInt(0, textureAlts.size() - 1)));
    }

    public void update(GameContainer container, int delta) {
        this.texture.update();
    }

    public Image getTexture() {
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
        setTextureAlts(Arrays.asList(textureAlts));
    }

    public void setTextureAlts(List<Image> textureAlts) {
        this.textureAlts = new ArrayList<>();
        this.textureAlts.addAll(textureAlts);
        chooseAltTexture();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public LangType getType() {
        return LangType.ITEM;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    @Override
    public <T extends Item> T newCopy(T obj) {
        T item = obj;
        item.setAnimation(texture);
        item.setHasAlts(hasAlts);
        item.setTextureAlts(textureAlts);
        item.setStackable(stackable);
        return item;
    }
}
