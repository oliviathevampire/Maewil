package coffeecatteam.theultimatetile.game.state.options;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.UIButtonSlider;
import coffeecatteam.theultimatetile.gfx.ui.UISlider;
import coffeecatteam.theultimatetile.utils.Logger;

import java.awt.*;

public class OptionsSounds extends StateAbstractOptions {

    private UISlider volMusic, volPassive, volHostile, volPlayer, volOther;

    public OptionsSounds(GameEngine gameEngineIn) {
        super(gameEngineIn);
        init();
        int width = 200;
        int x = 80, xOff = width + 120;
        int y = 100, yOff = 130;

        uiManager.addObject(volMusic = new UISlider(gameEngineIn, x, y, width, (int) (StateOptions.OPTIONS.getVolumeMusic() * 10)));

        uiManager.addObject(volPassive = new UISlider(gameEngineIn, x, y + yOff, width, (int) (StateOptions.OPTIONS.getVolumePassive() * 10)));

        uiManager.addObject(volHostile = new UISlider(gameEngineIn, x, y + yOff * 2, width, (int) (StateOptions.OPTIONS.getVolumeHostile() * 10)));

        uiManager.addObject(volPlayer = new UISlider(gameEngineIn, x + xOff, y, width, (int) (StateOptions.OPTIONS.getVolumePlayer() * 10)));

        uiManager.addObject(volOther = new UISlider(gameEngineIn, x + xOff, y + yOff, width, (int) (StateOptions.OPTIONS.getVolumeOther() * 10)));
    }

    @Override
    public void tick() {
        super.tick();

        StateOptions.OPTIONS.setVolumeMusic(volMusic.getValue() / 10f);
        StateOptions.OPTIONS.setVolumePassive(volPassive.getValue() / 10f);
        StateOptions.OPTIONS.setVolumeHostile(volHostile.getValue() / 10f);
        StateOptions.OPTIONS.setVolumePlayer(volPlayer.getValue() / 10f);
        StateOptions.OPTIONS.setVolumeOther(volOther.getValue() / 10f);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        Font font = Assets.FONT_30;
        Color c = Color.WHITE;
        int ytOff = 15;
        Text.drawString(g, "Music: " + volMusic.getValue(), (int) volMusic.getX(), (int) volMusic.getY() - ytOff, false, false, c, font);
        Text.drawString(g, "Passive: " + volPassive.getValue(), (int) volPassive.getX(), (int) volPassive.getY() - ytOff, false, false, c, font);
        Text.drawString(g, "Hostile: " + volHostile.getValue(), (int) volHostile.getX(), (int) volHostile.getY() - ytOff, false, false, c, font);
        Text.drawString(g, "Player: " + volPlayer.getValue(), (int) volPlayer.getX(), (int) volPlayer.getY() - ytOff, false, false, c, font);
        Text.drawString(g, "Other: " + volOther.getValue(), (int) volOther.getX(), (int) volOther.getY() - ytOff, false, false, c, font);
    }
}
