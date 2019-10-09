package com.mojang.blaze3d;

import com.google.common.primitives.Floats;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.*;
import java.util.Arrays;
import java.util.BitSet;

@Environment(EnvType.CLIENT)
public class BufferBuilder {
   private static final Logger LOGGER = LogManager.getLogger();
   private ByteBuffer bufByte;
   private IntBuffer bufInt;
   private ShortBuffer bufShort;
   private FloatBuffer bufFloat;
   private int vertexCount;
   private VertexFormatElement currentElement;
   private int currentElementId;
   private boolean colorDisabled;
   private int drawMode;
   private double offsetX;
   private double offsetY;
   private double offsetZ;
   private VertexFormat format;
   private boolean building;

   public BufferBuilder(int int_1) {
      this.bufByte = GlAllocationUtils.allocateByteBuffer(int_1 * 4);
      this.bufInt = this.bufByte.asIntBuffer();
      this.bufShort = this.bufByte.asShortBuffer();
      this.bufFloat = this.bufByte.asFloatBuffer();
   }

   private void grow(int int_1) {
      if (this.vertexCount * this.format.getVertexSize() + int_1 > this.bufByte.capacity()) {
         int int_2 = this.bufByte.capacity();
         int int_3 = int_2 + roundBufferSize(int_1);
         LOGGER.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", int_2, int_3);
         int int_4 = this.bufInt.position();
         ByteBuffer byteBuffer_1 = GlAllocationUtils.allocateByteBuffer(int_3);
         this.bufByte.position(0);
         byteBuffer_1.put(this.bufByte);
         byteBuffer_1.rewind();
         this.bufByte = byteBuffer_1;
         this.bufFloat = this.bufByte.asFloatBuffer().asReadOnlyBuffer();
         this.bufInt = this.bufByte.asIntBuffer();
         this.bufInt.position(int_4);
         this.bufShort = this.bufByte.asShortBuffer();
         this.bufShort.position(int_4 << 1);
      }
   }

   private static int roundBufferSize(int int_1) {
      int int_2 = 2097152;
      if (int_1 == 0) {
         return int_2;
      } else {
         if (int_1 < 0) {
            int_2 *= -1;
         }

         int int_3 = int_1 % int_2;
         return int_3 == 0 ? int_1 : int_1 + int_2 - int_3;
      }
   }

   public void sortQuads(float float_1, float float_2, float float_3) {
      int int_1 = this.vertexCount / 4;
      float[] floats_1 = new float[int_1];

      for(int int_2 = 0; int_2 < int_1; ++int_2) {
         floats_1[int_2] = getDistanceSq(this.bufFloat, (float)((double)float_1 + this.offsetX), (float)((double)float_2 + this.offsetY), (float)((double)float_3 + this.offsetZ), this.format.getVertexSizeInteger(), int_2 * this.format.getVertexSize());
      }

      Integer[] integers_1 = new Integer[int_1];

      for(int int_3 = 0; int_3 < integers_1.length; ++int_3) {
         integers_1[int_3] = int_3;
      }

      Arrays.sort(integers_1, (integer_1, integer_2) -> {
         return Floats.compare(floats_1[integer_2], floats_1[integer_1]);
      });
      BitSet bitSet_1 = new BitSet();
      int int_4 = this.format.getVertexSize();
      int[] ints_1 = new int[int_4];

      for(int int_5 = bitSet_1.nextClearBit(0); int_5 < integers_1.length; int_5 = bitSet_1.nextClearBit(int_5 + 1)) {
         int int_6 = integers_1[int_5];
         if (int_6 != int_5) {
            this.bufInt.limit(int_6 * int_4 + int_4);
            this.bufInt.position(int_6 * int_4);
            this.bufInt.get(ints_1);
            int int_7 = int_6;

            for(int int_8 = integers_1[int_6]; int_7 != int_5; int_8 = integers_1[int_8]) {
               this.bufInt.limit(int_8 * int_4 + int_4);
               this.bufInt.position(int_8 * int_4);
               IntBuffer intBuffer_1 = this.bufInt.slice();
               this.bufInt.limit(int_7 * int_4 + int_4);
               this.bufInt.position(int_7 * int_4);
               this.bufInt.put(intBuffer_1);
               bitSet_1.set(int_7);
               int_7 = int_8;
            }

            this.bufInt.limit(int_5 * int_4 + int_4);
            this.bufInt.position(int_5 * int_4);
            this.bufInt.put(ints_1);
         }

         bitSet_1.set(int_5);
      }

   }

