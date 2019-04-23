package coffeecatteam.theultimatetile.state;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.UITextBox;
import coffeecatteam.theultimatetile.jsonparsers.JsonUtils;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * @author CoffeeCatRailway
 * Created: 3/12/2018
 */
public class StateCredits extends StateAbstractMenu {

    private String[] devs, helpers;
    private UITextBox textBoxDev, textBoxHelper;

    public StateCredits(TutEngine tutEngine) {
        super(tutEngine, CENTRE_GRASS);

        try {
            Object[] devs = JsonUtils.getArray("devs", "/assets/credits.json", true).toArray();
            this.devs = new String[devs.length];
            for (int i = 0; i < devs.length; i++)
                this.devs[i] = (String) devs[i];

            Object[] helpers = JsonUtils.getArray("helpers", "/assets/credits.json", true).toArray();
            this.helpers = new String[helpers.length];
            for (int i = 0; i < helpers.length; i++)
                this.helpers[i] = (String) helpers[i];
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        textBoxDev = new UITextBox();
        textBoxDev.addText("Developers", Assets.FONTS.get("40"));
        for (String dev : devs)
            textBoxDev.addText(dev);

        textBoxHelper = new UITextBox();
        textBoxHelper.addText("Helpers", Assets.FONTS.get("40"));
        for (String helper : helpers)
            textBoxHelper.addText(helper);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);

        textBoxDev.setPosition(new Vector2D(TutLauncher.WIDTH / 2d - textBoxDev.getWidth() / 2d, 100));
        float tbDiff = textBoxDev.getWidth() - textBoxHelper.getWidth();
        textBoxHelper.setPosition(new Vector2D(textBoxDev.getPosition().x + tbDiff / 2d, textBoxDev.getPosition().y + textBoxDev.getHeight() + 10));

        textBoxDev.render(container, game, g);
        textBoxHelper.render(container, game, g);
    }
}
