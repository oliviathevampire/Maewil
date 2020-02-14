package io.github.vampirestudios.tdg_rewritten.start;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import io.github.vampirestudios.tdg_rewritten.utils.*;

public class MaewilLauncher implements Runnable {

    /*
     * Screen Ids
     */
    public static final int ID_SPLASHSCREEN = 0;
    public static final int ID_GAME = 1;

    /*
     * Properties
     */
    public static String[] ARGS;
    public static boolean FULLSCREEN;
    public static int WIDTH = 854, HEIGHT = 480, WIDTH_TILE_SIZE, HEIGHT_TILE_SIZE;
    public static final String TITLE = "Maewil";

    public static Window window;
    public Shader shader;
    public Thread game;

    public static CatLogger LOGGER;

    public MaewilLauncher() {
        LOGGER = new CatLogger("Maewil-Main");
    }

    public static void main(String[] args) {
        new MaewilLauncher().start();
    }

    public void start() {
        game = new Thread(this, "Game");
        game.start();
    }

    public void init() {
        window = new Window(new WindowSettings(TITLE, WIDTH, HEIGHT, false));
        shader = new Shader("assets/maewil/shaders/mainVertex.glsl", "assets/maewil/shaders/mainFragment.glsl");
        window.setBackgroundColor(0, 0, 0);
        window.create();
        shader.create();
    }

    @Override
    public void run() {
        init();

        while (!window.shouldClose() && !Input.isKeyDown(Settings.QUIT_KEYBIND)) {
            update();
            render();
            if (Input.isKeyDown(Settings.FULLSCREEN_KEYBIND)) window.setFullscreen(!window.getSettings().isFullscreen());
            if (Input.isButtonDown(Settings.LOCK_MOUSE_KEYBIND)) window.mouseState(true);

            if (Input.isKeyDown(Settings.SCRFEENSHOT_KEYBIND)) window.takeScreenshot();
        }
        close();
    }

    private void update() {
        window.update();
    }

    private void render() {
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        shader.destroy();
    }

}