   public BufferBuilder.State toBufferState() {
      this.bufInt.rewind();
      int int_1 = this.getCurrentSize();
      this.bufInt.limit(int_1);
      int[] ints_1 = new int[int_1];
      this.bufInt.get(ints_1);
      this.bufInt.limit(this.bufInt.capacity());
      this.bufInt.position(int_1);
      return new BufferBuilder.State(ints_1, new VertexFormat(this.format));
   }

   private int getCurrentSize() {
      return this.vertexCount * this.format.getVertexSizeInteger();
   }

   private static float getDistanceSq(FloatBuffer floatBuffer_1, float float_1, float float_2, float float_3, int int_1, int int_2) {
      float float_4 = floatBuffer_1.get(int_2 + int_1 * 0 + 0);
      float float_5 = floatBuffer_1.get(int_2 + int_1 * 0 + 1);
      float float_6 = floatBuffer_1.get(int_2 + int_1 * 0 + 2);
      float float_7 = floatBuffer_1.get(int_2 + int_1 * 1 + 0);
      float float_8 = floatBuffer_1.get(int_2 + int_1 * 1 + 1);
      float float_9 = floatBuffer_1.get(int_2 + int_1 * 1 + 2);
      float float_10 = floatBuffer_1.get(int_2 + int_1 * 2 + 0);
      float float_11 = floatBuffer_1.get(int_2 + int_1 * 2 + 1);
      float float_12 = floatBuffer_1.get(int_2 + int_1 * 2 + 2);
      float float_13 = floatBuffer_1.get(int_2 + int_1 * 3 + 0);
      float float_14 = floatBuffer_1.get(int_2 + int_1 * 3 + 1);
      float float_15 = floatBuffer_1.get(int_2 + int_1 * 3 + 2);
      float float_16 = (float_4 + float_7 + float_10 + float_13) * 0.25F - float_1;
      float float_17 = (float_5 + float_8 + float_11 + float_14) * 0.25F - float_2;
      float float_18 = (float_6 + float_9 + float_12 + float_15) * 0.25F - float_3;
      return float_16 * float_16 + float_17 * float_17 + float_18 * float_18;
   }

   public void restoreState(BufferBuilder.State bufferBuilder$State_1) {
      this.bufInt.clear();
      this.grow(bufferBuilder$State_1.getRawBuffer().length * 4);
      this.bufInt.put(bufferBuilder$State_1.getRawBuffer());
      this.vertexCount = bufferBuilder$State_1.getVertexCount();
      this.format = new VertexFormat(bufferBuilder$State_1.getFormat());
   }

   public void clear() {
      this.vertexCount = 0;
      this.currentElement = null;
      this.currentElementId = 0;
   }

   public void begin(int int_1, VertexFormat vertexFormat_1) {
      if (this.building) {
         throw new IllegalStateException("Already building!");
      } else {
         this.building = true;
         this.clear();
         this.drawMode = int_1;
         this.format = vertexFormat_1;
         this.currentElement = vertexFormat_1.getElement(this.currentElementId);
         this.colorDisabled = false;
         this.bufByte.limit(this.bufByte.capacity());
      }
   }

   public BufferBuilder texture(double double_1, double double_2) {
      int int_1 = this.vertexCount * this.format.getVertexSize() + this.format.getElementOffset(this.currentElementId);
      switch(this.currentElement.getFormat()) {
      case FLOAT:
         this.bufByte.putFloat(int_1, (float)double_1);
         this.bufByte.putFloat(int_1 + 4, (float)double_2);
         break;
      case UINT:
      case INT:
         this.bufByte.putInt(int_1, (int)double_1);
         this.bufByte.putInt(int_1 + 4, (int)double_2);
         break;
      case USHORT:
      case SHORT:
         this.bufByte.putShort(int_1, (short)((int)double_2));
         this.bufByte.putShort(int_1 + 2, (short)((int)double_1));
         break;
      case UBYTE:
      case BYTE:
         this.bufByte.put(int_1, (byte)((int)double_2));
         this.bufByte.put(int_1 + 1, (byte)((int)double_1));
      }

      this.nextElement();
      return this;
   }

