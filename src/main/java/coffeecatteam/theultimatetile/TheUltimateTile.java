package coffeecatteam.theultimatetile;

import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Camera;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.audio.AudioMaster;
import coffeecatteam.theultimatetile.gfx.audio.Sound;
import coffeecatteam.theultimatetile.manager.*;
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.state.StateMenu;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.game.StateSelectGame;
import coffeecatteam.theultimatetile.state.options.OptionsSounds;
import coffeecatteam.theultimatetile.state.options.controls.OptionsControls;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;
import coffeecatteam.theultimatetile.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class TheUltimateTile extends Canvas implements Runnable {

    private static TheUltimateTile theUltimateTile;

    private String[] args;
    private JFrame frame;
    private int width, height;
    public String title;

    private int fps = 0;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    // States
    public StateMenu stateMenu;
    public StateSelectGame stateSelectGame;
    public StateOptions stateOptions;

    // Option states
    public OptionsControls optionsControls;
    public OptionsSounds optionsSpounds;

    private KeyManager keyManager;
    private MouseManager mouseManager;
    private EntityManager entityManager;
    private ItemManager itemManager;

    private Camera camera;
    private World world;

    private WindowManager windowManager;

    private String username;

    public TheUltimateTile(String[] args, String title, int width, int height) {
        this.args = args;
        this.title = title;
        this.width = width;
        this.height = height;

        keyManager = new KeyManager();
        mouseManager = new MouseManager();

        Assets.init();
        createDisplay();

        theUltimateTile = this;
    }

    private void createDisplay() {
        Dimension size = new Dimension(width, height);

        frame = new JFrame(title);
        frame.setSize(size);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setIconImage(Assets.ULTIMATE_TILE[0]);

        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.setMinimumSize(size);
        this.setFocusable(false);

        frame.add(this);
        frame.pack();
    }

    private void init() {
        frame.addKeyListener(keyManager);
        frame.addMouseListener(mouseManager);
        frame.addMouseMotionListener(mouseManager);
        this.addMouseListener(mouseManager);
        this.addMouseMotionListener(mouseManager);
        frame.addWindowListener(windowManager);

        camera = new Camera(this, 0, 0);

        username = hasArgument("-username") ? getArgument("-username") : Utils.getUsername();
        Logger.print("Set username: " + username);

        stateMenu = new StateMenu(this);
        stateSelectGame = new StateSelectGame(this);
        stateOptions = new StateOptions(this);
        optionsControls = new OptionsControls(this);
        optionsSpounds = new OptionsSounds(this);
        State.setState(stateMenu);

        entityManager = new EntityManager(this, new EntityPlayer(this, username));

        itemManager = new ItemManager(this);
        windowManager = new WindowManager(this);

        ItemManager.init();

        // Audio/sound initialized
        AudioMaster.init();
        AudioMaster.setListenerData(0f, 0f, 0f);

        Sound.init();
    }

    private void tick() {
        keyManager.tick();

        if (State.getState() != null)
            State.getState().tick();
    }

    private void render() {
        bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        // Start drawing

        if (State.getState() != null)
            State.getState().render(g);
        if (StateOptions.OPTIONS.fpsCounter())
            renderFPSCounter(g);

        // End drawing
        bs.show();
        g.dispose();
    }

    private void renderFPSCounter(Graphics g) {
        Font font = Assets.FONT_20;
        Text.drawString(g, "FPS: " + fps, 5, 5 + Text.getHeight(g, font), false, false, Color.orange, font);
    }

    @Override
    public void run() {
        init();

        // Background music
        Sound.play(Sound.BG_MUSIC, StateOptions.OPTIONS.getVolumeMusic(), 0f, 0f, 0f, 1f, true);

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                tick();

                // Background music volume update
                Sound.setVolume(Sound.BG_MUSIC, StateOptions.OPTIONS.getVolumeMusic());

                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
                this.fps = ticks;
                ticks = 0;
                timer = 0;
            }
        }

        // Cleanup sounds
        Sound.delete();
        AudioMaster.cleanUp();

        stop();

        Logger.print("Exiting game..");
        System.exit(0);
    }

    public synchronized void start() {
        if (running)
            return;
        running = true;


        thread = new Thread(this);
        thread.setName("Thread-" + title.replace(" ", "_"));
        thread.start();
    }

    public synchronized void stop() {
        if (!running)
            return;
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean hasArgument(String arg) {
        for (String a : args) {
            String regex = ":";
            if (a.contains(regex))
                a = a.split(regex)[0];

            if (a.equals(arg))
                return true;
        }
        return false;
    }

    public String getArgument(String arg) {
        String value = null;
        for (int i = 0; i < args.length; i++) {
            if (hasArgument(arg)) {
                String regex = ":";
                String argName = args[i].split(regex)[0];
                String argValue = null;
                if (args[i].contains(regex))
                    argValue = args[i].split(regex)[1];

                if (argName.equals(arg))
                    value = argValue;
            }
        }
        return value;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public Camera getCamera() {
        return camera;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public JFrame getFrame() {
        return frame;
    }

    public String getUsername() {
        return username;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /*
     * Gets an instance of the game.
     */
    public static TheUltimateTile getTheUltimateTile() {
        return theUltimateTile;
    }
}
