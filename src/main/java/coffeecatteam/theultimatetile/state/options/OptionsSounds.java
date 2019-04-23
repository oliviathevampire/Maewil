package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.UISlider;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class OptionsSounds extends StateAbstractOptions {

    private UISlider volMusic, volPassive, volHostile, volPlayer, volOther;

    public OptionsSounds(TutEngine tutEngineIn) {
        super(tutEngineIn);
        int width = 200;
        int x = 80, xOff = width + 120;
        int y = 100, yOff = 130;

        uiManager.addObject(volMusic = new UISlider(tutEngineIn, new Vector2D(x, y), width, (int) (StateOptions.OPTIONS.getVolumeMusic() * 10)));

        uiManager.addObject(volPassive = new UISlider(tutEngineIn, new Vector2D(x, y + yOff), width, (int) (StateOptions.OPTIONS.getVolumePassive() * 10)));

        uiManager.addObject(volHostile = new UISlider(tutEngineIn, new Vector2D(x, y + yOff * 2), width, (int) (StateOptions.OPTIONS.getVolumeHostile() * 10)));

        uiManager.addObject(volPlayer = new UISlider(tutEngineIn, new Vector2D(x + xOff, y), width, (int) (StateOptions.OPTIONS.getVolumePlayer() * 10)));

        uiManager.addObject(volOther = new UISlider(tutEngineIn, new Vector2D(x + xOff, y + yOff), width, (int) (StateOptions.OPTIONS.getVolumeOther() * 10)));
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);

        StateOptions.OPTIONS.setVolumeMusic(volMusic.getValue() / 10f);
        StateOptions.OPTIONS.setVolumePassive(volPassive.getValue() / 10f);
        StateOptions.OPTIONS.setVolumeHostile(volHostile.getValue() / 10f);
        StateOptions.OPTIONS.setVolumePlayer(volPlayer.getValue() / 10f);
        StateOptions.OPTIONS.setVolumeOther(volOther.getValue() / 10f);
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
