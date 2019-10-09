package com.mojang.blaze3d;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMonitorCallbackI;

import javax.annotation.Nullable;

@Environment(EnvType.CLIENT)
public class MonitorTracker {
   private final Long2ObjectMap<Monitor> pointerToMonitorMap = new Long2ObjectOpenHashMap();
   private final MonitorFactory monitorFactory;

   public MonitorTracker(MonitorFactory monitorFactory_1) {
      this.monitorFactory = monitorFactory_1;
      GLFW.glfwSetMonitorCallback(this::handleMonitorEvent);
      PointerBuffer pointerBuffer_1 = GLFW.glfwGetMonitors();
      if (pointerBuffer_1 != null) {
         for(int int_1 = 0; int_1 < pointerBuffer_1.limit(); ++int_1) {
            long long_1 = pointerBuffer_1.get(int_1);
            this.pointerToMonitorMap.put(long_1, monitorFactory_1.createMonitor(long_1));
         }
      }

   }

   private void handleMonitorEvent(long long_1, int int_1) {
      if (int_1 == 262145) {
         this.pointerToMonitorMap.put(long_1, this.monitorFactory.createMonitor(long_1));
      } else if (int_1 == 262146) {
         this.pointerToMonitorMap.remove(long_1);
      }

   }

   @Nullable
   public Monitor getMonitor(long long_1) {
      return (Monitor)this.pointerToMonitorMap.get(long_1);
   }

   @Nullable
   public Monitor getMonitor(Window window_1) {
      long long_1 = GLFW.glfwGetWindowMonitor(window_1.getHandle());
      if (long_1 != 0L) {
         return this.getMonitor(long_1);
      } else {
         int int_1 = window_1.getPositionY();
         int int_2 = int_1 + window_1.getWidth();
         int int_3 = window_1.getPositionX();
         int int_4 = int_3 + window_1.getHeight();
         int int_5 = -1;
         Monitor monitor_1 = null;
         ObjectIterator var10 = this.pointerToMonitorMap.values().iterator();

         while(var10.hasNext()) {
            Monitor monitor_2 = (Monitor)var10.next();
            int int_6 = monitor_2.getViewportX();
            int int_7 = int_6 + monitor_2.getCurrentVideoMode().getWidth();
            int int_8 = monitor_2.getViewportY();
            int int_9 = int_8 + monitor_2.getCurrentVideoMode().getHeight();
            int int_10 = clamp(int_1, int_6, int_7);
            int int_11 = clamp(int_2, int_6, int_7);
            int int_12 = clamp(int_3, int_8, int_9);
            int int_13 = clamp(int_4, int_8, int_9);
            int int_14 = Math.max(0, int_11 - int_10);
            int int_15 = Math.max(0, int_13 - int_12);
            int int_16 = int_14 * int_15;
            if (int_16 > int_5) {
               monitor_1 = monitor_2;
               int_5 = int_16;
            }
         }

         return monitor_1;
      }
   }

   public static int clamp(int int_1, int int_2, int int_3) {
      if (int_1 < int_2) {
         return int_2;
      } else {
         return int_1 > int_3 ? int_3 : int_1;
      }
   }

   public void stop() {
      GLFWMonitorCallback gLFWMonitorCallback_1 = GLFW.glfwSetMonitorCallback((GLFWMonitorCallbackI)null);
      if (gLFWMonitorCallback_1 != null) {
         gLFWMonitorCallback_1.free();
      }

   }
}
