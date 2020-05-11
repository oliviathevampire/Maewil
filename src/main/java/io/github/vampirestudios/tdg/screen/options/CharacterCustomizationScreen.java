package io.github.vampirestudios.tdg.screen.options;

import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.ui.WidgetInputBox;
import io.github.vampirestudios.tdg.screen.AbstractMenuScreen;
import io.github.vampirestudios.tdg.screen.TileBackgrounds;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class CharacterCustomizationScreen extends AbstractMenuScreen {

    private WidgetInputBox skinVariant;

    public CharacterCustomizationScreen(MaewilEngine maewilEngine) {
        super(maewilEngine, TileBackgrounds.GRASS.getTiles());
        int width = 200;
        int x = 80, xOff = width + 120;
        int y = 100, yOff = 130;

        uiManager.addObject(skinVariant = new WidgetInputBox(x, y, width));
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);

        skinVariant.update(container, game, delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);

        Font font = Assets.FONTS.get("35");
        Color c = Color.white;
        Text.drawStringCenteredX(g, "Customize your character", 70, c, font);

        skinVariant.render(container, game, g);

//        Image current = maewilEngine.getPlayer().getTextures().get("idle").getCurrentFrame();
//        current.draw(10, 70, 50, 50);
    }
}
