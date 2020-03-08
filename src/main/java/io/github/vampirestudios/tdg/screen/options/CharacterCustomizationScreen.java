package io.github.vampirestudios.tdg.screen.options;

import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.screen.AbstractMenuScreen;
import io.github.vampirestudios.tdg.screen.TileBackgrounds;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

public class CharacterCustomizationScreen extends AbstractMenuScreen {

    public CharacterCustomizationScreen(MaewilEngine maewilEngine) {
        super(maewilEngine, TileBackgrounds.GRASS.getTiles());
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        super.render(container, g);

        Font font = Assets.FONTS.get("35");
        Color c = Color.white;
        Text.drawStringCenteredX(g, "Customize your character", 70, c, font);

        Image current = maewilEngine.getPlayer().getTextures().get("idle").getCurrentFrame();
        current.draw(10, 70, 50, 50);
    }
}