   public BufferBuilder texture(int int_1, int int_2) {
      int int_3 = this.vertexCount * this.format.getVertexSize() + this.format.getElementOffset(this.currentElementId);
      switch(this.currentElement.getFormat()) {
      case FLOAT:
         this.bufByte.putFloat(int_3, (float)int_1);
         this.bufByte.putFloat(int_3 + 4, (float)int_2);
         break;
      case UINT:
      case INT:
         this.bufByte.putInt(int_3, int_1);
         this.bufByte.putInt(int_3 + 4, int_2);
         break;
      case USHORT:
      case SHORT:
         this.bufByte.putShort(int_3, (short)int_2);
         this.bufByte.putShort(int_3 + 2, (short)int_1);
         break;
      case UBYTE:
      case BYTE:
         this.bufByte.put(int_3, (byte)int_2);
         this.bufByte.put(int_3 + 1, (byte)int_1);
      }

      this.nextElement();
      return this;
   }

   public void brightness(int int_1, int int_2, int int_3, int int_4) {
      int int_5 = (this.vertexCount - 4) * this.format.getVertexSizeInteger() + this.format.getUvOffset(1) / 4;
      int int_6 = this.format.getVertexSize() >> 2;
      this.bufInt.put(int_5, int_1);
      this.bufInt.put(int_5 + int_6, int_2);
      this.bufInt.put(int_5 + int_6 * 2, int_3);
      this.bufInt.put(int_5 + int_6 * 3, int_4);
   }

   public void postPosition(double double_1, double double_2, double double_3) {
      int int_1 = this.format.getVertexSizeInteger();
      int int_2 = (this.vertexCount - 4) * int_1;

      for(int int_3 = 0; int_3 < 4; ++int_3) {
         int int_4 = int_2 + int_3 * int_1;
         int int_5 = int_4 + 1;
         int int_6 = int_5 + 1;
         this.bufInt.put(int_4, Float.floatToRawIntBits((float)(double_1 + this.offsetX) + Float.intBitsToFloat(this.bufInt.get(int_4))));
         this.bufInt.put(int_5, Float.floatToRawIntBits((float)(double_2 + this.offsetY) + Float.intBitsToFloat(this.bufInt.get(int_5))));
         this.bufInt.put(int_6, Float.floatToRawIntBits((float)(double_3 + this.offsetZ) + Float.intBitsToFloat(this.bufInt.get(int_6))));
      }

   }

   private int getColorIndex(int int_1) {
      return ((this.vertexCount - int_1) * this.format.getVertexSize() + this.format.getColorOffset()) / 4;
   }

   public void multiplyColor(float float_1, float float_2, float float_3, int int_1) {
      int int_2 = this.getColorIndex(int_1);
      int int_3 = -1;
      if (!this.colorDisabled) {
         int_3 = this.bufInt.get(int_2);
         int int_4;
         int int_5;
         int int_6;
         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            int_4 = (int)((float)(int_3 & 255) * float_1);
            int_5 = (int)((float)(int_3 >> 8 & 255) * float_2);
            int_6 = (int)((float)(int_3 >> 16 & 255) * float_3);
            int_3 &= -16777216;
            int_3 |= int_6 << 16 | int_5 << 8 | int_4;
         } else {
            int_4 = (int)((float)(int_3 >> 24 & 255) * float_1);
            int_5 = (int)((float)(int_3 >> 16 & 255) * float_2);
            int_6 = (int)((float)(int_3 >> 8 & 255) * float_3);
            int_3 &= 255;
            int_3 |= int_4 << 24 | int_5 << 16 | int_6 << 8;
         }
      }

