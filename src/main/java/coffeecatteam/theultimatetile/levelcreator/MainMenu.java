package coffeecatteam.theultimatetile.levelcreator;

import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;

public class MainMenu {

    private CreatorEngine creatorEngine;
    private LevelRenderer levelRenderer;

    public MainMenu(CreatorEngine creatorEngine) {
        this.creatorEngine = creatorEngine;
        levelRenderer = new LevelRenderer(creatorEngine, 20, 20);
    }

    public void tick() {
        levelRenderer.tick();
    }

    public void render(Graphics g) {
        levelRenderer.render(g);

        g.drawImage(Assets.MG_OVERLAY_OUTER, 0, 0, creatorEngine.getWidth(), creatorEngine.getHeight(), null);
    }
}
