package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlink;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 20/04/2019
 */
public abstract class UIAbstractList extends AbstractListTheme {

    /*
     * List code
     */
    private List<UIListOBJ> ITEMS = new ArrayList<>();
    private int size = 0;

    public boolean add(String label) {
        size++;
        return ITEMS.add(new UIListOBJ(label));
    }

    public boolean add(String label, Animation icon) {
        size++;
        return ITEMS.add(new UIListOBJ(label, icon));
    }

    public boolean add(String label, ClickListener listener) {
        size++;
        return ITEMS.add(new UIListOBJ(label, listener));
    }

    public boolean add(String label, Animation icon, ClickListener listener) {
        size++;
        return ITEMS.add(new UIListOBJ(label, icon, listener));
    }

    public UIListOBJ get(int index) {
        return ITEMS.get(index);
    }

    public UIListOBJ remove(int index) {
        size--;
        return ITEMS.remove(index);
    }

    public int size() {
        return size;
    }

    /*
     * Other code
     */
    protected float itemWidth;

    UIAbstractList(float x, float y, float itemWidthIn, float width, float height) {
        this(new Vector2D(x, y), itemWidthIn, width, height);
    }

    UIAbstractList(Vector2D position, float itemWidthIn, float width, float height) {
        super(position, width, height);
        itemWidth = itemWidthIn;
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);

        for (UIListOBJ ITEM : ITEMS) ITEM.update(container, game, delta);
    }

    @Override
    public void onClick() {
        for (int i = 0; i < size; i++) ITEMS.get(i).onClick();
    }

    public class ArrowButton {

        private Vector2D pos;
        private AABB bounds;
        private boolean hovering, iconUpArrow;
        private ClickListener listener;

        public ArrowButton(Vector2D pos, boolean iconUpArrow, ClickListener listener) {
            this.pos = pos;
            this.iconUpArrow = iconUpArrow;
            this.listener = listener;
        }

        public void onClick() {
            if (hovering) {
                Sounds.CLICK_1.play(0.5f, 0.2f);
                listener.onClick();
            }
        }

        public void update(GameContainer container, StateBasedGame game, int delta) {
            listener.update(container, game, delta);
            bounds = new AABB(pos, (int) SIZE, (int) SIZE);
            hovering = bounds.contains(TutEngine.INSTANCE.getMousePos());
        }

        public void render(GameContainer container, StateBasedGame game, Graphics g) {
            THEME[3].draw((float) pos.x, (float) pos.y, SIZE, SIZE);

            Image arrow = Assets.GUI_ARROW_ICONS[(iconUpArrow ? 0 : 2) + (hovering ? 1 : 0)];
            arrow.draw((float) pos.x, (float) pos.y, SIZE, SIZE);
        }

        public void setPos(Vector2D pos) {
            this.pos = pos;
        }

        public void setIconUpArrow(boolean iconUpArrow) {
            this.iconUpArrow = iconUpArrow;
        }
    }

    public class UIListOBJ {

        private String label;
        private Animation icon;
        private ClickListener listener;
        private AABB bounds = new AABB();
        private boolean hasIcon, rendering;

        UIListOBJ(String label) {
            this(label, Tiles.AIR.getAnimation());
        }

        UIListOBJ(String label, Animation icon) {
            this(label, icon, new ClickListener() {
                @Override
                public void onClick() {
                }

                @Override
                public void update(GameContainer container, StateBasedGame game, int delta) {
                }
            });
        }

        UIListOBJ(String label, ClickListener listener) {
            this(label, Tiles.AIR.getAnimation(), listener);
        }

        UIListOBJ(String label, Animation icon, ClickListener listener) {
            this.label = label;
            this.icon = icon;
            this.listener = listener;
        }

        public void onClick() {
            if (this.bounds.contains(TutEngine.INSTANCE.getMousePos()) && rendering) {
                listener.onClick();
                Sounds.CLICK_1.play(0.5f, 0.2f);
            }
        }

        public void update(GameContainer container, StateBasedGame game, int delta) {
            listener.update(container, game, delta);
            this.hasIcon = icon != Tiles.AIR.getAnimation();
            rendering = false;
        }

        public void render(GameContainer container, StateBasedGame game, Graphics g, Vector2D pos) {
            bounds = new AABB(pos, (int) itemWidth, (int) SIZE);

            Image secLeft = THEME[0];
            Image secMid = THEME[1];
            Image secRight = THEME[2];
            float midLength = (float) ((pos.x + itemWidth - SIZE) - (pos.x + SIZE));

            secLeft.draw((float) pos.x, (float) pos.y, SIZE, SIZE);
            secMid.draw((float) pos.x + SIZE, (float) pos.y, midLength, SIZE);
            secRight.draw((float) pos.x + itemWidth - SIZE, (float) pos.y, SIZE, SIZE);

            float padding = 10f;
            float iconSize = SIZE - padding;
            float textIconOff = padding / 2f + iconSize;
            Vector2D textPos = new Vector2D(pos.x + padding, pos.y + SIZE - padding * 1.5f);
            if (hasIcon) {
                textPos.add(textIconOff, 0);
                icon.getCurrentFrame().draw((float) (pos.x + padding / 2f), (float) (pos.y + padding / 2f), iconSize, iconSize);
            }

            Color color = bounds.contains(TutEngine.INSTANCE.getMousePos()) ? UIHyperlink.hoverColor : UIHyperlink.mainColor;
            Font font = Text.getCorrectFont(label, SIZE - padding * 3f);
            Object[] textFont = Text.fitTextInWidth(label, font, itemWidth - padding * 2 - (hasIcon ? textIconOff : 0), SIZE - padding * 3f, true);

            Text.drawString(g, String.valueOf(textFont[0]), (float) textPos.x, (float) textPos.y, false, color, (Font) textFont[1]);
        }

        public AABB getBounds() {
            return bounds;
        }

        public void setBounds(AABB bounds) {
            this.bounds = bounds;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Animation getIcon() {
            return icon;
        }

        public void setIcon(Animation icon) {
            this.icon = icon;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }

        public void setHasIcon(boolean hasIcon) {
            this.hasIcon = hasIcon;
        }

        public boolean isRendering() {
            return rendering;
        }

        public void setRendering(boolean rendering) {
            this.rendering = rendering;
        }
    }
}
