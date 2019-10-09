package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.io.IOUtils;
import org.lwjgl.stb.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.EnumSet;
import java.util.Set;

@Environment(EnvType.CLIENT)
public final class NativeImage implements AutoCloseable {
   private static final Set<StandardOpenOption> WRITE_TO_FILE_OPEN_OPTIONS;
   private final NativeImage.Format format;
   private final int width;
   private final int height;
   private final boolean isStbImage;
   private long pointer;
   private final int sizeBytes;

   public NativeImage(int int_1, int int_2, boolean boolean_1) {
      this(NativeImage.Format.RGBA, int_1, int_2, boolean_1);
   }

   public NativeImage(NativeImage.Format nativeImage$Format_1, int int_1, int int_2, boolean boolean_1) {
      this.format = nativeImage$Format_1;
      this.width = int_1;
      this.height = int_2;
      this.sizeBytes = int_1 * int_2 * nativeImage$Format_1.getChannelCount();
      this.isStbImage = false;
      if (boolean_1) {
         this.pointer = MemoryUtil.nmemCalloc(1L, (long)this.sizeBytes);
      } else {
         this.pointer = MemoryUtil.nmemAlloc((long)this.sizeBytes);
      }

   }

   private NativeImage(NativeImage.Format nativeImage$Format_1, int int_1, int int_2, boolean boolean_1, long long_1) {
      this.format = nativeImage$Format_1;
      this.width = int_1;
      this.height = int_2;
      this.isStbImage = boolean_1;
      this.pointer = long_1;
      this.sizeBytes = int_1 * int_2 * nativeImage$Format_1.getChannelCount();
   }

   public String toString() {
      return "NativeImage[" + this.format + " " + this.width + "x" + this.height + "@" + this.pointer + (this.isStbImage ? "S" : "N") + "]";
   }

   public static NativeImage read(InputStream inputStream_1) throws IOException {
      return read(NativeImage.Format.RGBA, inputStream_1);
   }

   public static NativeImage read(@Nullable NativeImage.Format nativeImage$Format_1, InputStream inputStream_1) throws IOException {
      ByteBuffer byteBuffer_1 = null;

      NativeImage var3;
      try {
         byteBuffer_1 = class_4536.readResource(inputStream_1);
         byteBuffer_1.rewind();
         var3 = read(nativeImage$Format_1, byteBuffer_1);
      } finally {
         MemoryUtil.memFree(byteBuffer_1);
         IOUtils.closeQuietly(inputStream_1);
      }

      return var3;
   }

   public static NativeImage read(ByteBuffer byteBuffer_1) throws IOException {
      return read(NativeImage.Format.RGBA, byteBuffer_1);
   }

   public static NativeImage read(@Nullable NativeImage.Format nativeImage$Format_1, ByteBuffer byteBuffer_1) throws IOException {
      if (nativeImage$Format_1 != null && !nativeImage$Format_1.isWriteable()) {
         throw new UnsupportedOperationException("Don't know how to read format " + nativeImage$Format_1);
      } else if (MemoryUtil.memAddress(byteBuffer_1) == 0L) {
         throw new IllegalArgumentException("Invalid buffer");
      } else {
         MemoryStack memoryStack_1 = MemoryStack.stackPush();
         Throwable var3 = null;

         NativeImage var8;
         try {
            IntBuffer intBuffer_1 = memoryStack_1.mallocInt(1);
            IntBuffer intBuffer_2 = memoryStack_1.mallocInt(1);
            IntBuffer intBuffer_3 = memoryStack_1.mallocInt(1);
            ByteBuffer byteBuffer_2 = STBImage.stbi_load_from_memory(byteBuffer_1, intBuffer_1, intBuffer_2, intBuffer_3, nativeImage$Format_1 == null ? 0 : nativeImage$Format_1.channelCount);
            if (byteBuffer_2 == null) {
               throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }

            var8 = new NativeImage(nativeImage$Format_1 == null ? NativeImage.Format.getFormat(intBuffer_3.get(0)) : nativeImage$Format_1, intBuffer_1.get(0), intBuffer_2.get(0), true, MemoryUtil.memAddress(byteBuffer_2));
         } catch (Throwable var17) {
            var3 = var17;
            throw var17;
         } finally {
            if (memoryStack_1 != null) {
               if (var3 != null) {
                  try {
                     memoryStack_1.close();
                  } catch (Throwable var16) {
                     var3.addSuppressed(var16);
                  }
               } else {
                  memoryStack_1.close();
               }
            }

         }

         return var8;
      }
   }

