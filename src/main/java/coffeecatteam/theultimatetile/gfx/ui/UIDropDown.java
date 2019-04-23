package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlink;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author CoffeeCatRailway
 * Created: 7/03/2019
 */
public class UIDropDown extends UIAbstractList {

    private String title;
    private ArrowButton dropdownArrow;
    private boolean isDown = false;

    public UIDropDown(float x, float y, float width, String title) {
        super(x, y, width, width + UIListOBJ.SIZE, UIListOBJ.SIZE);
        this.title = title;
        this.dropdownArrow = new ArrowButton(new Vector2D(x + width, y), false, new ClickListener() {
            @Override
            public void onClick() {
                isDown = !isDown;
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
                dropdownArrow.setIconUpArrow(isDown);
            }
        });
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
        bounds.setSize(bounds.width, (int) getHeight());
        dropdownArrow.update(container, game, delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        Image secLeft = THEME[0];
        Image secMid = THEME[1];
        Image secRight = THEME[2];

        float padding = 10f;
        Font font = getCorrectFont(title, UIListOBJ.SIZE - padding * 2f);
        float midLength = itemWidth - UIListOBJ.SIZE;

        secLeft.draw((float) position.x, (float) position.y, UIListOBJ.SIZE, UIListOBJ.SIZE);
        secMid.draw((float) position.x + padding, (float) position.y, midLength, UIListOBJ.SIZE);
        secRight.draw((float) position.x + itemWidth - UIListOBJ.SIZE, (float) position.y, UIListOBJ.SIZE, UIListOBJ.SIZE);

        Color color = bounds.contains(TutEngine.getTutEngine().getMousePos()) ? UIHyperlink.hoverColor : UIHyperlink.mainColor;
        Text.drawString(g, title, (float) (position.x + padding), (float) (position.y + Text.getHeight(title, font) + padding), false, color, font);

        if (isDown) {
            for (int i = 0; i < size(); i++) {
                UIListOBJ obj = get(i);
                obj.setRendering(true);
                Vector2D pos = new Vector2D(position.x, position.y + UIListOBJ.SIZE * (i + 1));
                obj.render(container, game, g, pos);
            }

            THEME[4].draw((float) (position.x + itemWidth), (float) position.y, UIListOBJ.SIZE, UIListOBJ.SIZE * size());
            THEME[3].draw((float) (position.x + itemWidth), (float) (position.y + UIListOBJ.SIZE * size()), UIListOBJ.SIZE, UIListOBJ.SIZE);
        }

        dropdownArrow.render(container, game, g);
    }

    @Override
    public void onClick() {
        super.onClick();
        dropdownArrow.onClick();
    }

    public boolean isDown() {
        return isDown;
    }

    @Override
    public float getHeight() {
        return isDown ? UIListOBJ.SIZE * (size() + 1) : UIListOBJ.SIZE;
    }
}
