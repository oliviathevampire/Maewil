package io.github.vampirestudios.tdg.manager;

import io.github.vampirestudios.tdg.gfx.overlays.Overlay;
import io.github.vampirestudios.tdg.gfx.overlays.PlayerHealthOverlay;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

public class OverlayManager {

    private List<Overlay> overlays;

    public OverlayManager(MaewilEngine maewilEngine, PlayerEntity player) {
        overlays = new ArrayList<>();

        addOverlay(new PlayerHealthOverlay(maewilEngine, player));
    }

    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        overlays.forEach(overlay -> overlay.update(container, delta));
    }

    public void render(GameContainer container, Graphics g) {
        overlays.forEach(overlay -> overlay.render(container, g));
    }

    public void addOverlay(Overlay overlay) {
        overlays.add(overlay);
    }
}
