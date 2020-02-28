package io.github.vampirestudios.tdg.gfx.ui;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.ui.hyperlink.WidgetHyperlink;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class WidgetDropDown extends WidgetAbstractList {

    private String title;
    private ArrowButton dropdownArrow;
    private boolean isDown = false;

    public WidgetDropDown(float x, float y, float width, String title) {
        super(x, y, width, width + SIZE, SIZE);
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
        Font font = Text.getCorrectFont(title, SIZE - padding * 2f);
        float midLength = itemWidth - SIZE;

        secLeft.draw((float) position.x, (float) position.y, SIZE, SIZE);
        secMid.draw((float) position.x + padding, (float) position.y, midLength, SIZE);
        secRight.draw((float) position.x + itemWidth - SIZE, (float) position.y, SIZE, SIZE);

        Text.drawString(g, title, (float) (position.x + padding), (float) (position.y + Text.getHeight(title, font) + padding), false, WidgetHyperlink.mainColor, font);

        if (isDown) {
            for (int i = 0; i < size(); i++) {
                UIListOBJ obj = get(i);
                obj.setRendering(true);
                Vector2D pos = new Vector2D(position.x, position.y + SIZE * (i + 1));
                obj.render(container, game, g, pos);
            }

            THEME[4].draw((float) (position.x + itemWidth), (float) position.y, SIZE, SIZE * size());
            THEME[3].draw((float) (position.x + itemWidth), (float) (position.y + SIZE * size()), SIZE, SIZE);
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
        return isDown ? SIZE * (size() + 1) : SIZE;
    }
}