   private static void setTextureClamp(boolean boolean_1) {
      if (boolean_1) {
         RenderSystem.texParameter(3553, 10242, 10496);
         RenderSystem.texParameter(3553, 10243, 10496);
      } else {
         RenderSystem.texParameter(3553, 10242, 10497);
         RenderSystem.texParameter(3553, 10243, 10497);
      }

   }

   private static void setTextureFilter(boolean boolean_1, boolean boolean_2) {
      if (boolean_1) {
         RenderSystem.texParameter(3553, 10241, boolean_2 ? 9987 : 9729);
         RenderSystem.texParameter(3553, 10240, 9729);
      } else {
         RenderSystem.texParameter(3553, 10241, boolean_2 ? 9986 : 9728);
         RenderSystem.texParameter(3553, 10240, 9728);
      }

   }

   private void checkAllocated() {
      if (this.pointer == 0L) {
         throw new IllegalStateException("Image is not allocated.");
      }
   }

   public void close() {
      if (this.pointer != 0L) {
         if (this.isStbImage) {
            STBImage.nstbi_image_free(this.pointer);
         } else {
            MemoryUtil.nmemFree(this.pointer);
         }
      }

      this.pointer = 0L;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public NativeImage.Format getFormat() {
      return this.format;
   }

   public int getPixelRGBA(int int_1, int int_2) {
      if (this.format != NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.format));
      } else if (int_1 <= this.width && int_2 <= this.height) {
         this.checkAllocated();
         return MemoryUtil.memIntBuffer(this.pointer, this.sizeBytes).get(int_1 + int_2 * this.width);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", int_1, int_2, this.width, this.height));
      }
   }

   public void setPixelRGBA(int int_1, int int_2, int int_3) {
      if (this.format != NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.format));
      } else if (int_1 <= this.width && int_2 <= this.height) {
         this.checkAllocated();
         MemoryUtil.memIntBuffer(this.pointer, this.sizeBytes).put(int_1 + int_2 * this.width, int_3);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", int_1, int_2, this.width, this.height));
      }
   }

   public byte getPixelOpacity(int int_1, int int_2) {
      if (!this.format.hasOpacityChannel()) {
         throw new IllegalArgumentException(String.format("no luminance or alpha in %s", this.format));
      } else if (int_1 <= this.width && int_2 <= this.height) {
         return MemoryUtil.memByteBuffer(this.pointer, this.sizeBytes).get((int_1 + int_2 * this.width) * this.format.getChannelCount() + this.format.getOpacityOffset() / 8);
      } else {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", int_1, int_2, this.width, this.height));
      }
   }

   public void blendPixel(int int_1, int int_2, int int_3) {
      if (this.format != NativeImage.Format.RGBA) {
         throw new UnsupportedOperationException("Can only call blendPixel with RGBA format");
      } else {
         int int_4 = this.getPixelRGBA(int_1, int_2);
         float float_1 = (float)(int_3 >> 24 & 255) / 255.0F;
         float float_2 = (float)(int_3 >> 16 & 255) / 255.0F;
         float float_3 = (float)(int_3 >> 8 & 255) / 255.0F;
         float float_4 = (float)(int_3 >> 0 & 255) / 255.0F;
         float float_5 = (float)(int_4 >> 24 & 255) / 255.0F;
         float float_6 = (float)(int_4 >> 16 & 255) / 255.0F;
         float float_7 = (float)(int_4 >> 8 & 255) / 255.0F;
         float float_8 = (float)(int_4 >> 0 & 255) / 255.0F;
         float float_10 = 1.0F - float_1;
         float float_11 = float_1 * float_1 + float_5 * float_10;
         float float_12 = float_2 * float_1 + float_6 * float_10;
         float float_13 = float_3 * float_1 + float_7 * float_10;
         float float_14 = float_4 * float_1 + float_8 * float_10;
         if (float_11 > 1.0F) {
            float_11 = 1.0F;
         }

         if (float_12 > 1.0F) {
            float_12 = 1.0F;
         }

         if (float_13 > 1.0F) {
            float_13 = 1.0F;
         }

         if (float_14 > 1.0F) {
            float_14 = 1.0F;
         }

         int int_5 = (int)(float_11 * 255.0F);
         int int_6 = (int)(float_12 * 255.0F);
         int int_7 = (int)(float_13 * 255.0F);
         int int_8 = (int)(float_14 * 255.0F);
         this.setPixelRGBA(int_1, int_2, int_5 << 24 | int_6 << 16 | int_7 << 8 | int_8 << 0);
      }
   }

   @Deprecated
   public int[] makePixelArray() {
      if (this.format != NativeImage.Format.RGBA) {
         throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
      } else {
         this.checkAllocated();
         int[] ints_1 = new int[this.getWidth() * this.getHeight()];

         for(int int_1 = 0; int_1 < this.getHeight(); ++int_1) {
            for(int int_2 = 0; int_2 < this.getWidth(); ++int_2) {
               int int_3 = this.getPixelRGBA(int_2, int_1);
               int int_4 = int_3 >> 24 & 255;
               int int_5 = int_3 >> 16 & 255;
               int int_6 = int_3 >> 8 & 255;
               int int_7 = int_3 >> 0 & 255;
               int int_8 = int_4 << 24 | int_7 << 16 | int_6 << 8 | int_5;
               ints_1[int_2 + int_1 * this.getWidth()] = int_8;
            }
         }

         return ints_1;
      }
   }

   public void upload(int int_1, int int_2, int int_3, boolean boolean_1) {
      this.upload(int_1, int_2, int_3, 0, 0, this.width, this.height, boolean_1);
   }

   public void upload(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, boolean boolean_1) {
      this.upload(int_1, int_2, int_3, int_4, int_5, int_6, int_7, false, false, boolean_1);
   }

   public void upload(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, boolean boolean_1, boolean boolean_2, boolean boolean_3) {
      this.checkAllocated();
      setTextureFilter(boolean_1, boolean_3);
      setTextureClamp(boolean_2);
      if (int_6 == this.getWidth()) {
         RenderSystem.pixelStore(3314, 0);
      } else {
         RenderSystem.pixelStore(3314, this.getWidth());
      }

      RenderSystem.pixelStore(3316, int_4);
      RenderSystem.pixelStore(3315, int_5);
      this.format.setUnpackAlignment();
      RenderSystem.texSubImage2D(3553, int_1, int_2, int_3, int_6, int_7, this.format.getPixelDataFormat(), 5121, this.pointer);
   }

   public void loadFromTextureImage(int int_1, boolean boolean_1) {
      this.checkAllocated();
      this.format.setPackAlignment();
      RenderSystem.getTexImage(3553, int_1, this.format.getPixelDataFormat(), 5121, this.pointer);
      if (boolean_1 && this.format.hasAlphaChannel()) {
         for(int int_2 = 0; int_2 < this.getHeight(); ++int_2) {
            for(int int_3 = 0; int_3 < this.getWidth(); ++int_3) {
               this.setPixelRGBA(int_3, int_2, this.getPixelRGBA(int_3, int_2) | 255 << this.format.getAlphaChannelOffset());
            }
         }
      }

   }

   public void writeFile(File file_1) throws IOException {
      this.writeFile(file_1.toPath());
   }

   public void makeGlyphBitmapSubpixel(STBTTFontinfo sTBTTFontinfo_1, int int_1, int int_2, int int_3, float float_1, float float_2, float float_3, float float_4, int int_4, int int_5) {
      if (int_4 >= 0 && int_4 + int_2 <= this.getWidth() && int_5 >= 0 && int_5 + int_3 <= this.getHeight()) {
         if (this.format.getChannelCount() != 1) {
            throw new IllegalArgumentException("Can only write fonts into 1-component images.");
         } else {
            STBTruetype.nstbtt_MakeGlyphBitmapSubpixel(sTBTTFontinfo_1.address(), this.pointer + (long)int_4 + (long)(int_5 * this.getWidth()), int_2, int_3, this.getWidth(), float_1, float_2, float_3, float_4, int_1);
         }
      } else {
         throw new IllegalArgumentException(String.format("Out of bounds: start: (%s, %s) (size: %sx%s); size: %sx%s", int_4, int_5, int_2, int_3, this.getWidth(), this.getHeight()));
      }
   }

   public void writeFile(Path path_1) throws IOException {
      if (!this.format.isWriteable()) {
         throw new UnsupportedOperationException("Don't know how to write format " + this.format);
      } else {
         this.checkAllocated();
         WritableByteChannel writableByteChannel_1 = Files.newByteChannel(path_1, WRITE_TO_FILE_OPEN_OPTIONS);
         Throwable var3 = null;

         try {
            NativeImage.WriteCallback nativeImage$WriteCallback_1 = new NativeImage.WriteCallback(writableByteChannel_1);

            try {
               if (!STBImageWrite.stbi_write_png_to_func(nativeImage$WriteCallback_1, 0L, this.getWidth(), this.getHeight(), this.format.getChannelCount(), MemoryUtil.memByteBuffer(this.pointer, this.sizeBytes), 0)) {
                  throw new IOException("Could not write image to the PNG file \"" + path_1.toAbsolutePath() + "\": " + STBImage.stbi_failure_reason());
               }
            } finally {
               nativeImage$WriteCallback_1.free();
            }

            nativeImage$WriteCallback_1.throwStoredException();
         } catch (Throwable var19) {
            var3 = var19;
            throw var19;
         } finally {
            if (writableByteChannel_1 != null) {
               if (var3 != null) {
                  try {
                     writableByteChannel_1.close();
                  } catch (Throwable var17) {
                     var3.addSuppressed(var17);
                  }
               } else {
                  writableByteChannel_1.close();
               }
            }

         }

      }
   }

   public void copyFrom(NativeImage nativeImage_1) {
      if (nativeImage_1.getFormat() != this.format) {
         throw new UnsupportedOperationException("Image formats don't match.");
      } else {
         int int_1 = this.format.getChannelCount();
         this.checkAllocated();
         nativeImage_1.checkAllocated();
         if (this.width == nativeImage_1.width) {
            MemoryUtil.memCopy(nativeImage_1.pointer, this.pointer, (long)Math.min(this.sizeBytes, nativeImage_1.sizeBytes));
         } else {
            int int_2 = Math.min(this.getWidth(), nativeImage_1.getWidth());
            int int_3 = Math.min(this.getHeight(), nativeImage_1.getHeight());

            for(int int_4 = 0; int_4 < int_3; ++int_4) {
               int int_5 = int_4 * nativeImage_1.getWidth() * int_1;
               int int_6 = int_4 * this.getWidth() * int_1;
               MemoryUtil.memCopy(nativeImage_1.pointer + (long)int_5, this.pointer + (long)int_6, (long)int_2);
            }
         }

      }
   }

   public void fillRect(int int_1, int int_2, int int_3, int int_4, int int_5) {
      for(int int_6 = int_2; int_6 < int_2 + int_4; ++int_6) {
         for(int int_7 = int_1; int_7 < int_1 + int_3; ++int_7) {
            this.setPixelRGBA(int_7, int_6, int_5);
         }
      }

   }

   public void copyRect(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, boolean boolean_1, boolean boolean_2) {
      for(int int_7 = 0; int_7 < int_6; ++int_7) {
         for(int int_8 = 0; int_8 < int_5; ++int_8) {
            int int_9 = boolean_1 ? int_5 - 1 - int_8 : int_8;
            int int_10 = boolean_2 ? int_6 - 1 - int_7 : int_7;
            int int_11 = this.getPixelRGBA(int_1 + int_8, int_2 + int_7);
            this.setPixelRGBA(int_1 + int_3 + int_9, int_2 + int_4 + int_10, int_11);
         }
      }

   }

   public void method_4319() {
      this.checkAllocated();
      MemoryStack memoryStack_1 = MemoryStack.stackPush();
      Throwable var2 = null;

      try {
         int int_1 = this.format.getChannelCount();
         int int_2 = this.getWidth() * int_1;
         long long_1 = memoryStack_1.nmalloc(int_2);

         for(int int_3 = 0; int_3 < this.getHeight() / 2; ++int_3) {
            int int_4 = int_3 * this.getWidth() * int_1;
            int int_5 = (this.getHeight() - 1 - int_3) * this.getWidth() * int_1;
            MemoryUtil.memCopy(this.pointer + (long)int_4, long_1, (long)int_2);
            MemoryUtil.memCopy(this.pointer + (long)int_5, this.pointer + (long)int_4, (long)int_2);
            MemoryUtil.memCopy(long_1, this.pointer + (long)int_5, (long)int_2);
         }
      } catch (Throwable var17) {
         var2 = var17;
         throw var17;
      } finally {
         if (memoryStack_1 != null) {
            if (var2 != null) {
               try {
                  memoryStack_1.close();
               } catch (Throwable var16) {
                  var2.addSuppressed(var16);
               }
            } else {
               memoryStack_1.close();
            }
         }

      }

   }

   public void resizeSubRectTo(int int_1, int int_2, int int_3, int int_4, NativeImage nativeImage_1) {
      this.checkAllocated();
      if (nativeImage_1.getFormat() != this.format) {
         throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
      } else {
         int int_5 = this.format.getChannelCount();
         STBImageResize.nstbir_resize_uint8(this.pointer + (long)((int_1 + int_2 * this.getWidth()) * int_5), int_3, int_4, this.getWidth() * int_5, nativeImage_1.pointer, nativeImage_1.getWidth(), nativeImage_1.getHeight(), 0, int_5);
      }
   }

   public void untrack() {
      Untracker.untrack(this.pointer);
   }

   public static NativeImage read(String string_1) throws IOException {
      MemoryStack memoryStack_1 = MemoryStack.stackPush();
      Throwable var2 = null;

      NativeImage var6;
      try {
         ByteBuffer byteBuffer_1 = memoryStack_1.UTF8(string_1.replaceAll("\n", ""), false);
         ByteBuffer byteBuffer_2 = Base64.getDecoder().decode(byteBuffer_1);
         ByteBuffer byteBuffer_3 = memoryStack_1.malloc(byteBuffer_2.remaining());
         byteBuffer_3.put(byteBuffer_2);
         byteBuffer_3.rewind();
         var6 = read(byteBuffer_3);
      } catch (Throwable var15) {
         var2 = var15;
         throw var15;
      } finally {
         if (memoryStack_1 != null) {
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

      return var6;
   }

   static {
      WRITE_TO_FILE_OPEN_OPTIONS = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
   }

   @Environment(EnvType.CLIENT)
   public static enum Format {
      RGBA(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
      RGB(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
      LUMINANCE_ALPHA(2, 6410, false, false, false, true, true, 255, 255, 255, 0, 8, true),
      LUMINANCE(1, 6409, false, false, false, true, false, 0, 0, 0, 0, 255, true);

      private final int channelCount;
      private final int pixelDataFormat;
      private final boolean hasRed;
      private final boolean hasGreen;
      private final boolean hasBlue;
      private final boolean hasLuminance;
      private final boolean hasAlpha;
      private final int redOffset;
      private final int greenOffset;
      private final int blueOffset;
      private final int luminanceChannelOffset;
      private final int alphaChannelOffset;
      private final boolean writeable;

      private Format(int int_1, int int_2, boolean boolean_1, boolean boolean_2, boolean boolean_3, boolean boolean_4, boolean boolean_5, int int_3, int int_4, int int_5, int int_6, int int_7, boolean boolean_6) {
         this.channelCount = int_1;
         this.pixelDataFormat = int_2;
         this.hasRed = boolean_1;
         this.hasGreen = boolean_2;
         this.hasBlue = boolean_3;
         this.hasLuminance = boolean_4;
         this.hasAlpha = boolean_5;
         this.redOffset = int_3;
         this.greenOffset = int_4;
         this.blueOffset = int_5;
         this.luminanceChannelOffset = int_6;
         this.alphaChannelOffset = int_7;
         this.writeable = boolean_6;
      }

      public int getChannelCount() {
         return this.channelCount;
      }

      public void setPackAlignment() {
         RenderSystem.pixelStore(3333, this.getChannelCount());
      }

      public void setUnpackAlignment() {
         RenderSystem.pixelStore(3317, this.getChannelCount());
      }

      public int getPixelDataFormat() {
         return this.pixelDataFormat;
      }

      public boolean hasAlphaChannel() {
         return this.hasAlpha;
      }

      public int getAlphaChannelOffset() {
         return this.alphaChannelOffset;
      }

      public boolean hasOpacityChannel() {
         return this.hasLuminance || this.hasAlpha;
      }

      public int getOpacityOffset() {
         return this.hasLuminance ? this.luminanceChannelOffset : this.alphaChannelOffset;
      }

      public boolean isWriteable() {
         return this.writeable;
      }

      private static NativeImage.Format getFormat(int int_1) {
         switch(int_1) {
         case 1:
            return LUMINANCE;
         case 2:
            return LUMINANCE_ALPHA;
         case 3:
            return RGB;
         case 4:
         default:
            return RGBA;
         }
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum GLFormat {
      RGBA(6408),
      RGB(6407),
      LUMINANCE_ALPHA(6410),
      LUMINANCE(6409),
      INTENSITY(32841);

      private final int glConstant;

      private GLFormat(int int_1) {
         this.glConstant = int_1;
      }

      int getGlConstant() {
         return this.glConstant;
      }
   }

   @Environment(EnvType.CLIENT)
   static class WriteCallback extends STBIWriteCallback {
      private final WritableByteChannel channel;
      private IOException exception;

      private WriteCallback(WritableByteChannel writableByteChannel_1) {
         this.channel = writableByteChannel_1;
      }

      public void invoke(long long_1, long long_2, int int_1) {
         ByteBuffer byteBuffer_1 = getData(long_2, int_1);

         try {
            this.channel.write(byteBuffer_1);
         } catch (IOException var8) {
            this.exception = var8;
         }

      }

      public void throwStoredException() throws IOException {
         if (this.exception != null) {
            throw this.exception;
         }
      }

      // $FF: synthetic method
      WriteCallback(WritableByteChannel writableByteChannel_1, Object nativeImage$1_1) {
         this(writableByteChannel_1);
      }
   }
}
