package coffeecatteam.theultimatetile;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.audio.AudioMaster;
import coffeecatteam.theultimatetile.gfx.audio.Sound;
import coffeecatteam.theultimatetile.manager.ItemManager;
import coffeecatteam.theultimatetile.manager.KeyManager;
import coffeecatteam.theultimatetile.manager.MouseManager;
import coffeecatteam.theultimatetile.manager.WindowManager;
import coffeecatteam.theultimatetile.utils.DiscordHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public abstract class Engine extends Canvas implements Runnable {

    protected String[] args;
    protected String title;
    protected int width, height;
    protected JFrame frame;

    protected int fps = 0;

    protected boolean running = false, initOptionsUI = true, playBGMusic = true;
    protected Thread thread;

    protected BufferStrategy bs;
    protected Graphics2D g;

    protected KeyManager keyManager;
    protected MouseManager mouseManager;

    protected WindowManager windowManager;

    public StateOptions stateOptions;

    public Engine(String[] args, String title, int width, int height) {
        this.args = args;
        this.title = title;
        this.width = width;
        this.height = height;

        keyManager = new KeyManager();
        mouseManager = new MouseManager();

        Assets.init();
        createDisplay();
    }

    private void createDisplay() {
        Dimension size = new Dimension(width, height);

        frame = new JFrame(title);
        frame.setSize(size);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setIconImage(Assets.LOGO);

        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.setMinimumSize(size);
        this.setFocusable(false);

        frame.add(this);
        frame.pack();
    }

    protected void init() {
        frame.addKeyListener(keyManager);
        frame.addMouseListener(mouseManager);
        frame.addMouseMotionListener(mouseManager);
        this.addMouseListener(mouseManager);
        this.addMouseMotionListener(mouseManager);
        frame.addWindowListener(windowManager);

        windowManager = new WindowManager(this);

        stateOptions = new StateOptions(this, initOptionsUI);

        ItemManager.init();

        // Audio/sound initialized
        AudioMaster.init();
        AudioMaster.setListenerData(0f, 0f, 0f);

        Sound.init();
    }

    public void tick() {
        keyManager.tick();
    }

    public abstract void render(Graphics2D g);

    private void renderA() {
        bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        g = (Graphics2D) bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);

        render(g);

        // End drawing
        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();
        DiscordHandler.INSTANCE.setup();

        // Background music
        if (playBGMusic)
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
                if (playBGMusic)
                    Sound.setVolume(Sound.BG_MUSIC, StateOptions.OPTIONS.getVolumeMusic());

                renderA();
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

        Logger.print("\nExiting [" + title + "]..");
        System.exit(0);
    }

    protected void renderFPSCounter(Graphics2D g, int fps) {
        Font font = Assets.FONT_20;
        Text.drawString(g, "FPS: " + fps, 5, 5 + Text.getHeight(g, font), false, false, Color.orange, font);
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

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
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

    public void setRunning(boolean r) {
        running = r;
    }

    public Graphics2D newGraphics() {
        return (Graphics2D) bs.getDrawGraphics();
    }

    public String[] getArgs() {
        return args;
    }
}
