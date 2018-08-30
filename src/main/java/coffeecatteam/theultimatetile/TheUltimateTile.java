package coffeecatteam.theultimatetile;

import coffeecatteam.theultimatetile.entities.EntityManager;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Camera;
import coffeecatteam.theultimatetile.input.KeyManager;
import coffeecatteam.theultimatetile.input.MouseManager;
import coffeecatteam.theultimatetile.input.WindowManager;
import coffeecatteam.theultimatetile.inventory.items.ItemManager;
import coffeecatteam.theultimatetile.inventory.items.Items;
import coffeecatteam.theultimatetile.net.Client;
import coffeecatteam.theultimatetile.net.Server;
import coffeecatteam.theultimatetile.net.packet.Packet01Disconnect;
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.state.StateMenu;
import coffeecatteam.theultimatetile.state.StateMenuMultiplayer;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;
import coffeecatteam.theultimatetile.worlds.World;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class TheUltimateTile extends Canvas implements Runnable {
    
    private static TheUltimateTile theUltimateTile;

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
    private World world;

    private WindowManager windowManager;

    private Client client;
    private Server server;
    private String username;
    private boolean singlePlayer = true;

    public TheUltimateTile(String title, int width, int height) {
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

        camera = new Camera(this, 0, 0);

        username = Utils.getUsername();
        //stateGame = new StateGame(this).reset();
        stateMenu = new StateMenu(this);
        stateMenuMultiplayer = new StateMenuMultiplayer(this, username);
        State.setState(stateMenu);

        entityManager = new EntityManager(this, new EntityPlayerMP(this, username, null, -1, true));

        itemManager = new ItemManager(this);
        windowManager = new WindowManager(this);

        Items.init();
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

    public synchronized void start(String[] args) {
        if (running)
            return;
        running = true;

        if (args.length > 0) { // JOptionPane.showConfirmDialog(this, "Do you want to host?") == 0
            if (args[0].equalsIgnoreCase("-runServer")) {
                Logger.print("Running game while hosting server!\n");
                server = new Server(this);
                server.setName("Thread-Server");
                server.start();
            }
        }

        client = new Client(this);
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

    public String getUsername() {
        return username;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public boolean isHosting() {
        return server != null;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    public void setSinglePlayer(boolean singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    /*
     * Disconnect from server.
     * Checks if in single player
     */
    public void disconnect() {
        if (!singlePlayer) {
            Packet01Disconnect packet = new Packet01Disconnect(this.getEntityManager().getPlayer().getUsername());
            packet.writeData(client);
        }
    }

    /*
     * Gets an instance of the game.
     */
    public static TheUltimateTile getTheUltimateTile() {
        return theUltimateTile;
    }
}
