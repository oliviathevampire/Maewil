package coffeecatteam.theultimatetile.state;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UITitleRender;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlink;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlinkCopyright;
import coffeecatteam.theultimatetile.objs.entities.Entities;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class StateMenu extends State {

    private UIButton btnPlayer;

    public StateMenu(TutEngine tutEngineIn) {
        super(tutEngineIn);

        int yOff = 20;
        uiManager.addObject(new UITitleRender());

        float sizeP = 64;
        float xp = TutLauncher.WIDTH / 4f - sizeP / 2f;
        float yp = TutLauncher.HEIGHT / 2f - sizeP / 2f;
        uiManager.addObject(btnPlayer = new UIButton(tutEngineIn, new Vector2D(xp, yp), tutEngine.getPlayer().getTextures().get("idle"), 4.0f, 0, new ClickListener() {
            @Override
            public void onClick() {
                // TODO: Add entity texture data alt support
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));
        btnPlayer.setRenderBtnBG(false);
        btnPlayer.setRenderBtnHover(false);

        uiManager.addObject(new UIButton(tutEngineIn, true, TutLauncher.HEIGHT / 2 - yOff, "Select Game", new ClickListener() {
            @Override
            public void onClick() {
//                State.setState(((tutEngine) engine).stateSelectGame);
                StateManager.setCurrentState(new StateCreateWorld(tutEngine));
//                State.setState(new StateGame(engine, "./data/saves/Test_World", "Test World"));

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Selecting A Game");
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        uiManager.addObject(new UIButton(tutEngineIn, true, TutLauncher.HEIGHT / 2 - yOff + 80, "Options", new ClickListener() {
            @Override
            public void onClick() {
                StateManager.setCurrentState(tutEngine.stateOptions);

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        uiManager.addObject(new UIButton(tutEngineIn, true, TutLauncher.HEIGHT / 2 - yOff + 160, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                tutEngine.close();
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        int cy = TutLauncher.HEIGHT - 20;
        uiManager.addObject(new UIHyperlinkCopyright(new Vector2D(5, cy)));
        Font font = Assets.FONTS.get("30");
        String text = "Credits";
        int height = (int) Text.getHeight(text, font);
        uiManager.addObject(new UIHyperlink(new Vector2D(TutLauncher.WIDTH - 5 - Text.getWidth(text, font), cy - 10), height, text, font, new ClickListener() {
            @Override
            public void onClick() {
                StateManager.setCurrentState(tutEngine.stateCredits);

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

        Image ultimate = Entities.ULTIMATE.getTexture("main");
        float sizeU = 96;
        ultimate.draw((TutLauncher.WIDTH / 4f) * 3 - sizeU / 2f, TutLauncher.HEIGHT / 2f - sizeU / 2f, sizeU, sizeU);
    }
}
