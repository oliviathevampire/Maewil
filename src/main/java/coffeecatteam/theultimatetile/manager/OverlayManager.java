package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.overlays.Overlay;
import coffeecatteam.theultimatetile.gfx.overlays.OverlayGlub;
import coffeecatteam.theultimatetile.gfx.overlays.OverlayPlayerHealth;
import coffeecatteam.theultimatetile.gfx.overlays.OverlayPlayerSprint;
import coffeecatteam.theultimatetile.manager.iinterface.IRenderableManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OverlayManager implements IRenderableManager {

    private List<Overlay> overlays;

    public OverlayManager(GameEngine gameEngine, EntityPlayer player) {
        overlays = new ArrayList<>();

        addOverlay(new OverlayPlayerHealth(gameEngine, player));
        addOverlay(new OverlayPlayerSprint(gameEngine, player));
        addOverlay(new OverlayGlub(gameEngine, player));
    }

    @Override
    public void tick() {
        overlays.forEach(overlay -> overlay.tick());
    }

    @Override
    public void render(Graphics g) {
        overlays.forEach(overlay -> overlay.render(g));
    }

    public void addOverlay(Overlay overlay) {
        overlays.add(overlay);
    }
}
