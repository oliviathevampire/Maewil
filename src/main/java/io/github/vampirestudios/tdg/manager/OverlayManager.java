package io.github.vampirestudios.tdg.manager;

import io.github.vampirestudios.tdg.gfx.overlays.Overlay;
import io.github.vampirestudios.tdg.gfx.overlays.PlayerHealthOverlay;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public class OverlayManager {

    private List<Overlay> overlays;

    public OverlayManager(MaewilEngine maewilEngine, PlayerEntity player) {
        overlays = new ArrayList<>();

        addOverlay(new PlayerHealthOverlay(maewilEngine, player));
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
