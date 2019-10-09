package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

@Environment(EnvType.CLIENT)
public class class_4536 {
   private static final Logger LOGGER = LogManager.getLogger();

   public static int generateTextureId() {
      return RenderSystem.genTexture();
   }

   public static void releaseTextureId(int int_1) {
      RenderSystem.deleteTexture(int_1);
   }

   public static void prepareImage(int int_1, int int_2, int int_3) {
      prepareImage(NativeImage.GLFormat.RGBA, int_1, 0, int_2, int_3);
   }

   public static void prepareImage(NativeImage.GLFormat nativeImage$GLFormat_1, int int_1, int int_2, int int_3) {
      prepareImage(nativeImage$GLFormat_1, int_1, 0, int_2, int_3);
   }

   public static void prepareImage(int int_1, int int_2, int int_3, int int_4) {
      prepareImage(NativeImage.GLFormat.RGBA, int_1, int_2, int_3, int_4);
   }

   public static void prepareImage(NativeImage.GLFormat nativeImage$GLFormat_1, int int_1, int int_2, int int_3, int int_4) {
      bind(int_1);
      if (int_2 >= 0) {
         RenderSystem.texParameter(3553, 33085, int_2);
         RenderSystem.texParameter(3553, 33082, 0);
         RenderSystem.texParameter(3553, 33083, int_2);
         RenderSystem.texParameter(3553, 34049, 0.0F);
      }

      for(int int_5 = 0; int_5 <= int_2; ++int_5) {
         RenderSystem.texImage2D(3553, int_5, nativeImage$GLFormat_1.getGlConstant(), int_3 >> int_5, int_4 >> int_5, 0, 6408, 5121, (IntBuffer)null);
      }

   }

   private static void bind(int int_1) {
      RenderSystem.bindTexture(int_1);
   }

   public static ByteBuffer readResource(InputStream inputStream_1) throws IOException {
      ByteBuffer byteBuffer_2;
      if (inputStream_1 instanceof FileInputStream) {
         FileInputStream fileInputStream_1 = (FileInputStream)inputStream_1;
         FileChannel fileChannel_1 = fileInputStream_1.getChannel();
         byteBuffer_2 = MemoryUtil.memAlloc((int)fileChannel_1.size() + 1);

         while(true) {
            if (fileChannel_1.read(byteBuffer_2) != -1) {
               continue;
            }
         }
      } else {
         byteBuffer_2 = MemoryUtil.memAlloc(8192);
         ReadableByteChannel readableByteChannel_1 = Channels.newChannel(inputStream_1);

         while(readableByteChannel_1.read(byteBuffer_2) != -1) {
            if (byteBuffer_2.remaining() == 0) {
               byteBuffer_2 = MemoryUtil.memRealloc(byteBuffer_2, byteBuffer_2.capacity() * 2);
            }
         }
      }

      return byteBuffer_2;
   }

   public static String readResourceAsString(InputStream inputStream_1) {
      ByteBuffer byteBuffer_1 = null;

      try {
         byteBuffer_1 = readResource(inputStream_1);
         int int_1 = byteBuffer_1.position();
         byteBuffer_1.rewind();
         String var3 = MemoryUtil.memASCII(byteBuffer_1, int_1);
         return var3;
      } catch (IOException var7) {
      } finally {
         if (byteBuffer_1 != null) {
            MemoryUtil.memFree(byteBuffer_1);
         }

      }

      return null;
   }

   public static void initTexture(IntBuffer intBuffer_1, int int_1, int int_2) {
      GL11.glPixelStorei(3312, 0);
      GL11.glPixelStorei(3313, 0);
      GL11.glPixelStorei(3314, 0);
      GL11.glPixelStorei(3315, 0);
      GL11.glPixelStorei(3316, 0);
      GL11.glPixelStorei(3317, 4);
      GL11.glTexImage2D(3553, 0, 6408, int_1, int_2, 0, 32993, 33639, intBuffer_1);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10241, 9729);
   }
}
