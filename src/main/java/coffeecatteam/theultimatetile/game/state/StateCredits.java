package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.UITextBox;
import coffeecatteam.theultimatetile.jsonparsers.JsonParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

/**
 * @author CoffeeCatRailway
 * Created: 3/12/2018
 */
public class StateCredits extends StateAbstractMenu {

    private String[] devs, helpers;
    private UITextBox textBoxDev, textBoxHelper;

    public StateCredits(Engine engine) {
        super(engine);
        init();

        try {
            JsonParser jsonParser = new JsonParser("/assets/credits.json", true);
            Object[] devs = jsonParser.getArray("devs").toArray();
            this.devs = new String[devs.length];
            for (int i = 0; i < devs.length; i++)
                this.devs[i] = (String) devs[i];

            Object[] helpers = jsonParser.getArray("helpers").toArray();
            this.helpers = new String[helpers.length];
            for (int i = 0; i < helpers.length; i++)
                this.helpers[i] = (String) helpers[i];
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        textBoxDev = new UITextBox();
        textBoxDev.addText("Developers", Assets.FONTS.get("40"), true);
        for (String dev : devs)
            textBoxDev.addText(dev);

        textBoxHelper = new UITextBox();
        textBoxHelper.addText("Helpers", Assets.FONTS.get("40"), true);
        for (String helper : helpers)
            textBoxHelper.addText(helper);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        textBoxDev.setPosition(new Vector2D(engine.getWidth() / 2d - textBoxDev.getWidth() / 2d, 100));
        int tbDiff = textBoxDev.getWidth() - textBoxHelper.getWidth();
        textBoxHelper.setPosition(new Vector2D(textBoxDev.getPosition().x + tbDiff / 2d, textBoxDev.getPosition().y + textBoxDev.getHeight() + 10));

        textBoxDev.render(g);
        textBoxHelper.render(g);
    }
}
