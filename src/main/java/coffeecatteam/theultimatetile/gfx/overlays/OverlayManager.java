package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.player.EntityPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OverlayManager {

    private List<Overlay> overlays;

    public OverlayManager(TheUltimateTile theUltimateTile, EntityPlayer player) {
        overlays = new ArrayList<>();

        addOverlay(new OverlayPlayerHealth(theUltimateTile, player));
        addOverlay(new OverlayPlayerSprint(theUltimateTile, player));
        addOverlay(new OverlayGlub(theUltimateTile, player));
    }

    public void tick() {
        overlays.forEach(overlay -> overlay.tick());
    }

    public void render(Graphics g) {
        overlays.forEach(overlay -> overlay.render(g));
    }

    public void addOverlay(Overlay overlay) {
        overlays.add(overlay);
    }
}
