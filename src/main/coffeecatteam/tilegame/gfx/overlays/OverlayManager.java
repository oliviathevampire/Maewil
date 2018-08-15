package coffeecatteam.tilegame.gfx.overlays;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.creatures.EntityPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OverlayManager {

    private List<Overlay> overlays;

    public OverlayManager(Handler handler, EntityPlayer player) {
        overlays = new ArrayList<>();

        addOverlay(new OverlayPlayerHealth(handler, player));
        addOverlay(new OverlayPlayerHotbar(handler, player));
        addOverlay(new OverlayPlayerSprint(handler, player));
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
