package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.game.overlays.Overlay;
import coffeecatteam.theultimatetile.game.overlays.OverlayGlub;
import coffeecatteam.theultimatetile.game.overlays.OverlayPlayerHealth;
import coffeecatteam.theultimatetile.game.overlays.OverlayPlayerSprint;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public class OverlayManager {

    private List<Overlay> overlays;

    public OverlayManager(Engine engine, EntityPlayer player) {
        overlays = new ArrayList<>();

        addOverlay(new OverlayPlayerHealth(engine, player));
        addOverlay(new OverlayPlayerSprint(engine, player));
        addOverlay(new OverlayGlub(engine, player));
    }

    public void update(GameContainer container, int delta) {
        overlays.forEach(overlay -> overlay.update(container, delta));
    }

    public void render(Graphics g) {
        overlays.forEach(overlay -> overlay.render(g));
    }

    public void addOverlay(Overlay overlay) {
        overlays.add(overlay);
    }
}