      this.bufInt.put(int_2, int_3);
   }

   private void setColor(int int_1, int int_2) {
      int int_3 = this.getColorIndex(int_2);
      int int_4 = int_1 >> 16 & 255;
      int int_5 = int_1 >> 8 & 255;
      int int_6 = int_1 & 255;
      this.setColor(int_3, int_4, int_5, int_6);
   }

   public void setColor(float float_1, float float_2, float float_3, int int_1) {
      int int_2 = this.getColorIndex(int_1);
      int int_3 = clamp((int)(float_1 * 255.0F), 0, 255);
      int int_4 = clamp((int)(float_2 * 255.0F), 0, 255);
      int int_5 = clamp((int)(float_3 * 255.0F), 0, 255);
      this.setColor(int_2, int_3, int_4, int_5);
   }

   private static int clamp(int int_1, int int_2, int int_3) {
      if (int_1 < int_2) {
         return int_2;
      } else {
         return int_1 > int_3 ? int_3 : int_1;
      }
   }

   private void setColor(int int_1, int int_2, int int_3, int int_4) {
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         this.bufInt.put(int_1, -16777216 | int_4 << 16 | int_3 << 8 | int_2);
      } else {
         this.bufInt.put(int_1, int_2 << 24 | int_3 << 16 | int_4 << 8 | 255);
      }

   }

   public void disableColor() {
      this.colorDisabled = true;
   }

   public BufferBuilder color(float float_1, float float_2, float float_3, float float_4) {
      return this.color((int)(float_1 * 255.0F), (int)(float_2 * 255.0F), (int)(float_3 * 255.0F), (int)(float_4 * 255.0F));
   }

   public BufferBuilder color(int int_1, int int_2, int int_3, int int_4) {
      if (this.colorDisabled) {
         return this;
      } else {
         int int_5 = this.vertexCount * this.format.getVertexSize() + this.format.getElementOffset(this.currentElementId);
         switch(this.currentElement.getFormat()) {
         case FLOAT:
            this.bufByte.putFloat(int_5, (float)int_1 / 255.0F);
            this.bufByte.putFloat(int_5 + 4, (float)int_2 / 255.0F);
            this.bufByte.putFloat(int_5 + 8, (float)int_3 / 255.0F);
            this.bufByte.putFloat(int_5 + 12, (float)int_4 / 255.0F);
            break;
         case UINT:
         case INT:
            this.bufByte.putFloat(int_5, (float)int_1);
            this.bufByte.putFloat(int_5 + 4, (float)int_2);
            this.bufByte.putFloat(int_5 + 8, (float)int_3);
            this.bufByte.putFloat(int_5 + 12, (float)int_4);
            break;
         case USHORT:
         case SHORT:
            this.bufByte.putShort(int_5, (short)int_1);
            this.bufByte.putShort(int_5 + 2, (short)int_2);
            this.bufByte.putShort(int_5 + 4, (short)int_3);
            this.bufByte.putShort(int_5 + 6, (short)int_4);
            break;
         case UBYTE:
         case BYTE:
            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
               this.bufByte.put(int_5, (byte)int_1);
               this.bufByte.put(int_5 + 1, (byte)int_2);
               this.bufByte.put(int_5 + 2, (byte)int_3);
               this.bufByte.put(int_5 + 3, (byte)int_4);
            } else {
               this.bufByte.put(int_5, (byte)int_4);
               this.bufByte.put(int_5 + 1, (byte)int_3);
               this.bufByte.put(int_5 + 2, (byte)int_2);
               this.bufByte.put(int_5 + 3, (byte)int_1);
            }
         }

         this.nextElement();
         return this;
      }
   }

   public void putVertexData(int[] ints_1) {
      this.grow(ints_1.length * 4 + this.format.getVertexSize());
      this.bufInt.position(this.getCurrentSize());
      this.bufInt.put(ints_1);
      this.vertexCount += ints_1.length / this.format.getVertexSizeInteger();
   }

   public void next() {
      ++this.vertexCount;
      this.grow(this.format.getVertexSize());
   }

   public BufferBuilder vertex(double double_1, double double_2, double double_3) {
      int int_1 = this.vertexCount * this.format.getVertexSize() + this.format.getElementOffset(this.currentElementId);
      switch(this.currentElement.getFormat()) {
      case FLOAT:
         this.bufByte.putFloat(int_1, (float)(double_1 + this.offsetX));
         this.bufByte.putFloat(int_1 + 4, (float)(double_2 + this.offsetY));
         this.bufByte.putFloat(int_1 + 8, (float)(double_3 + this.offsetZ));
         break;
      case UINT:
      case INT:
         this.bufByte.putInt(int_1, Float.floatToRawIntBits((float)(double_1 + this.offsetX)));
         this.bufByte.putInt(int_1 + 4, Float.floatToRawIntBits((float)(double_2 + this.offsetY)));
         this.bufByte.putInt(int_1 + 8, Float.floatToRawIntBits((float)(double_3 + this.offsetZ)));
         break;
      case USHORT:
      case SHORT:
         this.bufByte.putShort(int_1, (short)((int)(double_1 + this.offsetX)));
         this.bufByte.putShort(int_1 + 2, (short)((int)(double_2 + this.offsetY)));
         this.bufByte.putShort(int_1 + 4, (short)((int)(double_3 + this.offsetZ)));
         break;
      case UBYTE:
      case BYTE:
         this.bufByte.put(int_1, (byte)((int)(double_1 + this.offsetX)));
         this.bufByte.put(int_1 + 1, (byte)((int)(double_2 + this.offsetY)));
         this.bufByte.put(int_1 + 2, (byte)((int)(double_3 + this.offsetZ)));
      }

      this.nextElement();
      return this;
   }

   public void postNormal(float float_1, float float_2, float float_3) {
      int int_1 = (byte)((int)(float_1 * 127.0F)) & 255;
      int int_2 = (byte)((int)(float_2 * 127.0F)) & 255;
      int int_3 = (byte)((int)(float_3 * 127.0F)) & 255;
      int int_4 = int_1 | int_2 << 8 | int_3 << 16;
      int int_5 = this.format.getVertexSize() >> 2;
      int int_6 = (this.vertexCount - 4) * int_5 + this.format.getNormalOffset() / 4;
      this.bufInt.put(int_6, int_4);
      this.bufInt.put(int_6 + int_5, int_4);
      this.bufInt.put(int_6 + int_5 * 2, int_4);
      this.bufInt.put(int_6 + int_5 * 3, int_4);
   }

   private void nextElement() {
      ++this.currentElementId;
      this.currentElementId %= this.format.getElementCount();
      this.currentElement = this.format.getElement(this.currentElementId);
      if (this.currentElement.getType() == VertexFormatElement.Type.PADDING) {
         this.nextElement();
      }

   }

   public BufferBuilder normal(float float_1, float float_2, float float_3) {
      int int_1 = this.vertexCount * this.format.getVertexSize() + this.format.getElementOffset(this.currentElementId);
      switch(this.currentElement.getFormat()) {
      case FLOAT:
         this.bufByte.putFloat(int_1, float_1);
         this.bufByte.putFloat(int_1 + 4, float_2);
         this.bufByte.putFloat(int_1 + 8, float_3);
         break;
      case UINT:
      case INT:
         this.bufByte.putInt(int_1, (int)float_1);
         this.bufByte.putInt(int_1 + 4, (int)float_2);
         this.bufByte.putInt(int_1 + 8, (int)float_3);
         break;
      case USHORT:
      case SHORT:
         this.bufByte.putShort(int_1, (short)((int)float_1 * 32767 & '\uffff'));
         this.bufByte.putShort(int_1 + 2, (short)((int)float_2 * 32767 & '\uffff'));
         this.bufByte.putShort(int_1 + 4, (short)((int)float_3 * 32767 & '\uffff'));
         break;
      case UBYTE:
      case BYTE:
         this.bufByte.put(int_1, (byte)((int)float_1 * 127 & 255));
         this.bufByte.put(int_1 + 1, (byte)((int)float_2 * 127 & 255));
         this.bufByte.put(int_1 + 2, (byte)((int)float_3 * 127 & 255));
      }

      this.nextElement();
      return this;
   }

   public void setOffset(double double_1, double double_2, double double_3) {
      this.offsetX = double_1;
      this.offsetY = double_2;
      this.offsetZ = double_3;
   }

   public void end() {
      if (!this.building) {
         throw new IllegalStateException("Not building!");
      } else {
         this.building = false;
         this.bufByte.position(0);
         this.bufByte.limit(this.getCurrentSize() * 4);
      }
   }

   public ByteBuffer getByteBuffer() {
      return this.bufByte;
   }

   public VertexFormat getVertexFormat() {
      return this.format;
   }

   public int getVertexCount() {
      return this.vertexCount;
   }

   public int getDrawMode() {
      return this.drawMode;
   }

   public void setQuadColor(int int_1) {
      for(int int_2 = 0; int_2 < 4; ++int_2) {
         this.setColor(int_1, int_2 + 1);
      }

   }

   public void setQuadColor(float float_1, float float_2, float float_3) {
      for(int int_1 = 0; int_1 < 4; ++int_1) {
         this.setColor(float_1, float_2, float_3, int_1 + 1);
      }

   }

   @Environment(EnvType.CLIENT)
   public class State {
      private final int[] rawBuffer;
      private final VertexFormat format;

      public State(int[] ints_1, VertexFormat vertexFormat_1) {
         this.rawBuffer = ints_1;
         this.format = vertexFormat_1;
      }

      public int[] getRawBuffer() {
         return this.rawBuffer;
      }

      public int getVertexCount() {
         return this.rawBuffer.length / this.format.getVertexSizeInteger();
      }

      public VertexFormat getFormat() {
         return this.format;
      }
   }
}
