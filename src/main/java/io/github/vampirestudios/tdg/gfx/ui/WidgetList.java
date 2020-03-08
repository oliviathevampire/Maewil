package io.github.vampirestudios.tdg.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

public class WidgetList extends WidgetAbstractList {

    private int maxShownItems, shownItemChange = 0;
    private boolean canMoveUp = true, canMoveDown = true;
    private ArrowButton arrowUp, arrowDown;

    public WidgetList(float x, float y, float width, int maxShownItems) {
        super(x, y, width - SIZE, width, SIZE * maxShownItems + 1);
        this.maxShownItems = maxShownItems;

        arrowUp = new ArrowButton(new Vector2D(position.x + itemWidth, position.y), true, new ClickListener() {
            @Override
            public void onClick() {
                if (canMoveUp)
                    shownItemChange += -1;
            }

            @Override
            public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
            }
        });
        arrowDown = new ArrowButton(new Vector2D(), false, new ClickListener() {
            @Override
            public void onClick() {
                if (canMoveDown)
                    shownItemChange += 1;
            }

            @Override
            public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
            }
        });
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        super.update(container, delta);

        arrowUp.update(container, delta);
        arrowDown.update(container, delta);
        arrowDown.setPos(new Vector2D(position.x + itemWidth, position.y + SIZE * (maxShownItems - 1)));
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        for (int i = 0; i < maxShownItems; i++) {
            UIListOBJ obj = getItem(i);
            obj.setRendering(true);
            Vector2D pos = new Vector2D(position.x, position.y + SIZE * i);
            if (obj.equals(get(size() - 1)) && i + shownItemChange >= size())
                new UIListOBJ("").render(container, g, pos);
            else
                obj.render(container, g, pos);
        }

        if (maxShownItems > 2)
            THEME[4].draw((float) (position.x + itemWidth), (float) position.y, SIZE, SIZE * maxShownItems);
        arrowUp.render(container, g);
        arrowDown.render(container, g);
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
