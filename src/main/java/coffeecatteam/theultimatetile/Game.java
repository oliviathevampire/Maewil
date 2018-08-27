package coffeecatteam.theultimatetile;

import coffeecatteam.theultimatetile.entities.EntityManager;
import coffeecatteam.theultimatetile.entities.player.EntityPlayer;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Camera;
import coffeecatteam.theultimatetile.input.KeyManager;
import coffeecatteam.theultimatetile.input.MouseManager;
import coffeecatteam.theultimatetile.items.ItemManager;
import coffeecatteam.theultimatetile.net.Client;
import coffeecatteam.theultimatetile.net.Server;
import coffeecatteam.theultimatetile.net.packet.Packet00Login;
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.state.StateGame;
import coffeecatteam.theultimatetile.state.StateMenu;
import coffeecatteam.theultimatetile.state.StateMenuMultiplayer;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private JFrame frame;
    private int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    // States
    //public StateGame stateGame;
    public StateMenu stateMenu;
    public StateMenuMultiplayer stateMenuMultiplayer;

    private KeyManager keyManager;
    private MouseManager mouseManager;
    private EntityManager entityManager;
    private ItemManager itemManager;

    private Camera camera;

    private Handler handler;

    private Client client;
    private Server server;
    private String username;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        keyManager = new KeyManager();
        mouseManager = new MouseManager();

        Assets.init();
        createDisplay();

        handler = new Handler(this);
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

        camera = new Camera(handler, 0, 0);

        username = Utils.getUsername();
        //stateGame = new StateGame(handler).reset();
        stateMenu = new StateMenu(handler);
        stateMenuMultiplayer = new StateMenuMultiplayer(handler, username);
        State.setState(stateMenu);

        entityManager = new EntityManager(handler, new EntityPlayerMP(handler, username, null, -1, true));

        itemManager = new ItemManager(handler);
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

        // End drawing
        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();

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
                frame.setTitle(title + " - " + username + " - FPS: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
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

    public Client getClient() {
        return client;
    }

    public Server getServer() {
        return server;
    }

    public synchronized void start(String[] args) {
        if (running)
            return;
        running = true;

        if (args.length > 0) { // JOptionPane.showConfirmDialog(handler.getGame(), "Do you want to host?") == 0
            if (args[0].equalsIgnoreCase("-runServer")) {
                Logger.print("Running game while hosting server!\n");
                server = new Server(handler);
                server.setName("Thread-Server");
                server.start();
            }
        }

        client = new Client(handler);
        client.setIpAddress("localhost");
        client.setName("Thread-Client");
        client.start();

        thread = new Thread(this);
        thread.setName("Thread-Main");
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

    public String getUsername() {
        return username;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}
