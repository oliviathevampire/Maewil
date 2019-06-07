package coffeecatteam.theultimatetile.screen.options;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.WidgetSlider;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class SoundOptions extends AbstractOptions {

    private WidgetSlider volMusic, volPassive, volHostile, volPlayer, volOther;

    public SoundOptions(TutEngine tutEngineIn) {
        super(tutEngineIn);
        int width = 200;
        int x = 80, xOff = width + 120;
        int y = 100, yOff = 130;

        uiManager.addObject(volMusic = new WidgetSlider(tutEngineIn, new Vector2D(x, y), width, (int) (OptionsScreen.OPTIONS.getVolumeMusic() * 10)));

        uiManager.addObject(volPassive = new WidgetSlider(tutEngineIn, new Vector2D(x, y + yOff), width, (int) (OptionsScreen.OPTIONS.getVolumePassive() * 10)));

        uiManager.addObject(volHostile = new WidgetSlider(tutEngineIn, new Vector2D(x, y + yOff * 2), width, (int) (OptionsScreen.OPTIONS.getVolumeHostile() * 10)));

        uiManager.addObject(volPlayer = new WidgetSlider(tutEngineIn, new Vector2D(x + xOff, y), width, (int) (OptionsScreen.OPTIONS.getVolumePlayer() * 10)));

        uiManager.addObject(volOther = new WidgetSlider(tutEngineIn, new Vector2D(x + xOff, y + yOff), width, (int) (OptionsScreen.OPTIONS.getVolumeOther() * 10)));
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);

        OptionsScreen.OPTIONS.setVolumeMusic(volMusic.getValue() / 10f);
        OptionsScreen.OPTIONS.setVolumePassive(volPassive.getValue() / 10f);
        OptionsScreen.OPTIONS.setVolumeHostile(volHostile.getValue() / 10f);
        OptionsScreen.OPTIONS.setVolumePlayer(volPlayer.getValue() / 10f);
        OptionsScreen.OPTIONS.setVolumeOther(volOther.getValue() / 10f);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);

        Font font = Assets.FONTS.get("30");
        Color c = Color.white;
        int ytOff = 15;
        Text.drawString(g, "Music: " + volMusic.getValue(), (int) volMusic.getPosition().x, (int) volMusic.getPosition().y - ytOff, false, false, c, font);
        Text.drawString(g, "Passive: " + volPassive.getValue(), (int) volPassive.getPosition().x, (int) volPassive.getPosition().y - ytOff, false, false, c, font);
        Text.drawString(g, "Hostile: " + volHostile.getValue(), (int) volHostile.getPosition().x, (int) volHostile.getPosition().y - ytOff, false, false, c, font);
        Text.drawString(g, "Player: " + volPlayer.getValue(), (int) volPlayer.getPosition().x, (int) volPlayer.getPosition().y - ytOff, false, false, c, font);
        Text.drawString(g, "Other: " + volOther.getValue(), (int) volOther.getPosition().x, (int) volOther.getPosition().y - ytOff, false, false, c, font);
    }
}
