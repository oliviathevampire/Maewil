package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.state.StateAbstractMenu;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class StateCharacterCustomization extends StateAbstractMenu {

    public StateCharacterCustomization(TutEngine tutEngine) {
        super(tutEngine, CENTRE_GRASS);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);

        Font font = Assets.FONTS.get("35");
        Color c = Color.white;
        Text.drawStringCenteredX(g, "Customize your character", 70, c, font);

        Image current = tutEngine.getPlayer().getTextures().get("idle").getCurrentFrame();
        current.draw(10, 70, 30, 30);
    }
}
