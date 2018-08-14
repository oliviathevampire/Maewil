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

        overlays.add(new OverlayPlayerHealth(handler, player));
        overlays.add(new OverlayPlayerHotbar(handler, player));
    }

    public void tick() {
        overlays.forEach(overlay -> overlay.tick());
    }

    public void render(Graphics g) {
        overlays.forEach(overlay -> overlay.render(g));
    }
}
