package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Environment(EnvType.CLIENT)
public final class VideoMode {
   private final int width;
   private final int height;
   private final int redBits;
   private final int greenBits;
   private final int blueBits;
   private final int refreshRate;
   private static final Pattern PATTERN = Pattern.compile("(\\d+)x(\\d+)(?:@(\\d+)(?::(\\d+))?)?");

   public VideoMode(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6) {
      this.width = int_1;
      this.height = int_2;
      this.redBits = int_3;
      this.greenBits = int_4;
      this.blueBits = int_5;
      this.refreshRate = int_6;
   }

   public VideoMode(Buffer gLFWVidMode$Buffer_1) {
      this.width = gLFWVidMode$Buffer_1.width();
      this.height = gLFWVidMode$Buffer_1.height();
      this.redBits = gLFWVidMode$Buffer_1.redBits();
      this.greenBits = gLFWVidMode$Buffer_1.greenBits();
      this.blueBits = gLFWVidMode$Buffer_1.blueBits();
      this.refreshRate = gLFWVidMode$Buffer_1.refreshRate();
   }

   public VideoMode(GLFWVidMode gLFWVidMode_1) {
      this.width = gLFWVidMode_1.width();
      this.height = gLFWVidMode_1.height();
      this.redBits = gLFWVidMode_1.redBits();
      this.greenBits = gLFWVidMode_1.greenBits();
      this.blueBits = gLFWVidMode_1.blueBits();
      this.refreshRate = gLFWVidMode_1.refreshRate();
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int getRedBits() {
      return this.redBits;
   }

   public int getGreenBits() {
      return this.greenBits;
   }

   public int getBlueBits() {
      return this.blueBits;
   }

   public int getRefreshRate() {
      return this.refreshRate;
   }

   public boolean equals(Object object_1) {
      if (this == object_1) {
         return true;
      } else if (object_1 != null && this.getClass() == object_1.getClass()) {
         VideoMode videoMode_1 = (VideoMode)object_1;
         return this.width == videoMode_1.width && this.height == videoMode_1.height && this.redBits == videoMode_1.redBits && this.greenBits == videoMode_1.greenBits && this.blueBits == videoMode_1.blueBits && this.refreshRate == videoMode_1.refreshRate;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.width, this.height, this.redBits, this.greenBits, this.blueBits, this.refreshRate});
   }

   public String toString() {
      return String.format("%sx%s@%s (%sbit)", this.width, this.height, this.refreshRate, this.redBits + this.greenBits + this.blueBits);
   }

   public static Optional<VideoMode> fromString(@Nullable String string_1) {
      if (string_1 == null) {
         return Optional.empty();
      } else {
         try {
            Matcher matcher_1 = PATTERN.matcher(string_1);
            if (matcher_1.matches()) {
               int int_1 = Integer.parseInt(matcher_1.group(1));
               int int_2 = Integer.parseInt(matcher_1.group(2));
               String string_2 = matcher_1.group(3);
               int int_4;
               if (string_2 == null) {
                  int_4 = 60;
               } else {
                  int_4 = Integer.parseInt(string_2);
               }

               String string_3 = matcher_1.group(4);
               int int_6;
               if (string_3 == null) {
                  int_6 = 24;
               } else {
                  int_6 = Integer.parseInt(string_3);
               }

               int int_7 = int_6 / 3;
               return Optional.of(new VideoMode(int_1, int_2, int_7, int_7, int_7, int_4));
            }
         } catch (Exception var9) {
         }

         return Optional.empty();
      }
   }

   public String asString() {
      return String.format("%sx%s@%s:%s", this.width, this.height, this.refreshRate, this.redBits + this.greenBits + this.blueBits);
   }
}
