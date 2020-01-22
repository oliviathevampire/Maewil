package io.github.vampirestudios.tdg.screen;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.WidgetTitleRender;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.gfx.ui.hyperlink.WidgetHyperlink;
import io.github.vampirestudios.tdg.gfx.ui.hyperlink.WidgetHyperlinkCopyright;
import io.github.vampirestudios.tdg.screen.options.CharacterCustomizationScreen;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.DiscordHandler;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class TitleScreen extends Screen {

    private WidgetButton btnPlayer;

    public TitleScreen(MaewilEngine maewilEngineIn) {
        super(maewilEngineIn);

        int yOff = 20;
        uiManager.addObject(new WidgetTitleRender());

        float sizeP = 64;
        float xp = MaewilLauncher.WIDTH / 4f - sizeP / 2f;
        float yp = MaewilLauncher.HEIGHT / 2f - sizeP / 2f;
        uiManager.addObject(btnPlayer = new WidgetButton(maewilEngineIn, new Vector2D(xp, yp), maewilEngine.getPlayer().getTextures().get("idle"), 4.0f, 0, new ClickListener() {
            @Override
            public void onClick() {
                // TODO: Add entity texture data alt support
                ScreenManager.setCurrentScreen(new CharacterCustomizationScreen(maewilEngine));
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));
        btnPlayer.setRenderBtnBG(false);
        btnPlayer.setRenderBtnHover(false);

        uiManager.addObject(new WidgetButton(maewilEngineIn, true, MaewilLauncher.HEIGHT / 2 - yOff, "Select Game", new ClickListener() {
            @Override
            public void onClick() {
//                ScreenManager.setCurrentScreen(maewilEngineIn.stateSelectGame);
                ScreenManager.setCurrentScreen(new CreateWorldScreen(maewilEngine));
//                String worldName = "New_World_" + NumberUtils.getRandomInt(1000);
//                ScreenManager.setCurrentScreen(new GameScreen(tutEngineIn, "./data/saves/" + worldName, worldName));

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Selecting A Game");
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        uiManager.addObject(new WidgetButton(maewilEngineIn, true, MaewilLauncher.HEIGHT / 2 - yOff + 80, "Options", new ClickListener() {
            @Override
            public void onClick() {
                ScreenManager.setCurrentScreen(maewilEngine.stateOptions);

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        uiManager.addObject(new WidgetButton(maewilEngineIn, true, MaewilLauncher.HEIGHT / 2 - yOff + 160, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                maewilEngine.close();
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        int cy = MaewilLauncher.HEIGHT - 20;
        uiManager.addObject(new WidgetHyperlinkCopyright(new Vector2D(5, cy)));
        Font font = Assets.FONTS.get("30");
        String text = "Credits";
        int height = (int) Text.getHeight(text, font);
        uiManager.addObject(new WidgetHyperlink(new Vector2D(MaewilLauncher.WIDTH - 5 - Text.getWidth(text, font), cy - 10), height, text, font, new ClickListener() {
            @Override
            public void onClick() {
                ScreenManager.setCurrentScreen(maewilEngine.stateCredits);

                DiscordHandler.INSTANCE.updatePresence("Viewing credits");
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        uiManager.update(container, game, delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        this.renderBG(container, game, g);
        uiManager.render(container, game, g);

        /*Image ultimate = Entities.ULTIMATE.getTexture("main");
        float sizeU = 96;
        ultimate.draw((TutLauncher.WIDTH / 4f) * 3 - sizeU / 2f, TutLauncher.HEIGHT / 2f - sizeU / 2f, sizeU, sizeU);*/
    }
}
