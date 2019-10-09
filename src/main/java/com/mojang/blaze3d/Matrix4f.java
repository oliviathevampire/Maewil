package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.nio.FloatBuffer;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
public final class Matrix4f {
   private final float[] components;

   public Matrix4f() {
      this.components = new float[16];
   }

   public Matrix4f(Quaternion quaternion_1) {
      this();
      float float_1 = quaternion_1.getX();
      float float_2 = quaternion_1.getY();
      float float_3 = quaternion_1.getZ();
      float float_4 = quaternion_1.getW();
      float float_5 = 2.0F * float_1 * float_1;
      float float_6 = 2.0F * float_2 * float_2;
      float float_7 = 2.0F * float_3 * float_3;
      this.components[0] = 1.0F - float_6 - float_7;
      this.components[5] = 1.0F - float_7 - float_5;
      this.components[10] = 1.0F - float_5 - float_6;
      this.components[15] = 1.0F;
      float float_8 = float_1 * float_2;
      float float_9 = float_2 * float_3;
      float float_10 = float_3 * float_1;
      float float_11 = float_1 * float_4;
      float float_12 = float_2 * float_4;
      float float_13 = float_3 * float_4;
      this.components[1] = 2.0F * (float_8 + float_13);
      this.components[4] = 2.0F * (float_8 - float_13);
      this.components[2] = 2.0F * (float_10 - float_12);
      this.components[8] = 2.0F * (float_10 + float_12);
      this.components[6] = 2.0F * (float_9 + float_11);
      this.components[9] = 2.0F * (float_9 - float_11);
   }

   public boolean equals(Object object_1) {
      if (this == object_1) {
         return true;
      } else if (object_1 != null && this.getClass() == object_1.getClass()) {
         Matrix4f matrix4f_1 = (Matrix4f)object_1;
         return Arrays.equals(this.components, matrix4f_1.components);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.components);
   }

   public void setFromBuffer(FloatBuffer floatBuffer_1) {
      this.setFromBuffer(floatBuffer_1, false);
   }

   public void setFromBuffer(FloatBuffer floatBuffer_1, boolean boolean_1) {
      if (boolean_1) {
         for(int int_1 = 0; int_1 < 4; ++int_1) {
            for(int int_2 = 0; int_2 < 4; ++int_2) {
               this.components[int_1 * 4 + int_2] = floatBuffer_1.get(int_2 * 4 + int_1);
            }
         }
      } else {
         floatBuffer_1.get(this.components);
      }

   }

   public String toString() {
      StringBuilder stringBuilder_1 = new StringBuilder();
      stringBuilder_1.append("Matrix4f:\n");

      for(int int_1 = 0; int_1 < 4; ++int_1) {
         for(int int_2 = 0; int_2 < 4; ++int_2) {
            stringBuilder_1.append(this.components[int_1 + int_2 * 4]);
            if (int_2 != 3) {
               stringBuilder_1.append(" ");
            }
         }

         stringBuilder_1.append("\n");
      }

      return stringBuilder_1.toString();
   }

   public void putIntoBuffer(FloatBuffer floatBuffer_1) {
      this.putIntoBuffer(floatBuffer_1, false);
   }

   public void putIntoBuffer(FloatBuffer floatBuffer_1, boolean boolean_1) {
      if (boolean_1) {
         for(int int_1 = 0; int_1 < 4; ++int_1) {
            for(int int_2 = 0; int_2 < 4; ++int_2) {
               floatBuffer_1.put(int_2 * 4 + int_1, this.components[int_1 * 4 + int_2]);
            }
         }
      } else {
         floatBuffer_1.put(this.components);
      }

   }

   public void set(int int_1, int int_2, float float_1) {
      this.components[int_1 + 4 * int_2] = float_1;
   }

   public static Matrix4f method_4929(double double_1, float float_1, float float_2, float float_3) {
      float float_4 = (float)(1.0D / Math.tan(double_1 * 0.01745329238474369D / 2.0D));
      Matrix4f matrix4f_1 = new Matrix4f();
      matrix4f_1.set(0, 0, float_4 / float_1);
      matrix4f_1.set(1, 1, float_4);
      matrix4f_1.set(2, 2, (float_3 + float_2) / (float_2 - float_3));
      matrix4f_1.set(3, 2, -1.0F);
      matrix4f_1.set(2, 3, 2.0F * float_3 * float_2 / (float_2 - float_3));
      return matrix4f_1;
   }

   public static Matrix4f projectionMatrix(float float_1, float float_2, float float_3, float float_4) {
      Matrix4f matrix4f_1 = new Matrix4f();
      matrix4f_1.set(0, 0, 2.0F / float_1);
      matrix4f_1.set(1, 1, 2.0F / float_2);
      float float_5 = float_4 - float_3;
      matrix4f_1.set(2, 2, -2.0F / float_5);
      matrix4f_1.set(3, 3, 1.0F);
      matrix4f_1.set(0, 3, -1.0F);
      matrix4f_1.set(1, 3, -1.0F);
      matrix4f_1.set(2, 3, -(float_4 + float_3) / float_5);
      return matrix4f_1;
   }
}
