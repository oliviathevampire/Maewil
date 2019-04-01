package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.overlays.Overlay;
import coffeecatteam.theultimatetile.gfx.overlays.OverlayGlub;
import coffeecatteam.theultimatetile.gfx.overlays.OverlayPlayerHealth;
import coffeecatteam.theultimatetile.gfx.overlays.OverlayPlayerSprint;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

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

    public void update(GameContainer container, StateBasedGame game, int delta) {
        overlays.forEach(overlay -> overlay.update(container, game, delta));
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        overlays.forEach(overlay -> overlay.render(container, game, g));
    }

    public void addOverlay(Overlay overlay) {
        overlays.add(overlay);
    }
}
