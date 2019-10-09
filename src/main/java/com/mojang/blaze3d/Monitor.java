package com.mojang.blaze3d;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public final class Monitor {
   private final long handle;
   private final List<VideoMode> videoModes;
   private VideoMode currentVideoMode;
   private int x;
   private int y;

   public Monitor(long long_1) {
      this.handle = long_1;
      this.videoModes = Lists.newArrayList();
      this.populateVideoModes();
   }

   private void populateVideoModes() {
      this.videoModes.clear();
      Buffer gLFWVidMode$Buffer_1 = GLFW.glfwGetVideoModes(this.handle);

      for(int int_1 = gLFWVidMode$Buffer_1.limit() - 1; int_1 >= 0; --int_1) {
         gLFWVidMode$Buffer_1.position(int_1);
         VideoMode videoMode_1 = new VideoMode(gLFWVidMode$Buffer_1);
         if (videoMode_1.getRedBits() >= 8 && videoMode_1.getGreenBits() >= 8 && videoMode_1.getBlueBits() >= 8) {
            this.videoModes.add(videoMode_1);
         }
      }

      int[] ints_1 = new int[1];
      int[] ints_2 = new int[1];
      GLFW.glfwGetMonitorPos(this.handle, ints_1, ints_2);
      this.x = ints_1[0];
      this.y = ints_2[0];
      GLFWVidMode gLFWVidMode_1 = GLFW.glfwGetVideoMode(this.handle);
      this.currentVideoMode = new VideoMode(gLFWVidMode_1);
   }

   public VideoMode findClosestVideoMode(Optional<VideoMode> optional_1) {
      if (optional_1.isPresent()) {
         VideoMode videoMode_1 = (VideoMode)optional_1.get();
         Iterator var3 = this.videoModes.iterator();

         while(var3.hasNext()) {
            VideoMode videoMode_2 = (VideoMode)var3.next();
            if (videoMode_2.equals(videoMode_1)) {
               return videoMode_2;
            }
         }
      }

      return this.getCurrentVideoMode();
   }

   public int findClosestVideoModeIndex(VideoMode videoMode_1) {
      return this.videoModes.indexOf(videoMode_1);
   }

   public VideoMode getCurrentVideoMode() {
      return this.currentVideoMode;
   }

   public int getViewportX() {
      return this.x;
   }

   public int getViewportY() {
      return this.y;
   }

   public VideoMode getVideoMode(int int_1) {
      return (VideoMode)this.videoModes.get(int_1);
   }

   public int getVideoModeCount() {
      return this.videoModes.size();
   }

   public long getHandle() {
      return this.handle;
   }

   public String toString() {
      return String.format("Monitor[%s %sx%s %s]", this.handle, this.x, this.y, this.currentVideoMode);
   }
}
