package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.ui.UISlider;
import coffeecatteam.theultimatetile.state.StateOptions;

public class OptionsSounds extends StateAbstractOptions {

    public OptionsSounds(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
        init();

        uiManager.addObject(new UISlider(15, 15, "Music") {
            @Override
            public float changeValue(float amt) {
                StateOptions.OPTIONS.setVolumeMusic(StateOptions.OPTIONS.getVolumeMusic() + amt);
                if (StateOptions.OPTIONS.getVolumeMusic() < 0f) StateOptions.OPTIONS.setVolumeMusic(0f);
                if (StateOptions.OPTIONS.getVolumeMusic() > maxValue) StateOptions.OPTIONS.setVolumeMusic(maxValue);
                return StateOptions.OPTIONS.getVolumeMusic();
            }
        });

        uiManager.addObject(new UISlider(15, 94, "Passive") {
            @Override
            public float changeValue(float amt) {
                StateOptions.OPTIONS.setVolumePassive(StateOptions.OPTIONS.getVolumePassive() + amt);
                if (StateOptions.OPTIONS.getVolumePassive() < 0f) StateOptions.OPTIONS.setVolumePassive(0f);
                if (StateOptions.OPTIONS.getVolumePassive() > maxValue) StateOptions.OPTIONS.setVolumePassive(maxValue);
                return StateOptions.OPTIONS.getVolumePassive();
            }
        });

        uiManager.addObject(new UISlider(15, 173, "Hostile") {
            @Override
            public float changeValue(float amt) {
                StateOptions.OPTIONS.setVolumeHostile(StateOptions.OPTIONS.getVolumeHostile() + amt);
                if (StateOptions.OPTIONS.getVolumeHostile() < 0f) StateOptions.OPTIONS.setVolumeHostile(0f);
                if (StateOptions.OPTIONS.getVolumeHostile() > maxValue) StateOptions.OPTIONS.setVolumeHostile(maxValue);
                return StateOptions.OPTIONS.getVolumeHostile();
            }
        });

        uiManager.addObject(new UISlider(15, 252, "Player") {
            @Override
            public float changeValue(float amt) {
                StateOptions.OPTIONS.setVolumePlayer(StateOptions.OPTIONS.getVolumePlayer() + amt);
                if (StateOptions.OPTIONS.getVolumePlayer() < 0f) StateOptions.OPTIONS.setVolumePlayer(0f);
                if (StateOptions.OPTIONS.getVolumePlayer() > maxValue) StateOptions.OPTIONS.setVolumePlayer(maxValue);
                return StateOptions.OPTIONS.getVolumePlayer();
            }
        });

        uiManager.addObject(new UISlider(15, 331, "Other") {
            @Override
            public float changeValue(float amt) {
                StateOptions.OPTIONS.setVolumeOther(StateOptions.OPTIONS.getVolumeOther() + amt);
                if (StateOptions.OPTIONS.getVolumeOther() < 0f) StateOptions.OPTIONS.setVolumeOther(0f);
                if (StateOptions.OPTIONS.getVolumeOther() > maxValue) StateOptions.OPTIONS.setVolumeOther(maxValue);
                return StateOptions.OPTIONS.getVolumeOther();
            }
        });
    }
}
