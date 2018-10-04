package coffeecatteam.theultimatetile.gfx.ui;

import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.state.StateOptions;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UISlider extends UIObject {

    private String title;

    private float maxVolume = 1f;
    private UIButton volumeDown, volumeUp;

    public UISlider(float x, float y, String title) {
        super(x, y, 64, 64);
        this.title = title;

        volumeDown = new UIButton(x, y, width, height, "<", new ClickListener() {
            @Override
            public void onClick() {
                changeVolume(-0.1f);
            }

            @Override
            public void tick() {
            }
        });
        volumeUp = new UIButton(x, y, width, height, ">", new ClickListener() {
            @Override
            public void onClick() {
                changeVolume(0.1f);
            }

            @Override
            public void tick() {
            }
        });
    }

    private void changeVolume(float amt) {
        StateOptions.OPTIONS.setVolumeMusic(StateOptions.OPTIONS.getVolumeMusic() + amt);
        if (StateOptions.OPTIONS.getVolumeMusic() < 0f) StateOptions.OPTIONS.setVolumeMusic(0f);
        if (StateOptions.OPTIONS.getVolumeMusic() > maxVolume) StateOptions.OPTIONS.setVolumeMusic(maxVolume);
    }

    @Override
    public void tick() {
        volumeDown.tick();
        volumeUp.tick();
    }

    @Override
    public void render(Graphics g) {
        volumeDown.render(g);

        Font font = Assets.FONT_40;
        String text = String.valueOf((int) (StateOptions.OPTIONS.getVolumeMusic() * 10));
        int y = (int) (this.y + Text.getHeight(g, font) + 5);
        Text.drawString(g, text, (int) (x + 79), y, false, false, Color.lightGray, font);

        float vupx = x + 84 + Text.getWidth(g, text, font);
        volumeUp.setX(vupx);
        volumeUp.render(g);

        Text.drawString(g, this.title, (int) (vupx + 79), y, false, false, Color.lightGray, font);
    }

    @Override
    public void onClick() {
        volumeDown.onClick();
        volumeUp.onClick();
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        volumeDown.onMouseMoved(e);
        volumeUp.onMouseMoved(e);
    }

    @Override
    public void onMouseRelease(MouseEvent e) {
        volumeDown.onMouseRelease(e);
        volumeUp.onMouseRelease(e);
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        volumeDown.onMouseDragged(e);
        volumeUp.onMouseDragged(e);
    }
}
