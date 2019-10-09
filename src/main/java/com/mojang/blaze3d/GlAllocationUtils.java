package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

@Environment(EnvType.CLIENT)
public class GlAllocationUtils {
   public static synchronized int genLists(int int_1) {
      int int_2 = RenderSystem.genLists(int_1);
      if (int_2 == 0) {
         int int_3 = RenderSystem.getError();
         String string_1 = "No error code reported";
         if (int_3 != 0) {
            string_1 = GLX.getErrorString(int_3);
         }

         throw new IllegalStateException("glGenLists returned an ID of 0 for a count of " + int_1 + ", GL error (" + int_3 + "): " + string_1);
      } else {
         return int_2;
      }
   }

   public static synchronized void deleteLists(int int_1, int int_2) {
      RenderSystem.deleteLists(int_1, int_2);
   }

   public static synchronized void deleteSingletonList(int int_1) {
      deleteLists(int_1, 1);
   }

   public static synchronized ByteBuffer allocateByteBuffer(int int_1) {
      return ByteBuffer.allocateDirect(int_1).order(ByteOrder.nativeOrder());
   }

   public static FloatBuffer allocateFloatBuffer(int int_1) {
      return allocateByteBuffer(int_1 << 2).asFloatBuffer();
   }
}
