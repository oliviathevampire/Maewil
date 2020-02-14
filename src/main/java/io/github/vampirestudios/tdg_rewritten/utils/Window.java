package io.github.vampirestudios.tdg_rewritten.utils;

import com.google.common.base.Preconditions;
import io.github.vampirestudios.tdg_rewritten.utils.math.Matrix4f;
import io.github.vampirestudios.tdg_rewritten.utils.math.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private static long time;
    private final GLFWErrorCallback errorCallback = new GLFWErrorCallback() {
        @Override
        public void invoke(int error, long description) {
            Logging.glfwLogger.log(Level.WARNING, GLFWErrorCallback.getDescription(description), new RuntimeException());
        }
    };
    private WindowSettings settings;
    private long window;
    private int frames;
    private Input input;
    private Vector3f background = new Vector3f(0, 0, 0);
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private boolean isFullscreen;
    private int[] windowPosX = new int[1], windowPosY = new int[1];
    private Matrix4f projection;

    public Window(WindowSettings settings) {
        this.settings = settings;
        this.projection = Matrix4f.projection(70.0f, (float) this.settings.getWidth() / (float) this.settings.getHeight(), 0.1f, 1000.0f);
    }

    public void create() {
//		engine.LOGGER.info("Initializing GLFW");

        GLFW.glfwSetErrorCallback(this.errorCallback);

        Preconditions.checkState(GLFW.glfwInit(), "Unable to inialize GLFW");

        GLFW.glfwDefaultWindowHints();
        glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_COMPAT_PROFILE);
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, 4);

        input = new Input();
        window = GLFW.glfwCreateWindow(settings.getWidth(), settings.getHeight(), settings.getTitle(), settings.isFullscreen() ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
        if (this.window == MemoryUtil.NULL) {
            GLFW.glfwTerminate();
            throw new IllegalStateException("Unable to create window");
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        windowPosX[0] = (videoMode.width() - settings.getWidth()) / 2;
        windowPosY[0] = (videoMode.height() - settings.getHeight()) / 2;
        GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);

//		engine.LOGGER.info("Initializing system");

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        createCallbacks();

        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(1);

        time = System.currentTimeMillis();

        glEnable(GL_CULL_FACE);
        glCullFace(GL_FRONT);
        glFrontFace(GL_CCW);
    }

    private void createCallbacks() {
        sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                settings.setWidth(w);
                settings.setHeight(h);
                isResized = true;
            }
        };

        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getCursorMoveCallback());
        GLFW.glfwSetCursorEnterCallback(window, input.getCursorEnterCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }

    public void update() {
        if (isResized) {
            GL11.glViewport(0, 0, this.settings.getWidth(), this.settings.getHeight());
            isResized = false;
        }
        GL11.glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
        GLFW.glfwPollEvents();
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(this.window, this.settings.getTitle() + " | FPS: " + this.frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroy() {
        input.destroy();
        sizeCallback.free();

        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public void setBackgroundColor(float r, float g, float b) {
        background.set(r, g, b);
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public void setFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        isResized = true;
        if (this.settings.isFullscreen()) {
            GLFW.glfwGetWindowPos(this.window, this.windowPosX, this.windowPosY);
            GLFW.glfwSetWindowMonitor(this.window, GLFW.glfwGetPrimaryMonitor(), 0, 0, this.settings.getWidth(), this.settings.getHeight(), 0);
        } else {
            GLFW.glfwSetWindowMonitor(this.window, 0, this.windowPosX[0], this.windowPosY[0], this.settings.getWidth(), this.settings.getHeight(), 0);
        }
    }

    public void mouseState(boolean lock) {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, lock ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
    }

    public WindowSettings getSettings() {
        return settings;
    }

    public void takeScreenshot() {
        try {
            GL11.glReadBuffer(GL11.GL_FRONT);
            int colors = 4;

            ByteBuffer buf = BufferUtils.createByteBuffer(this.settings.getWidth() * this.settings.getHeight() * colors);
            GL11.glReadPixels(0, 0, this.settings.getWidth(), this.settings.getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

            BufferedImage image = new BufferedImage(this.settings.getWidth(), this.settings.getHeight(), BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < this.settings.getWidth(); x++) {
                for (int y = 0; y < this.settings.getHeight(); y++) {
                    int i = (x + (y * this.settings.getWidth())) * colors;

                    int r = buf.get(i) & 0xFF;
                    int g = buf.get(i + 1) & 0xFF;
                    int b = buf.get(i + 2) & 0xFF;

                    image.setRGB(x, this.settings.getHeight() - (y + 1), Colors.rgb(r, g, b));
                }
            }

            File dir = new File("screenshot");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".png");
            ImageIO.write(image, "png", file);

//            Main.LOGGER.info("Saved screenshot to " + file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return settings.getWidth();
    }

    public int getHeight() {
        return settings.getHeight();
    }

    public String getTitle() {
        return settings.getTitle();
    }

    public long getWindow() {
        return window;
    }

    public Matrix4f getProjectionMatrix() {
        return projection;
    }
}