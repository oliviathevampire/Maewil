package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlink;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlinkCopyright;
import coffeecatteam.theultimatetile.utils.DiscordHandler;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class StateMenu extends State {

    public StateMenu(GameEngine gameEngineIn) {
        super(gameEngineIn);
        init();

        int yOff = 20;

        uiManager.addObject(new UIButton(gameEngineIn, true, engine.getHeight() / 2 - yOff, "Select Game", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(((GameEngine) engine).stateSelectGame);

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Selecting A Game");
            }

            @Override
            public void update(GameContainer container, int delta) {
            }
        }));

        uiManager.addObject(new UIButton(gameEngineIn, true, engine.getHeight() / 2 - yOff + 80, "Options", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(engine.stateOptions);

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void update(GameContainer container, int delta) {
            }
        }));

        uiManager.addObject(new UIButton(gameEngineIn, true, engine.getHeight() / 2 - yOff + 160, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                engine.close();
            }

            @Override
            public void update(GameContainer container, int delta) {
            }
        }));

        int cy = engine.getHeight() - 20;
        uiManager.addObject(new UIHyperlinkCopyright(new Vector2D(5, cy)));
        Font font = Assets.FONTS.get("30");
        String text = "Credits";
        int height = Text.getHeight(text, font);
        uiManager.addObject(new UIHyperlink(new Vector2D(engine.getWidth() - 5 - Text.getWidth(text, font), cy - 10), height, text, font, new ClickListener() {
            @Override
            public void onClick() {
                State.setState(((GameEngine) engine).stateCredits);

                DiscordHandler.INSTANCE.updatePresence("Viewing credits");
            }

            @Override
            public void update(GameContainer container, int delta) {
            }
        }));
    }

    @Override
    public void update(GameContainer container, int delta) {
        uiManager.update(container, delta);
    }

    @Override
    public void render(Graphics g) {
        Assets.BACKGROUND.draw(0, 0, engine.getWidth(), engine.getHeight());
        uiManager.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        Assets.TITLE.draw(engine.getWidth() / 2f - w / 2f, 20, w, h);
    }
}
