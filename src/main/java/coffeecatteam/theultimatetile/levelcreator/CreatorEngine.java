package coffeecatteam.theultimatetile.levelcreator;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.*;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import coffeecatteam.theultimatetile.utils.Logger;
import net.arikia.dev.drpc.DiscordRPC;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class CreatorEngine extends GameEngine implements Runnable {

    private static CreatorEngine creatorEngine;

    private String[] args;
    private JFrame frame;
    private int width, height;
    public String title;

    private int fps = 0;

    private static boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    private KeyManager keyManager;
    private MouseManager mouseManager;

    private StateOptions stateOptions;
    private MainMenu mainMenu;

    private WindowManager windowManager;

    public CreatorEngine(String[] args, String title, int width, int height) {
        super(args, title, width, height, false);
        this.args = args;
        this.title = title;
        this.width = width;
        this.height = height;

        keyManager = new KeyManager();
        mouseManager = new MouseManager();

        createDisplay();

        creatorEngine = this;
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

    private void init() {
        frame.addKeyListener(keyManager);
        frame.addMouseListener(mouseManager);
        frame.addMouseMotionListener(mouseManager);
        this.addMouseListener(mouseManager);
        this.addMouseMotionListener(mouseManager);
        frame.addWindowListener(windowManager);

        stateOptions = new StateOptions(this);

        windowManager = new WindowManager(this);
        mainMenu = new MainMenu(this);
    }

    private void tick() {
        keyManager.tick();
        mainMenu.tick();
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

        mainMenu.render(g);
        renderFPSCounter(g, fps);

        // End drawing
        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();
        DiscordHandler.INSTANCE.setup();

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

        stop();

        Logger.print("Shutting down DiscordHook.");
        DiscordRPC.discordShutdown();

        Logger.print("\nExiting [" + title + "]..");
        System.exit(0);
    }

    @Override
    public synchronized void start() {
        if (running)
            return;
        running = true;

        thread = new Thread(this);
        thread.setName("Thread-" + title.replace(" ", "_"));
        thread.start();
    }

    @Override
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

    public static void setRunning(boolean r) {
        running = r;
    }

    public static CreatorEngine getCreatorEngine() {
        return creatorEngine;
    }
}
