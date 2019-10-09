package com.mojang.blaze3d;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import oshi.SystemInfo;
import oshi.hardware.Processor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class GLX {
   private static final Logger LOGGER = LogManager.getLogger();
   private static String capsString = "";
   private static String cpuInfo;
   private static final Map<Integer, String> LOOKUP_MAP = make(Maps.newHashMap(), (hashMap_1) -> {
      hashMap_1.put(0, "No error");
      hashMap_1.put(1280, "Enum parameter is invalid for this function");
      hashMap_1.put(1281, "Parameter is invalid for this function");
      hashMap_1.put(1282, "Current state is invalid for this function");
      hashMap_1.put(1283, "Stack overflow");
      hashMap_1.put(1284, "Stack underflow");
      hashMap_1.put(1285, "Out of memory");
      hashMap_1.put(1286, "Operation on incomplete framebuffer");
      hashMap_1.put(1286, "Operation on incomplete framebuffer");
   });

   public static String getOpenGLVersionString() {
      return GLFW.glfwGetCurrentContext() == 0L ? "NO CONTEXT" : RenderSystem.getString(7937) + " GL version " + RenderSystem.getString(7938) + ", " + RenderSystem.getString(7936);
   }

   public static int _getRefreshRate(Window window_1) {
      long long_1 = GLFW.glfwGetWindowMonitor(window_1.getHandle());
      if (long_1 == 0L) {
         long_1 = GLFW.glfwGetPrimaryMonitor();
      }

      GLFWVidMode gLFWVidMode_1 = long_1 == 0L ? null : GLFW.glfwGetVideoMode(long_1);
      return gLFWVidMode_1 == null ? 0 : gLFWVidMode_1.refreshRate();
   }

   public static String _getLWJGLVersion() {
      return Version.getVersion();
   }

   public static LongSupplier _initGlfw() {
      Window.method_4492((integer_1, string_1x) -> {
         throw new IllegalStateException(String.format("GLFW error before init: [0x%X]%s", integer_1, string_1x));
      });
      List<String> list_1 = Lists.newArrayList();
      GLFWErrorCallback gLFWErrorCallback_1 = GLFW.glfwSetErrorCallback((int_1, long_1) -> {
         list_1.add(String.format("GLFW error during init: [0x%X]%s", int_1, long_1));
      });
      if (!GLFW.glfwInit()) {
         throw new IllegalStateException("Failed to initialize GLFW, errors: " + Joiner.on(",").join(list_1));
      } else {
         LongSupplier longSupplier_2 = () -> {
            return (long)(GLFW.glfwGetTime() * 1.0E9D);
         };
         Iterator var3 = list_1.iterator();

         while(var3.hasNext()) {
            String string_1 = (String)var3.next();
            LOGGER.error("GLFW error collected during initialization: {}", string_1);
         }

         RenderSystem.setErrorCallback(gLFWErrorCallback_1);
         return longSupplier_2;
      }
   }

   public static void _setGlfwErrorCallback(GLFWErrorCallbackI gLFWErrorCallbackI_1) {
      GLFWErrorCallback gLFWErrorCallback_1 = GLFW.glfwSetErrorCallback(gLFWErrorCallbackI_1);
      if (gLFWErrorCallback_1 != null) {
         gLFWErrorCallback_1.free();
      }

   }

   public static boolean _shouldClose(Window window_1) {
      return GLFW.glfwWindowShouldClose(window_1.getHandle());
   }

   public static void _pollEvents() {
      GLFW.glfwPollEvents();
   }

   public static void _setupNvFogDistance() {
      if (GL.getCapabilities().GL_NV_fog_distance) {
         RenderSystem.fogi(34138, 34139);
      }

   }

   public static void _init(int int_1, boolean boolean_1) {
      GLCapabilities gLCapabilities_1 = GL.getCapabilities();
      capsString = "Using framebuffer using " + class_4493.method_21973(gLCapabilities_1);

      try {
         Processor[] processors_1 = (new SystemInfo()).getHardware().getProcessors();
         cpuInfo = String.format("%dx %s", processors_1.length, processors_1[0]).replaceAll("\\s+", " ");
      } catch (Throwable var4) {
      }

      GlDebug.enableDebug(int_1, boolean_1);
   }

   public static String _getCapsString() {
      return capsString;
   }

   public static String _getCpuInfo() {
      return cpuInfo == null ? "<unknown>" : cpuInfo;
   }

   public static void _renderCrosshair(int int_1, boolean boolean_1, boolean boolean_2, boolean boolean_3) {
      RenderSystem.disableTexture();
      RenderSystem.depthMask(false);
      Tessellator tessellator_1 = Tessellator.getInstance();
      BufferBuilder bufferBuilder_1 = tessellator_1.getBufferBuilder();
      GL11.glLineWidth(4.0F);
      bufferBuilder_1.begin(1, VertexFormats.POSITION_COLOR);
      if (boolean_1) {
         bufferBuilder_1.vertex(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).next();
         bufferBuilder_1.vertex((double)int_1, 0.0D, 0.0D).color(0, 0, 0, 255).next();
      }

      if (boolean_2) {
         bufferBuilder_1.vertex(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).next();
         bufferBuilder_1.vertex(0.0D, (double)int_1, 0.0D).color(0, 0, 0, 255).next();
      }

      if (boolean_3) {
         bufferBuilder_1.vertex(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).next();
         bufferBuilder_1.vertex(0.0D, 0.0D, (double)int_1).color(0, 0, 0, 255).next();
      }

      tessellator_1.draw();
      GL11.glLineWidth(2.0F);
      bufferBuilder_1.begin(1, VertexFormats.POSITION_COLOR);
      if (boolean_1) {
         bufferBuilder_1.vertex(0.0D, 0.0D, 0.0D).color(255, 0, 0, 255).next();
         bufferBuilder_1.vertex((double)int_1, 0.0D, 0.0D).color(255, 0, 0, 255).next();
      }

      if (boolean_2) {
         bufferBuilder_1.vertex(0.0D, 0.0D, 0.0D).color(0, 255, 0, 255).next();
         bufferBuilder_1.vertex(0.0D, (double)int_1, 0.0D).color(0, 255, 0, 255).next();
      }

      if (boolean_3) {
         bufferBuilder_1.vertex(0.0D, 0.0D, 0.0D).color(127, 127, 255, 255).next();
         bufferBuilder_1.vertex(0.0D, 0.0D, (double)int_1).color(127, 127, 255, 255).next();
      }

      tessellator_1.draw();
      GL11.glLineWidth(1.0F);
      RenderSystem.depthMask(true);
      RenderSystem.enableTexture();
   }

   public static String getErrorString(int int_1) {
      return (String)LOOKUP_MAP.get(int_1);
   }

   public static <T> T make(Supplier<T> supplier_1) {
      return supplier_1.get();
   }

   public static <T> T make(T object_1, Consumer<T> consumer_1) {
      consumer_1.accept(object_1);
      return object_1;
   }
}
