package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
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

    public StateCredits(Engine engine) {
        super(engine);
        init();

        try {
            JsonParser jsonParser = new JsonParser("/assets/credits.json", true);
            Object[] devs = jsonParser.getArray("devs").toArray();
            this.devs = new String[devs.length];
            for (int i  = 0; i < devs.length; i++)
                this.devs[i] = (String) devs[i];

            Object[] helpers = jsonParser.getArray("helpers").toArray();
            this.helpers = new String[helpers.length];
            for (int i  = 0; i < helpers.length; i++)
                this.helpers[i] = (String) helpers[i];
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        Font titleFont = Assets.FONT_40;
        int titleHeight = Text.getHeight(g, titleFont), yPadding = 100, devY = 25 + titleHeight;
        Text.drawStringCenteredX(g, "Developers", devY + yPadding, true, Color.WHITE, titleFont);

        Font personFont = Assets.FONT_20;
        int devHeight = Text.getHeight(g, personFont), lastDevY = 0;
        for (int i  = 0; i < devs.length; i++) {
            lastDevY = 5 + devY + devHeight * (i + 1);
            Text.drawStringCenteredX(g, devs[i], lastDevY + yPadding, false, Color.WHITE, personFont);
        }

        int helperY = 15 + titleHeight + lastDevY;
        Text.drawStringCenteredX(g, "Helpers", helperY + yPadding, true, Color.WHITE, titleFont);

        int helperHeight = Text.getHeight(g, personFont);
        for (int i  = 0; i < helpers.length; i++)
            Text.drawStringCenteredX(g, helpers[i], 5 + helperY + helperHeight * (i + 1) + yPadding, false, Color.WHITE, personFont);
    }
}
