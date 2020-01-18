package io.github.vampirestudios.tdg.screen.options;

import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.screen.AbstractMenuScreen;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class CharacterCustomizationScreen extends AbstractMenuScreen {

    public CharacterCustomizationScreen(MaewilEngine maewilEngine) {
        super(maewilEngine, CENTRE_GRASS);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);

        Font font = Assets.FONTS.get("35");
        Color c = Color.white;
        Text.drawStringCenteredX(g, "Customize your character", 70, c, font);

        Image current = maewilEngine.getPlayer().getTextures().get("idle").getCurrentFrame();
        current.draw(10, 70, 50, 50);
    }
}
