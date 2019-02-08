package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.overlays.Overlay;
import coffeecatteam.theultimatetile.overlays.OverlayGlub;
import coffeecatteam.theultimatetile.overlays.OverlayPlayerHealth;
import coffeecatteam.theultimatetile.overlays.OverlayPlayerSprint;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public class OverlayManager {

    private List<Overlay> overlays;

    public OverlayManager(TutEngine tutEngine, EntityPlayer player) {
        overlays = new ArrayList<>();

        addOverlay(new OverlayPlayerHealth(tutEngine, player));
        addOverlay(new OverlayPlayerSprint(tutEngine, player));
        addOverlay(new OverlayGlub(tutEngine, player));
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
