package io.github.vampirestudios.tdg.gfx.ui.hyperlink;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import org.newdawn.slick.Font;

import java.awt.*;
import java.net.URI;

public class WidgetHyperlinkCopyright extends WidgetHyperlink {

    private static String text = "Copyright (C) Vampire Studios 2020";
    private static Font font = Assets.FONTS.get("20");

    public WidgetHyperlinkCopyright(Vector2D position) {
        super(position, Text.getHeight(text, font), text, font, new ClickListener() {
            @Override
            public void onClick() {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI link = new URI("https://github.com/oliviathevampire/TheUltimateTile/blob/master/LICENSE.md");
                    desktop.browse(link);
                } catch (Exception e) {
                    MaewilLauncher.LOGGER.error(e);
                }
            }

            @Override
            public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
            }
        });
    }
}
