package io.github.vampirestudios.tdg.screen;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.gfx.ui.hyperlink.WidgetHyperlink;
import io.github.vampirestudios.tdg.gfx.ui.hyperlink.WidgetHyperlinkCopyright;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.DiscordHandler;
import org.newdawn.slick.Font;

public class TitleScreen extends Screen {

    private WidgetButton btnPlayer;

    public TitleScreen(MaewilEngine maewilEngineIn) {
        super(maewilEngineIn);

        int yOff = 20;
//        uiManager.addObject(new WidgetTitleRender());

        float sizeP = 64;
        float xp = MaewilLauncher.WIDTH / 4f - sizeP / 2f;
        float yp = MaewilLauncher.HEIGHT / 2f - sizeP / 2f;
        /*uiManager.addObject(btnPlayer = new WidgetButton(maewilEngineIn, new Vector2D(xp, yp), maewilEngine.getPlayer().getTextures().get("idle"), 4.0f, 0, new ClickListener() {
            @Override
            public void onClick() {
                // TODO: Add entity texture data alt support
                ScreenManager.setCurrentScreen(new CharacterCustomizationScreen(maewilEngine));
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));*/
//        btnPlayer.setRenderBtnBG(false);
//        btnPlayer.setRenderBtnHover(false);

        uiManager.addObject(new WidgetButton(maewilEngineIn, true, MaewilLauncher.HEIGHT / 2 - yOff, "Select World", new ClickListener() {
            @Override
            public void onClick() {
//                ScreenManager.setCurrentScreen(maewilEngineIn.worldListScreen);
                ScreenManager.setCurrentScreen(new GenerateRandomWorldScreen(maewilEngine));

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Selecting a world");
            }

            @Override
            public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
            }
        }));

        uiManager.addObject(new WidgetButton(maewilEngineIn, true, MaewilLauncher.HEIGHT / 2 - yOff + 80, "Options", new ClickListener() {
            @Override
            public void onClick() {
                ScreenManager.setCurrentScreen(maewilEngine.stateOptions);

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
            }
        }));

        uiManager.addObject(new WidgetButton(maewilEngineIn, true, MaewilLauncher.HEIGHT / 2 - yOff + 160, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                maewilEngine.close();
            }

            @Override
            public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
            }
        }));

        int cy = MaewilLauncher.HEIGHT - 20;
        uiManager.addObject(new WidgetHyperlinkCopyright(new Vector2D(5, cy)));
        Font font = Assets.FONTS.get("30");
        String text = "Credits";
        int height = (int) Text.getHeight(text, font);
        uiManager.addObject(new WidgetHyperlink(new Vector2D(MaewilLauncher.WIDTH - 5 - Text.getWidth(text, font), cy), height, text, font, new ClickListener() {
            @Override
            public void onClick() {
                ScreenManager.setCurrentScreen(maewilEngine.stateCredits);

                DiscordHandler.INSTANCE.updatePresence("Viewing credits");
            }

            @Override
            public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
            }
        }));
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        uiManager.update(container, delta);
    }

    @Override
    public void render(org.mini2Dx.core.game.GameContainer container, org.mini2Dx.core.graphics.Graphics g) {
        this.renderBG(container, g);
        uiManager.render(container, g);

        /*Image ultimate = Entities.ULTIMATE.getTexture("main");
        float sizeU = 96;
        ultimate.draw((TutLauncher.WIDTH / 4f) * 3 - sizeU / 2f, TutLauncher.HEIGHT / 2f - sizeU / 2f, sizeU, sizeU);*/
    }

}
