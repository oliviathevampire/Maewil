package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.state.StateCredits;
import coffeecatteam.theultimatetile.state.StateManager;
import coffeecatteam.theultimatetile.state.StateMenu;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.game.StateGame;
import coffeecatteam.theultimatetile.state.game.StateSelectGame;
import coffeecatteam.theultimatetile.state.options.OptionsSounds;
import coffeecatteam.theultimatetile.state.options.controls.OptionsControls;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;
import coffeecatteam.theultimatetile.world.World;
import coffeecatteam.theultimatetile.gfx.Camera;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.manager.EntityManager;
import coffeecatteam.theultimatetile.manager.InventoryManager;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.manager.KeyManager;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TutEngine extends BasicGame {

    protected CatLogger logger;

    private static TutEngine tutEngine;

    protected String[] args;
    protected int width, height, fps;

    /*
     * Mouse & keyboard values
     */
    protected int mouseX, mouseY;
    protected boolean leftPressed, rightPressed;
    protected boolean leftDown, rightDown;

    protected KeyManager keyManager;
    private char keyJustPressed = Character.UNASSIGNED;

    protected boolean initOptionsUI = true, playBGMusic = true, close = false;

    // States
    public StateMenu stateMenu;
    public StateSelectGame stateSelectGame;
    public StateCredits stateCredits;

    // Option states
    public StateOptions stateOptions;
    public OptionsControls optionsControls;
    public OptionsSounds optionsSpounds;

    private EntityManager entityManager;
    private Items items;
    private InventoryManager inventoryManager;

    private Camera camera;
    private World world;

    public TutEngine(String[] args, String title, int width, int height) {
        super(title);
        this.args = args;
        this.width = width;
        this.height = height;
        this.logger = new CatLogger("Client-Thread");
        tutEngine = this;
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        logger.print("Fullscreen is set to [" + container.isFullscreen() + "]");

        Assets.init();
        container.setShowFPS(false);
        container.setDefaultFont(Assets.FONTS.get("10"));
        container.setMouseCursor(Assets.CURSOR, 0, 0);

        Sounds.init();

        try {
            Items.init(this);
            Tiles.init(this);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        keyManager = new KeyManager();
        stateOptions = new StateOptions(this, initOptionsUI);

        DiscordHandler.INSTANCE.setup();
        camera = new Camera(this, 0, 0);

        stateMenu = new StateMenu(this);
        stateSelectGame = new StateSelectGame(this);
        stateCredits = new StateCredits(this);

        optionsControls = new OptionsControls(this);
        optionsSpounds = new OptionsSounds(this);
        StateManager.setCurrentState(stateMenu);

        entityManager = new EntityManager(this, new EntityPlayer(this, ""));

        items = new Items(this);

        inventoryManager = new InventoryManager(this);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
//        TagBase.TEST();
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

        if (StateManager.getCurrentState() != null)
            StateManager.getCurrentState().update(container, delta);

        /*
         * Take a screenshot
         */
        if (container.getInput().isKeyPressed(Input.KEY_F2)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
            String dateAndTime = dateFormat.format(new Date());
            String filename = "sreenshots/" + dateAndTime + ".png";
            File file = new File(filename);
            file.getParentFile().mkdirs();

            Image target = new Image(container.getWidth(), container.getHeight());
            container.getGraphics().copyArea(target, 0, 0);
            ImageOut.write(target, filename);
            target.destroy();

            logger.print("Screenshot saved to [" + filename + "]");
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.clear();
        Assets.MISSING_TEXTURE.draw(0, 0, width, height);

        if (StateManager.getCurrentState() != null) {
            StateManager.getCurrentState().render(g);
            StateManager.getCurrentState().postRender(g);
        }
        if (StateOptions.OPTIONS.fpsCounter())
            renderFPSCounter(g);
    }

    @Override
    public boolean closeRequested() {
        if (playBGMusic && Sounds.BG_MUSIC.playing())
            Sounds.BG_MUSIC.stop();
        logger.print("Shutting down [" + this.getTitle() + "] engine!");
        if (StateManager.getCurrentState() instanceof StateGame) {
            logger.print("Saving world!");
            ((StateGame) StateManager.getCurrentState()).saveWorld(true);
            logger.print("World saved!");
        }

        logger.print("Exiting [" + this.getTitle() + "]..");
        return true;
    }

    @Override
    public void keyPressed(int key, char c) {
        this.keyJustPressed = c;
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
        Font font = Assets.FONTS.get("25-bold");
        String t = "FPS: " + this.fps;
        Text.drawString(g, t, 5, 5 + Text.getHeight(t, font), false, false, Color.orange, font);
    }

    public String getKeyJustPressed() {
        if (keyJustPressed == Character.UNASSIGNED)
            return "";
        else {
            String c = String.valueOf(this.keyJustPressed);
            this.keyJustPressed = Character.UNASSIGNED;
            return c;
        }
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

    public Camera getCamera() {
        return camera;
    }

    public String getUsername() {
        return this.entityManager.getPlayer().getUsername();
    }

    public void setUsername(String username) {
        this.entityManager.getPlayer().setUsername(username);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityPlayer getPlayer() {
        return entityManager.getPlayer();
    }

    public Items getItems() {
        return items;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    /*
     * Get an instance of this
     */
    public static TutEngine getTutEngine() {
        return tutEngine;
    }
}
