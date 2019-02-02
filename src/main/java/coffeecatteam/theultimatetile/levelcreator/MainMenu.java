package coffeecatteam.theultimatetile.levelcreator;

import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class MainMenu {

    private CreatorEngine creatorEngine;
    private LevelRenderer levelRenderer;

    public MainMenu(CreatorEngine creatorEngine) {
        this.creatorEngine = creatorEngine;
        levelRenderer = new LevelRenderer(creatorEngine, 20, 20);
    }

    public void update(GameContainer container, int delta) {
        levelRenderer.update(container, delta);
    }

    public void render(Graphics g) {
        Assets.MG_OVERLAY_INNER_MID.draw(0, 0, creatorEngine.getWidth(), creatorEngine.getHeight());

        levelRenderer.render(g);

        Assets.MG_OVERLAY_OUTER.draw(0, 0, creatorEngine.getWidth(), creatorEngine.getHeight());
        Font font = Assets.FONTS.get("20");
        String tlc = "Level Creator";
        Text.drawString(g, tlc, 10, Text.getHeight(tlc, font) + 6, false, true, Color.white, font);
        String tFPS = "FPS: " + creatorEngine.getFps();
        Text.drawString(g, tFPS, 10, 100 + Text.getHeight(tFPS, font), false, false, Color.white, font);

        levelRenderer.postRender(g);
    }
}
