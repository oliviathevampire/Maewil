package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.overlays.Overlay;
import coffeecatteam.theultimatetile.gfx.overlays.OverlayGlub;
import coffeecatteam.theultimatetile.gfx.overlays.OverlayPlayerHealth;
import coffeecatteam.theultimatetile.gfx.overlays.OverlayPlayerSprint;
import coffeecatteam.theultimatetile.manager.iinterface.IRenderabelManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OverlayManager implements IRenderabelManager {

    private List<Overlay> overlays;

    public OverlayManager(TheUltimateTile theUltimateTile, EntityPlayer player) {
        overlays = new ArrayList<>();

        addOverlay(new OverlayPlayerHealth(theUltimateTile, player));
        addOverlay(new OverlayPlayerSprint(theUltimateTile, player));
        addOverlay(new OverlayGlub(theUltimateTile, player));
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
