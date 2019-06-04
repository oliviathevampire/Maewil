package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.state.StateAbstractMenu;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class StateCharacterCustomization extends StateAbstractMenu {

    private UIButton btnPlayer;

    public StateCharacterCustomization(TutEngine tutEngine) {
        super(tutEngine, CENTRE_GRASS);
        float sizeP = 64;
        float xp = TutLauncher.WIDTH / 4f - sizeP / 2f;
        float yp = TutLauncher.HEIGHT / 2f - sizeP / 2f;
        uiManager.addObject(btnPlayer = new UIButton(tutEngine, new Vector2D(xp, yp), tutEngine.getPlayer().getTextures().get("idle"), 4.0f, 0, new ClickListener() {
            @Override
            public void onClick() {
                // TODO: Add entity texture data alt support
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));
        btnPlayer.setRenderBtnBG(false);
        btnPlayer.setRenderBtnHover(false);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);

        Font font = Assets.FONTS.get("35");
        Color c = Color.white;
        Text.drawStringCenteredX(g, "Customize your character", 70, c, font);
    }
}
