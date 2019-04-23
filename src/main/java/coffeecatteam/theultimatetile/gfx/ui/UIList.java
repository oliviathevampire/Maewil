package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author CoffeeCatRailway
 * Created: 7/03/2019
 */
public class UIList extends UIAbstractList {

    private int maxShownItems, shownItemChange = 0;
    private boolean canMoveUp = true, canMoveDown = true;
    private ArrowButton arrowUp, arrowDown;

    public UIList(float x, float y, float width, int maxShownItems) {
        super(x, y, width - UIListOBJ.SIZE, width, UIListOBJ.SIZE * maxShownItems);
        this.maxShownItems = maxShownItems;

        arrowUp = new ArrowButton(new Vector2D(position.x + itemWidth, position.y), true, new ClickListener() {
            @Override
            public void onClick() {
                if (canMoveUp)
                    shownItemChange += -1;
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });
        arrowDown = new ArrowButton(new Vector2D(), false, new ClickListener() {
            @Override
            public void onClick() {
                if (canMoveDown)
                    shownItemChange += 1;
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);

        arrowUp.update(container, game, delta);
        arrowDown.update(container, game, delta);
        arrowDown.setPos(new Vector2D(position.x + itemWidth, position.y + UIListOBJ.SIZE * (maxShownItems - 1)));
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        for (int i = 0; i < maxShownItems; i++) {
            UIListOBJ obj = getItem(i);
            obj.setRendering(true);
            Vector2D pos = new Vector2D(position.x, position.y + UIListOBJ.SIZE * i);
            if (obj.equals(get(size() - 1)) && i + shownItemChange >= size())
                new UIListOBJ("").render(container, game, g, pos);
            else
                obj.render(container, game, g, pos);
        }

        if (maxShownItems > 2)
            THEME[4].draw((float) (position.x + itemWidth), (float) position.y, UIListOBJ.SIZE, UIListOBJ.SIZE * maxShownItems);
        arrowUp.render(container, game, g);
        arrowDown.render(container, game, g);
    }

    private UIListOBJ getItem(int i) {
        int index = i + shownItemChange;

        if (index <= 0 || shownItemChange == 0) {
            canMoveUp = false;
            canMoveDown = true;
        } else if (index >= size() - 1) {
            canMoveUp = true;
            canMoveDown = false;
        } else {
            canMoveUp = true;
            canMoveDown = true;
        }

        if (size() <= maxShownItems) {
            canMoveUp = false;
            canMoveDown = false;
        }

        if (index < 0) index = 0;
        if (index > size() - 1) index = size() - 1;

        return get(index);
    }

    @Override
    public void onClick() {
        super.onClick();
        arrowUp.onClick();
        arrowDown.onClick();
    }
}
