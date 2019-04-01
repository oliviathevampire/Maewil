package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Camera;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.assets.Sounds;
import coffeecatteam.theultimatetile.manager.EntityManager;
import coffeecatteam.theultimatetile.manager.InventoryManager;
import coffeecatteam.theultimatetile.manager.KeyManager;
import coffeecatteam.theultimatetile.objs.entities.Entities;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;
import coffeecatteam.theultimatetile.state.StateCredits;
import coffeecatteam.theultimatetile.state.StateManager;
import coffeecatteam.theultimatetile.state.StateMenu;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.game.StateSelectGame;
import coffeecatteam.theultimatetile.state.options.OptionsSounds;
import coffeecatteam.theultimatetile.state.options.controls.OptionsControls;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import coffeecatteam.theultimatetile.world.World;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TutEngine extends BasicGameState {

    private static TutEngine tutEngine;

    private String[] args;
    private int fps;

    /*
     * Mouse & keyboard values
     */
    private Vector2D mousePos;
    private boolean leftPressed, rightPressed;
    private boolean leftDown, rightDown;

    private KeyManager keyManager;
    private char keyJustPressed = Character.UNASSIGNED;

    private boolean initOptionsUI = true, playBGMusic = true, close = false;

    // States
    public StateMenu stateMenu;
    public StateSelectGame stateSelectGame;
    public StateCredits stateCredits;

    // Option states
    public StateOptions stateOptions;
    public OptionsControls optionsControls;
    public OptionsSounds optionsSpounds;

    private EntityManager entityManager;
    private InventoryManager inventoryManager;

    private Camera camera;
    private World world;

    public TutEngine(String[] args) {
        this.args = args;
        tutEngine = this;
    }

    @Override
    public int getID() {
        return TutLauncher.ID_GAME;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        container.setDefaultFont(Assets.FONTS.get("10"));
        container.setMouseCursor(Assets.CURSOR, 0, 0);

        try {
            entityManager = new EntityManager(this);

            Items.init(this);
            Tiles.init(this);
            Entities.init(this);
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

        inventoryManager = new InventoryManager(this);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
//          TagBase.TEST();
        if (close) container.exit();
        keyManager.update(container, delta);

        /*
         * Mouse values
         */
        this.mousePos = new Vector2D(container.getInput().getMouseX(), container.getInput().getMouseY());
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

        StateManager.getCurrentState().update(container, delta);

        /*
         * Take a screenshot
         */
        if (container.getInput().isKeyPressed(Input.KEY_F2))
            takeScreenshot(container);
    }

    public void takeScreenshot(GameContainer container) throws SlickException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        String dateAndTime = dateFormat.format(new Date());
        String filename = "screenshots/" + dateAndTime + ".png";
        File file = new File(filename);
        file.getParentFile().mkdirs();

        Image target = new Image(TutLauncher.WIDTH, TutLauncher.HEIGHT);
        container.getGraphics().copyArea(target, 0, 0);
        ImageOut.write(target, filename);
        target.destroy();

        TutLauncher.LOGGER.info("Screenshot saved to [" + filename + "]");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.clear();
        Assets.MISSING_TEXTURE.draw(0, 0, TutLauncher.WIDTH, TutLauncher.HEIGHT);

        if (StateManager.getCurrentState() != null) {
            StateManager.getCurrentState().render(g);
            StateManager.getCurrentState().postRender(g);
        }
        if (StateOptions.OPTIONS.fpsCounter())
            renderFPSCounter(g);
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

    public String[] getArgs() {
        return args;
    }

    public int getFps() {
        return fps;
    }

    public void close() {
        this.close = true;
    }

    public Vector2D getMousePos() {
        return mousePos;
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

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean isPlayBGMusic() {
        return playBGMusic;
    }

    /*
     * Get an instance of this
     */
    public static TutEngine getTutEngine() {
        return tutEngine;
    }
}
