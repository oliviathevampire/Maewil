package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.game.state.State;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.game.state.game.StateGame;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.theultimatetile.manager.KeyManager;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.newdawn.slick.*;

public abstract class Engine extends BasicGame {

    protected CatLogger logger;

    private static Engine engine;

    protected String[] args;
    protected int width, height, fps;

    /*
     * Mouse & keyboard values
     */
    protected int mouseX, mouseY;
    protected boolean leftPressed, rightPressed;
    protected boolean leftDown, rightDown;

    protected KeyManager keyManager;

    protected boolean initOptionsUI = true, playBGMusic = true, close = false;

    public StateOptions stateOptions;


    public Engine(String[] args, String title, int width, int height) {
        super(title);
        this.args = args;
        this.width = width;
        this.height = height;
        this.logger = new CatLogger(title);
        engine = this;
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        Assets.init(logger);
        container.setShowFPS(false);
        container.setDefaultFont(Assets.FONTS.get("10"));
        container.setMouseCursor(Assets.CURSOR, 0, 0);

        keyManager = new KeyManager();
        stateOptions = new StateOptions(this, initOptionsUI);

        ItemManager.init();
        Sounds.init();

        DiscordHandler.INSTANCE.setup();

        Sounds.BG_MUSIC.setVolume(StateOptions.OPTIONS.getVolumeMusic());
        Sounds.BG_MUSIC.loop();
        Sounds.BG_MUSIC.play();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (close) container.exit();
        keyManager.update(container, delta);

        /*
         * Mouse values
         */
        this.mouseX = container.getInput().getMouseX();
        this.mouseY = container.getInput().getMouseY();
        this.leftPressed = container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);
        this.rightPressed = container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON);
        this.leftDown = container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
        this.rightDown = container.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);

        this.fps = container.getFPS();

        // Background music
        if (playBGMusic) {
            if (Sounds.BG_MUSIC.playing())
                Sounds.BG_MUSIC.setVolume(StateOptions.OPTIONS.getVolumeMusic());
            else {
                Sounds.BG_MUSIC.loop();
                Sounds.BG_MUSIC.play();
            }
        }
    }

    public abstract void rendera(GameContainer container, Graphics g) throws SlickException;

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.clear();
        rendera(container, g);
    }

    @Override
    public boolean closeRequested() {
        if (playBGMusic && Sounds.BG_MUSIC.playing())
            Sounds.BG_MUSIC.stop();
        logger.print("Shutting down [" + this.getTitle() + "] engine!");
        if (State.getState() instanceof StateGame) {
            logger.print("Saving world!");
            ((StateGame) State.getState()).saveWorld();
            logger.print("World saved!");
        }

        logger.print("Exiting [" + this.getTitle() + "]..");
        return true;
    }

    @Override
    public void keyPressed(int key, char c) {
        keyManager.keyPressed(key, c);
    }

    public boolean keyJustPressed(int key) {
        return keyManager.keyJustPressed(key);
    }

    @Override
    public void keyReleased(int key, char c) {
        keyManager.keyReleased(key, c);
    }

    protected void renderFPSCounter(Graphics g) {
        Font font = Assets.FONTS.get("20");
        String t = "FPS: " + this.fps;
        Text.drawString(g, t, 5, 5 + Text.getHeight(t, font), false, false, Color.orange, font);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String[] getArgs() {
        return args;
    }

    public static Engine getEngine() {
        return engine;
    }

    public CatLogger getLogger() {
        return logger;
    }

    public int getFps() {
        return fps;
    }

    public void close() {
        this.close = true;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isLeftDown() {
        return leftDown;
    }

    public boolean isRightDown() {
        return rightDown;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
}
