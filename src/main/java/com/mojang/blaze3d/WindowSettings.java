package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.OptionalInt;

@Environment(EnvType.CLIENT)
public class WindowSettings {
   public final int width;
   public final int height;
   public final OptionalInt fullscreenWidth;
   public final OptionalInt fullscreenHeight;
   public final boolean fullscreen;

   public WindowSettings(int width, int height, OptionalInt fullscreenWidth, OptionalInt fullscreenHeight, boolean fullscreen) {
      this.width = width;
      this.height = height;
      this.fullscreenWidth = fullscreenWidth;
      this.fullscreenHeight = fullscreenHeight;
      this.fullscreen = fullscreen;
   }
}
