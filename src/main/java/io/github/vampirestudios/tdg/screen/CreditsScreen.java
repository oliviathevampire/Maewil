package io.github.vampirestudios.tdg.screen;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.ui.WidgetTextBox;
import io.github.vampirestudios.tdg.jsonparsers.JsonUtils;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import org.json.simple.parser.ParseException;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.newdawn.slick.Color;

import java.io.IOException;

public class CreditsScreen extends AbstractMenuScreen {

    private String[] devs, helpers, other;
    private WidgetTextBox textBoxDev, textBoxHelper, textBoxOther;

    public CreditsScreen(MaewilEngine maewilEngine) {
        super(maewilEngine, TileBackgrounds.GRASS.getTiles(), TileBackgrounds.GRASS.getTiles());

        try {
            Object[] devs = JsonUtils.getArray("devs", "/assets/credits.json", true).toArray();
            this.devs = new String[devs.length];
            for (int i = 0; i < devs.length; i++)
                this.devs[i] = (String) devs[i];

            Object[] helpers = JsonUtils.getArray("helpers", "/assets/credits.json", true).toArray();
            this.helpers = new String[helpers.length];
            for (int i = 0; i < helpers.length; i++)
                this.helpers[i] = (String) helpers[i];

            Object[] other = JsonUtils.getArray("other", "/assets/credits.json", true).toArray();
            this.other = new String[other.length];
            for (int i = 0; i < other.length; i++)
                this.other[i] = (String) other[i];
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        textBoxDev = new WidgetTextBox();
        textBoxDev.addText("Developers", Assets.FONTS.get("40"), Color.black);
        for (String dev : devs)
            textBoxDev.addText(dev, Assets.FONTS.get("20"), Color.darkGray);

        textBoxHelper = new WidgetTextBox();
        textBoxHelper.addText("Helpers", Assets.FONTS.get("40"), Color.black);
        for (String helper : helpers)
            textBoxHelper.addText(helper, Assets.FONTS.get("20"), Color.darkGray);

        textBoxOther = new WidgetTextBox();
        textBoxOther.addText("Other", Assets.FONTS.get("40"), Color.black);
        for (String other : other)
            textBoxOther.addText(other, Assets.FONTS.get("20"), Color.darkGray);
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        super.update(container, delta);
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        super.render(container, g);

        textBoxDev.setPosition(new Vector2D(MaewilLauncher.WIDTH / 2d - textBoxDev.getWidth() / 2d, 80));
        float tbDiff = textBoxDev.getWidth() - textBoxHelper.getWidth();
        textBoxHelper.setPosition(new Vector2D(textBoxDev.getPosition().x + tbDiff / 2d, textBoxDev.getPosition().y + textBoxDev.getHeight() + 10));
        float tbDiff2 = textBoxHelper.getWidth() - textBoxOther.getWidth();
        textBoxOther.setPosition(new Vector2D(textBoxHelper.getPosition().x + tbDiff2 / 2d, textBoxHelper.getPosition().y + textBoxHelper.getHeight() + 10));

        textBoxDev.render(container, g);
        textBoxHelper.render(container, g);
        textBoxOther.render(container, g);
    }
}
