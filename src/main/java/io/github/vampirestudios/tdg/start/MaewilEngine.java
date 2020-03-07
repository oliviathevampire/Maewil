package io.github.vampirestudios.tdg.start;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Animation;
import io.github.vampirestudios.tdg.gfx.Camera;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.manager.EntityManager;
import io.github.vampirestudios.tdg.manager.InventoryManager;
import io.github.vampirestudios.tdg.manager.KeyManager;
import io.github.vampirestudios.tdg.objs.entities.Entities;
import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.Tiles;
import io.github.vampirestudios.tdg.screen.CreditsScreen;
import io.github.vampirestudios.tdg.screen.ScreenManager;
import io.github.vampirestudios.tdg.screen.TitleScreen;
import io.github.vampirestudios.tdg.screen.UITestingScreen;
import io.github.vampirestudios.tdg.screen.game.WorldListScreen;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import io.github.vampirestudios.tdg.screen.options.SoundOptions;
import io.github.vampirestudios.tdg.screen.options.controls.ControlOptions;
import io.github.vampirestudios.tdg.utils.Colors;
import io.github.vampirestudios.tdg.utils.DiscordHandler;
import io.github.vampirestudios.tdg.world.World;
import org.json.simple.parser.ParseException;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.mini2Dx.miniscript.core.ScriptBindings;
import org.mini2Dx.miniscript.core.exception.InsufficientCompilersException;
import org.mini2Dx.miniscript.lua.LuaGameScriptingEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MaewilEngine extends BasicGameState {

    public static MaewilEngine INSTANCE;
    private int fps;

    /*
     * Mouse & keyboard values
     */
    private Vector2D mousePos;
    private boolean leftPressed, rightPressed;
    private boolean leftDown, rightDown;

    private KeyManager keyManager;
    private char keyJustPressed = Character.UNASSIGNED;

    private boolean playBGMusic = true;
    private boolean close = false;

    // States
    public TitleScreen stateMenu;
    public WorldListScreen worldListScreen;
    public CreditsScreen stateCredits;

    // Option states
    public OptionsScreen stateOptions;
    public ControlOptions controlOptions;
    public SoundOptions optionsSpounds;

    private EntityManager entityManager;
    private InventoryManager inventoryManager;

    private Camera camera;
    private World world;

    private LuaGameScriptingEngine luaGameScriptingEngine;
    private ScriptBindings scriptBindings;

    public MaewilEngine() {
        INSTANCE = this;
    }

    @Override
    public int getID() {
        return MaewilLauncher.ID_GAME;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        container.setDefaultFont(Assets.FONTS.get("10"));
        container.setMouseCursor(Assets.GUI_CURSOR, 0, 0);

        try {
            entityManager = new EntityManager(this);

            Items.init(this);
            Tiles.init(this);
            Entities.init(this);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        keyManager = new KeyManager();
        stateOptions = new OptionsScreen(this, true);

        DiscordHandler.INSTANCE.setup();
        camera = new Camera(this, 0, 0);

        stateMenu = new TitleScreen(this);
        worldListScreen = new WorldListScreen(this);
        stateCredits = new CreditsScreen(this);

        controlOptions = new ControlOptions(this);
        optionsSpounds = new SoundOptions(this);

        if (ArgUtils.hasArgument("-uiTest"))
            ScreenManager.setCurrentScreen(new UITestingScreen(this));
        else
            ScreenManager.setCurrentScreen(stateMenu);

        inventoryManager = new InventoryManager(this);

        luaGameScriptingEngine = new LuaGameScriptingEngine();
        scriptBindings = new ScriptBindings();

        scriptBindings.put("player", getPlayer());
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        if (close) container.exit();
        keyManager.update(container, game, delta);

        for (Animation anim : Animation.ANIMATIONS) {
            anim.update();
        }

        /*
         * Mouse values
         */
        this.mousePos = new Vector2D(container.getInput().getMouseX(), container.getInput().getMouseY());
        this.leftPressed = container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);
        this.rightPressed = container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON);
        this.leftDown = container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
        this.rightDown = container.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);

        this.fps = container.getFPS();
        boolean vSync = OptionsScreen.OPTIONS.vSync();
        if (container.isVSyncRequested() != vSync)
            container.setVSync(vSync);

        // Background music
        /*if (playBGMusic) {
            if (Sounds.BG_MUSIC.playing())
                Sounds.BG_MUSIC.setVolume(OptionsScreen.OPTIONS.getVolumeMusic());
            else {
                Sounds.BG_MUSIC.loop();
                Sounds.BG_MUSIC.play();
            }
        }*/

        ScreenManager.getCurrentScreen().update(container, game, delta);

        /*
         * Take a screenshot
         */
        if (container.getInput().isKeyPressed(Input.KEY_F2))
            takeScreenshot();
        luaGameScriptingEngine.update(delta);

        if(container.getInput().isKeyPressed(Input.KEY_T)) {
            try {
                luaGameScriptingEngine.invokeScript("player.moveTo(50, 50).waitForCompletion();", scriptBindings);
            } catch (InsufficientCompilersException e) {
                e.printStackTrace();
            }
        }
    }

    private void takeScreenshot() {
        try {
            GL11.glReadBuffer(GL11.GL_FRONT);
            int colors = 4;

            ByteBuffer buf = BufferUtils.createByteBuffer(MaewilLauncher.WIDTH * MaewilLauncher.HEIGHT * colors);
            GL11.glReadPixels(0, 0, MaewilLauncher.WIDTH, MaewilLauncher.HEIGHT, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

            BufferedImage image = new BufferedImage(MaewilLauncher.WIDTH, MaewilLauncher.HEIGHT, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < MaewilLauncher.WIDTH; x++) {
                for (int y = 0; y < MaewilLauncher.HEIGHT; y++) {
                    int i = (x + (y * MaewilLauncher.WIDTH)) * colors;

                    int r = buf.get(i) & 0xFF;
                    int g = buf.get(i + 1) & 0xFF;
                    int b = buf.get(i + 2) & 0xFF;

                    image.setRGB(x, MaewilLauncher.HEIGHT - (y + 1), Colors.rgb(r, g, b));
                }
            }

            File dir = new File("data/screenshots");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".png");
            ImageIO.write(image, "png", file);

            MaewilLauncher.LOGGER.info("Saved screenshot to " + file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        g.clear();
        Assets.MISSING_TEXTURE.draw(0, 0, MaewilLauncher.WIDTH, MaewilLauncher.HEIGHT);

        if (ScreenManager.getCurrentScreen() != null) {
            ScreenManager.getCurrentScreen().render(container, game, g);
            ScreenManager.getCurrentScreen().postRender(container, game, g);
        }
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

    public String getKeyJustPressed() {
        if (keyJustPressed == Character.UNASSIGNED)
            return "";
        else {
            String c = String.valueOf(this.keyJustPressed);
            this.keyJustPressed = Character.UNASSIGNED;
            return c;
        }
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

    public PlayerEntity getPlayer() {
        return entityManager.getPlayer();
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public LuaGameScriptingEngine getLuaGameScriptingEngine() {
        return luaGameScriptingEngine;
    }

    public ScriptBindings getScriptBindings() {
        return scriptBindings;
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
}
