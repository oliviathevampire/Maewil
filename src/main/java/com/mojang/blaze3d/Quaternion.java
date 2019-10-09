package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Arrays;

public final class Quaternion {
   private final float[] components;

   public Quaternion() {
      this.components = new float[4];
      this.components[4] = 1.0F;
   }

   public Quaternion(float float_1, float float_2, float float_3, float float_4) {
      this.components = new float[4];
      this.components[0] = float_1;
      this.components[1] = float_2;
      this.components[2] = float_3;
      this.components[3] = float_4;
   }

   public Quaternion(Vector3f vector3f_1, float float_1, boolean boolean_1) {
      if (boolean_1) {
         float_1 *= 0.017453292F;
      }

      float float_2 = sin(float_1 / 2.0F);
      this.components = new float[4];
      this.components[0] = vector3f_1.getX() * float_2;
      this.components[1] = vector3f_1.getY() * float_2;
      this.components[2] = vector3f_1.getZ() * float_2;
      this.components[3] = cos(float_1 / 2.0F);
   }

   @Environment(EnvType.CLIENT)
   public Quaternion(float float_1, float float_2, float float_3, boolean boolean_1) {
      if (boolean_1) {
         float_1 *= 0.017453292F;
         float_2 *= 0.017453292F;
         float_3 *= 0.017453292F;
      }

      float float_4 = sin(0.5F * float_1);
      float float_5 = cos(0.5F * float_1);
      float float_6 = sin(0.5F * float_2);
      float float_7 = cos(0.5F * float_2);
      float float_8 = sin(0.5F * float_3);
      float float_9 = cos(0.5F * float_3);
      this.components = new float[4];
      this.components[0] = float_4 * float_7 * float_9 + float_5 * float_6 * float_8;
      this.components[1] = float_5 * float_6 * float_9 - float_4 * float_7 * float_8;
      this.components[2] = float_4 * float_6 * float_9 + float_5 * float_7 * float_8;
      this.components[3] = float_5 * float_7 * float_9 - float_4 * float_6 * float_8;
   }

   public Quaternion(Quaternion quaternion_1) {
      this.components = Arrays.copyOf(quaternion_1.components, 4);
   }

   public boolean equals(Object object_1) {
      if (this == object_1) {
         return true;
      } else if (object_1 != null && this.getClass() == object_1.getClass()) {
         Quaternion quaternion_1 = (Quaternion)object_1;
         return Arrays.equals(this.components, quaternion_1.components);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.components);
   }

   public String toString() {
      StringBuilder stringBuilder_1 = new StringBuilder();
      stringBuilder_1.append("Quaternion[").append(this.getW()).append(" + ");
      stringBuilder_1.append(this.getX()).append("i + ");
      stringBuilder_1.append(this.getY()).append("j + ");
      stringBuilder_1.append(this.getZ()).append("k]");
      return stringBuilder_1.toString();
   }

   public float getX() {
      return this.components[0];
   }

   public float getY() {
      return this.components[1];
   }

   public float getZ() {
      return this.components[2];
   }

   public float getW() {
      return this.components[3];
   }

   public void copyFrom(Quaternion quaternion_1) {
      float float_1 = this.getX();
      float float_2 = this.getY();
      float float_3 = this.getZ();
      float float_4 = this.getW();
      float float_5 = quaternion_1.getX();
      float float_6 = quaternion_1.getY();
      float float_7 = quaternion_1.getZ();
      float float_8 = quaternion_1.getW();
      this.components[0] = float_4 * float_5 + float_1 * float_8 + float_2 * float_7 - float_3 * float_6;
      this.components[1] = float_4 * float_6 - float_1 * float_7 + float_2 * float_8 + float_3 * float_5;
      this.components[2] = float_4 * float_7 + float_1 * float_6 - float_2 * float_5 + float_3 * float_8;
      this.components[3] = float_4 * float_8 - float_1 * float_5 - float_2 * float_6 - float_3 * float_7;
   }

   public void reverse() {
      this.components[0] = -this.components[0];
      this.components[1] = -this.components[1];
      this.components[2] = -this.components[2];
   }

   private static float cos(float float_1) {
      return (float)Math.cos((double)float_1);
   }

   private static float sin(float float_1) {
      return (float)Math.sin((double)float_1);
   }
}
