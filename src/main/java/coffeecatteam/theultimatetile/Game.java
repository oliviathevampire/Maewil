package coffeecatteam.theultimatetile;

import coffeecatteam.theultimatetile.display.Display;
<<<<<<< HEAD
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
=======
>>>>>>> parent of fcc0183... Started work on multiplayer (crashes when start button is clicked)
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Camera;
import coffeecatteam.theultimatetile.input.KeyManager;
import coffeecatteam.theultimatetile.input.MouseManager;
<<<<<<< HEAD
import coffeecatteam.theultimatetile.net.Client;
import coffeecatteam.theultimatetile.net.Server;
import coffeecatteam.theultimatetile.net.packet.Packet00Login;
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.state.StateGame;
import coffeecatteam.theultimatetile.state.StateMenu;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Logger;
=======
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.state.StateGame;
import coffeecatteam.theultimatetile.state.StateMenu;
>>>>>>> parent of fcc0183... Started work on multiplayer (crashes when start button is clicked)

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private Display display;
    private int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    // States
    public StateGame gameState;
    public StateMenu menuState;

    private KeyManager keyManager;
    private MouseManager mouseManager;

    private Camera camera;

    private Handler handler;

<<<<<<< HEAD
    // Client & Server
    private Client client;
    private Server server;
    public EntityPlayer player;

=======
>>>>>>> parent of fcc0183... Started work on multiplayer (crashes when start button is clicked)
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    private void init() {
        Assets.init();
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        handler = new Handler(this);
        camera = new Camera(handler, 0, 0);

        gameState = new StateGame(handler);
        menuState = new StateMenu(handler);
        State.setState(menuState.init());
    }

    private void tick() {
        keyManager.tick();

        if (State.getState() != null)
            State.getState().tick();
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        // Start drawing

        if (State.getState() != null) {
            State.getState().render(g);
            player.render(g);
        }

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

<<<<<<< HEAD
        //client.sendData("ping".getBytes());
        int maxChars = 16;
        String username = JOptionPane.showInputDialog("Please enter a username\nMust be max " + maxChars + " characters", "Player");
        if (username.length() > maxChars)
            username = username.substring(0, maxChars);
        Packet00Login loginPacket = new Packet00Login(username);
        loginPacket.writeData(client);

        try {
            Logger.print(player.getUsername() + " " + player.getX() + " " + player.getY());
        } catch (NullPointerException e) {
            player = new EntityPlayer(handler, "player", "NULL").setPos(9 * Tile.TILE_WIDTH, 10 * Tile.TILE_HEIGHT);
            loginPacket = new Packet00Login(username);
            loginPacket.writeData(client);
        }

        gameState = new StateGame(handler);
        menuState = new StateMenu(handler);
        State.setState(gameState.init());

        player.setX((handler.getWorld().getSpawnX() + 1) * Tile.TILE_WIDTH);
        player.setY(handler.getWorld().getSpawnY() * Tile.TILE_HEIGHT);

//        for (int i = 0; i < 1000000; i++) {
//            tick();
//            render();
//        }

=======
>>>>>>> parent of fcc0183... Started work on multiplayer (crashes when start button is clicked)
        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                tick();
                player.tick();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
                System.out.println("Ticks and Frames: " + ticks);
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

    public Display getDisplay() {
        return display;
    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
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
}
