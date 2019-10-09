package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWImage.Buffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import javax.annotation.Nullable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Optional;
import java.util.function.BiConsumer;

@Environment(EnvType.CLIENT)
public final class Window implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final GLFWErrorCallback errorCallback = GLFWErrorCallback.create(this::logGlError);
   private final WindowEventHandler windowEventHandler;
   private final MonitorTracker monitorTracker;
   private final long handle;
   private int field_5175;
   private int field_5185;
   private int field_5174;
   private int field_5184;
   private Optional<VideoMode> videoMode;
   private boolean fullscreen;
   private boolean field_5177;
   private int positionX;
   private int positionY;
   private int width;
   private int height;
   private int framebufferWidth;
   private int framebufferHeight;
   private int scaledWidth;
   private int scaledHeight;
   private double scaleFactor;
   private String phase = "";
   private boolean field_5186;
   private double field_5189 = Double.MIN_VALUE;
   private int framerateLimit;
   private boolean field_16517;

   public Window(WindowEventHandler windowEventHandler_1, MonitorTracker monitorTracker_1, WindowSettings windowSettings_1, String string_1, String string_2) {
      this.monitorTracker = monitorTracker_1;
      this.throwExceptionOnGlError();
      this.setPhase("Pre startup");
      this.windowEventHandler = windowEventHandler_1;
      Optional<VideoMode> optional_1 = VideoMode.fromString(string_1);
      if (optional_1.isPresent()) {
         this.videoMode = optional_1;
      } else if (windowSettings_1.fullscreenWidth.isPresent() && windowSettings_1.fullscreenHeight.isPresent()) {
         this.videoMode = Optional.of(new VideoMode(windowSettings_1.fullscreenWidth.getAsInt(), windowSettings_1.fullscreenHeight.getAsInt(), 8, 8, 8, 60));
      } else {
         this.videoMode = Optional.empty();
      }

      this.field_5177 = this.fullscreen = windowSettings_1.fullscreen;
      Monitor monitor_1 = monitorTracker_1.getMonitor(GLFW.glfwGetPrimaryMonitor());
      this.field_5174 = this.width = windowSettings_1.width > 0 ? windowSettings_1.width : 1;
      this.field_5184 = this.height = windowSettings_1.height > 0 ? windowSettings_1.height : 1;
      GLFW.glfwDefaultWindowHints();
      GLFW.glfwWindowHint(139265, 196609);
      GLFW.glfwWindowHint(139275, 221185);
      GLFW.glfwWindowHint(139266, 2);
      GLFW.glfwWindowHint(139267, 0);
      GLFW.glfwWindowHint(139272, 0);
      this.handle = GLFW.glfwCreateWindow(this.width, this.height, string_2, this.fullscreen && monitor_1 != null ? monitor_1.getHandle() : 0L, 0L);
      if (monitor_1 != null) {
         VideoMode videoMode_1 = monitor_1.findClosestVideoMode(this.fullscreen ? this.videoMode : Optional.empty());
         this.field_5175 = this.positionX = monitor_1.getViewportX() + videoMode_1.getWidth() / 2 - this.width / 2;
         this.field_5185 = this.positionY = monitor_1.getViewportY() + videoMode_1.getHeight() / 2 - this.height / 2;
      } else {
         int[] ints_1 = new int[1];
         int[] ints_2 = new int[1];
         GLFW.glfwGetWindowPos(this.handle, ints_1, ints_2);
         this.field_5175 = this.positionX = ints_1[0];
         this.field_5185 = this.positionY = ints_2[0];
      }

      GLFW.glfwMakeContextCurrent(this.handle);
      GL.createCapabilities();
      this.method_4479();
      this.method_4483();
      GLFW.glfwSetFramebufferSizeCallback(this.handle, this::onFramebufferSizeChanged);
      GLFW.glfwSetWindowPosCallback(this.handle, this::onWindowPosChanged);
      GLFW.glfwSetWindowSizeCallback(this.handle, this::onWindowSizeChanged);
      GLFW.glfwSetWindowFocusCallback(this.handle, this::onWindowFocusChanged);
   }

   public int method_22092() {
      return GLX._getRefreshRate(this);
   }

   public boolean method_22093() {
      return GLX._shouldClose(this);
   }

   public static void method_4492(BiConsumer<Integer, String> biConsumer_1) {
      MemoryStack memoryStack_1 = MemoryStack.stackPush();
      Throwable var2 = null;

      try {
         PointerBuffer pointerBuffer_1 = memoryStack_1.mallocPointer(1);
         int int_1 = GLFW.glfwGetError(pointerBuffer_1);
         if (int_1 != 0) {
            long long_1 = pointerBuffer_1.get();
            String string_1 = long_1 == 0L ? "" : MemoryUtil.memUTF8(long_1);
            biConsumer_1.accept(int_1, string_1);
         }
      } catch (Throwable var15) {
         var2 = var15;
         throw var15;
      } finally {
         if (var2 != null) {
            try {
               memoryStack_1.close();
            } catch (Throwable var14) {
               var2.addSuppressed(var14);
            }
         } else {
            memoryStack_1.close();
         }

      }

   }

   public void method_4493(boolean boolean_1) {
      RenderSystem.clear(256, boolean_1);
      RenderSystem.matrixMode(5889);
      RenderSystem.loadIdentity();
      RenderSystem.ortho(0.0D, (double)this.getFramebufferWidth() / this.getScaleFactor(), (double)this.getFramebufferHeight() / this.getScaleFactor(), 0.0D, 1000.0D, 3000.0D);
      RenderSystem.matrixMode(5888);
      RenderSystem.loadIdentity();
      RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
   }

   public void setIcon(InputStream inputStream_1, InputStream inputStream_2) {
      try {
         MemoryStack memoryStack_1 = MemoryStack.stackPush();
         Throwable var4 = null;

         try {
            if (inputStream_1 == null) {
               throw new FileNotFoundException("icons/icon_16x16.png");
            }

            if (inputStream_2 == null) {
               throw new FileNotFoundException("icons/icon_32x32.png");
            }

            IntBuffer intBuffer_1 = memoryStack_1.mallocInt(1);
            IntBuffer intBuffer_2 = memoryStack_1.mallocInt(1);
            IntBuffer intBuffer_3 = memoryStack_1.mallocInt(1);
            Buffer gLFWImage$Buffer_1 = GLFWImage.mallocStack(2, memoryStack_1);
            ByteBuffer byteBuffer_1 = this.method_4510(inputStream_1, intBuffer_1, intBuffer_2, intBuffer_3);
            if (byteBuffer_1 == null) {
               throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }

            gLFWImage$Buffer_1.position(0);
            gLFWImage$Buffer_1.width(intBuffer_1.get(0));
            gLFWImage$Buffer_1.height(intBuffer_2.get(0));
            gLFWImage$Buffer_1.pixels(byteBuffer_1);
            ByteBuffer byteBuffer_2 = this.method_4510(inputStream_2, intBuffer_1, intBuffer_2, intBuffer_3);
            if (byteBuffer_2 == null) {
               throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }

            gLFWImage$Buffer_1.position(1);
            gLFWImage$Buffer_1.width(intBuffer_1.get(0));
            gLFWImage$Buffer_1.height(intBuffer_2.get(0));
            gLFWImage$Buffer_1.pixels(byteBuffer_2);
            gLFWImage$Buffer_1.position(0);
            GLFW.glfwSetWindowIcon(this.handle, gLFWImage$Buffer_1);
            STBImage.stbi_image_free(byteBuffer_1);
            STBImage.stbi_image_free(byteBuffer_2);
         } catch (Throwable var19) {
            var4 = var19;
            throw var19;
         } finally {
            if (memoryStack_1 != null) {
               if (var4 != null) {
                  try {
                     memoryStack_1.close();
                  } catch (Throwable var18) {
                     var4.addSuppressed(var18);
                  }
               } else {
                  memoryStack_1.close();
               }
            }

         }
      } catch (IOException var21) {
         LOGGER.error("Couldn't set icon", var21);
      }

   }

   @Nullable
   private ByteBuffer method_4510(InputStream inputStream_1, IntBuffer intBuffer_1, IntBuffer intBuffer_2, IntBuffer intBuffer_3) throws IOException {
      ByteBuffer byteBuffer_1 = null;

      ByteBuffer var6;
      try {
         byteBuffer_1 = class_4536.readResource(inputStream_1);
         byteBuffer_1.rewind();
         var6 = STBImage.stbi_load_from_memory(byteBuffer_1, intBuffer_1, intBuffer_2, intBuffer_3, 0);
      } finally {
         if (byteBuffer_1 != null) {
            MemoryUtil.memFree(byteBuffer_1);
         }

      }

      return var6;
   }

   public void setPhase(String string_1) {
      this.phase = string_1;
   }

   private void throwExceptionOnGlError() {
      GLFW.glfwSetErrorCallback(Window::throwExceptionForGlError);
   }

   private static void throwExceptionForGlError(int int_1, long long_1) {
      throw new IllegalStateException("GLFW error " + int_1 + ": " + MemoryUtil.memUTF8(long_1));
   }

   public void logGlError(int int_1, long long_1) {
      String string_1 = MemoryUtil.memUTF8(long_1);
      LOGGER.error("########## GL ERROR ##########");
      LOGGER.error("@ {}", this.phase);
      LOGGER.error("{}: {}", int_1, string_1);
   }

   public void logOnGlError() {
      GLFWErrorCallback gLFWErrorCallback_1 = GLFW.glfwSetErrorCallback(this.errorCallback);
      if (gLFWErrorCallback_1 != null) {
         gLFWErrorCallback_1.free();
      }

   }

   public void setVsync(boolean boolean_1) {
      this.field_16517 = boolean_1;
      GLFW.glfwSwapInterval(boolean_1 ? 1 : 0);
   }

   public void close() {
      Callbacks.glfwFreeCallbacks(this.handle);
      this.errorCallback.close();
      GLFW.glfwDestroyWindow(this.handle);
      GLFW.glfwTerminate();
   }

   private void onWindowPosChanged(long long_1, int int_1, int int_2) {
      this.positionX = int_1;
      this.positionY = int_2;
   }

   private void onFramebufferSizeChanged(long long_1, int int_1, int int_2) {
      if (long_1 == this.handle) {
         int int_3 = this.getFramebufferWidth();
         int int_4 = this.getFramebufferHeight();
         if (int_1 != 0 && int_2 != 0) {
            this.framebufferWidth = int_1;
            this.framebufferHeight = int_2;
            if (this.getFramebufferWidth() != int_3 || this.getFramebufferHeight() != int_4) {
               this.windowEventHandler.onResolutionChanged();
            }

         }
      }
   }

   private void method_4483() {
      int[] ints_1 = new int[1];
      int[] ints_2 = new int[1];
      GLFW.glfwGetFramebufferSize(this.handle, ints_1, ints_2);
      this.framebufferWidth = ints_1[0];
      this.framebufferHeight = ints_2[0];
   }

   private void onWindowSizeChanged(long long_1, int int_1, int int_2) {
      this.width = int_1;
      this.height = int_2;
   }

   private void onWindowFocusChanged(long long_1, boolean boolean_1) {
      if (long_1 == this.handle) {
         this.windowEventHandler.onWindowFocusChanged(boolean_1);
      }

   }

   public void setFramerateLimit(int int_1) {
      this.framerateLimit = int_1;
   }

   public int getFramerateLimit() {
      return this.framerateLimit;
   }

   public void setFullscreen(boolean boolean_1) {
      GLFW.glfwSwapBuffers(this.handle);
      pollEvents();
      if (this.fullscreen != this.field_5177) {
         this.field_5177 = this.fullscreen;
         this.method_4485(this.field_16517);
      }

   }

   public void waitForFramerateLimit() {
      double double_1 = this.field_5189 + 1.0D / (double)this.getFramerateLimit();

      double double_2;
      for(double_2 = GLFW.glfwGetTime(); double_2 < double_1; double_2 = GLFW.glfwGetTime()) {
         GLFW.glfwWaitEventsTimeout(double_1 - double_2);
      }

      this.field_5189 = double_2;
   }

   public Optional<VideoMode> getVideoMode() {
      return this.videoMode;
   }

   public void setVideoMode(Optional<VideoMode> optional_1) {
      boolean boolean_1 = !optional_1.equals(this.videoMode);
      this.videoMode = optional_1;
      if (boolean_1) {
         this.field_5186 = true;
      }

   }

   public void method_4475() {
      if (this.fullscreen && this.field_5186) {
         this.field_5186 = false;
         this.method_4479();
         this.windowEventHandler.onResolutionChanged();
      }

   }

   private void method_4479() {
      boolean boolean_1 = GLFW.glfwGetWindowMonitor(this.handle) != 0L;
      if (this.fullscreen) {
         Monitor monitor_1 = this.monitorTracker.getMonitor(this);
         if (monitor_1 == null) {
            LOGGER.warn("Failed to find suitable monitor for fullscreen mode");
            this.fullscreen = false;
         } else {
            VideoMode videoMode_1 = monitor_1.findClosestVideoMode(this.videoMode);
            if (!boolean_1) {
               this.field_5175 = this.positionX;
               this.field_5185 = this.positionY;
               this.field_5174 = this.width;
               this.field_5184 = this.height;
            }

            this.positionX = 0;
            this.positionY = 0;
            this.width = videoMode_1.getWidth();
            this.height = videoMode_1.getHeight();
            GLFW.glfwSetWindowMonitor(this.handle, monitor_1.getHandle(), this.positionX, this.positionY, this.width, this.height, videoMode_1.getRefreshRate());
         }
      } else {
         this.positionX = this.field_5175;
         this.positionY = this.field_5185;
         this.width = this.field_5174;
         this.height = this.field_5184;
         GLFW.glfwSetWindowMonitor(this.handle, 0L, this.positionX, this.positionY, this.width, this.height, -1);
      }

   }

   public void toggleFullscreen() {
      this.fullscreen = !this.fullscreen;
   }

   private void method_4485(boolean boolean_1) {
      try {
         this.method_4479();
         this.windowEventHandler.onResolutionChanged();
         this.setVsync(boolean_1);
         this.windowEventHandler.updateDisplay(false);
      } catch (Exception var3) {
         LOGGER.error("Couldn't toggle fullscreen", var3);
      }

   }

   public int calculateScaleFactor(int int_1, boolean boolean_1) {
      int int_2;
      for(int_2 = 1; int_2 != int_1 && int_2 < this.framebufferWidth && int_2 < this.framebufferHeight && this.framebufferWidth / (int_2 + 1) >= 320 && this.framebufferHeight / (int_2 + 1) >= 240; ++int_2) {
      }

      if (boolean_1 && int_2 % 2 != 0) {
         ++int_2;
      }

      return int_2;
   }

   public void setScaleFactor(double double_1) {
      this.scaleFactor = double_1;
      int int_1 = (int)((double)this.framebufferWidth / double_1);
      this.scaledWidth = (double)this.framebufferWidth / double_1 > (double)int_1 ? int_1 + 1 : int_1;
      int int_2 = (int)((double)this.framebufferHeight / double_1);
      this.scaledHeight = (double)this.framebufferHeight / double_1 > (double)int_2 ? int_2 + 1 : int_2;
   }

   public long getHandle() {
      return this.handle;
   }

   public boolean isFullscreen() {
      return this.fullscreen;
   }

   public int getFramebufferWidth() {
      return this.framebufferWidth;
   }

   public int getFramebufferHeight() {
      return this.framebufferHeight;
   }

   public static void pollEvents() {
      GLFW.glfwPollEvents();
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int getScaledWidth() {
      return this.scaledWidth;
   }

   public int getScaledHeight() {
      return this.scaledHeight;
   }

   public int getPositionY() {
      return this.positionX;
   }

   public int getPositionX() {
      return this.positionY;
   }

   public double getScaleFactor() {
      return this.scaleFactor;
   }

   @Nullable
   public Monitor getMonitor() {
      return this.monitorTracker.getMonitor(this);
   }

   public void method_21668(boolean boolean_1) {
      InputUtil.method_21736(this.handle, boolean_1);
   }
}
