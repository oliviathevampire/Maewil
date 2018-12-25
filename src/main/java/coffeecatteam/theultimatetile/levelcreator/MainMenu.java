package coffeecatteam.theultimatetile.levelcreator;

import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

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

    public void render(Graphics2D g) {
        g.drawImage(Assets.MG_OVERLAY_INNER_MID, 0, 0, creatorEngine.getWidth(), creatorEngine.getHeight(), null);

        levelRenderer.render(g);

        g.drawImage(Assets.MG_OVERLAY_OUTER, 0, 0, creatorEngine.getWidth(), creatorEngine.getHeight(), null);
        Font font = Assets.FONTS.get("20");
        Text.drawString(g, "Level Creator", 10, Text.getHeight(g, font) + 6, false, true, Color.white, font);

        levelRenderer.postRender(g);
    }
}
