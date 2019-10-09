package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Arrays;

public final class Vector3f {
   private final float[] components;

   @Environment(EnvType.CLIENT)
   public Vector3f(Vector3f vector3f_1) {
      this.components = Arrays.copyOf(vector3f_1.components, 3);
   }

   public Vector3f() {
      this.components = new float[3];
   }

   @Environment(EnvType.CLIENT)
   public Vector3f(float float_1, float float_2, float float_3) {
      this.components = new float[]{float_1, float_2, float_3};
   }

   public Vector3f(Vec3d vec3d_1) {
      this.components = new float[]{(float)vec3d_1.x, (float)vec3d_1.y, (float)vec3d_1.z};
   }

   public boolean equals(Object object_1) {
      if (this == object_1) {
         return true;
      } else if (object_1 != null && this.getClass() == object_1.getClass()) {
         Vector3f vector3f_1 = (Vector3f)object_1;
         return Arrays.equals(this.components, vector3f_1.components);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.components);
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

   @Environment(EnvType.CLIENT)
   public void scale(float float_1) {
      for(int int_1 = 0; int_1 < 3; ++int_1) {
         float[] var10000 = this.components;
         var10000[int_1] *= float_1;
      }

   }

   @Environment(EnvType.CLIENT)
   private static float clampFloat(float float_1, float float_2, float float_3) {
      if (float_1 < float_2) {
         return float_2;
      } else {
         return float_1 > float_3 ? float_3 : float_1;
      }
   }

   @Environment(EnvType.CLIENT)
   public void clamp(float float_1, float float_2) {
      this.components[0] = clampFloat(this.components[0], float_1, float_2);
      this.components[1] = clampFloat(this.components[1], float_1, float_2);
      this.components[2] = clampFloat(this.components[2], float_1, float_2);
   }

   public void set(float float_1, float float_2, float float_3) {
      this.components[0] = float_1;
      this.components[1] = float_2;
      this.components[2] = float_3;
   }

   @Environment(EnvType.CLIENT)
   public void add(float float_1, float float_2, float float_3) {
      float[] var10000 = this.components;
      var10000[0] += float_1;
      var10000 = this.components;
      var10000[1] += float_2;
      var10000 = this.components;
      var10000[2] += float_3;
   }

   @Environment(EnvType.CLIENT)
   public void subtract(Vector3f vector3f_1) {
      for(int int_1 = 0; int_1 < 3; ++int_1) {
         float[] var10000 = this.components;
         var10000[int_1] -= vector3f_1.components[int_1];
      }

   }

   @Environment(EnvType.CLIENT)
   public float dot(Vector3f vector3f_1) {
      float float_1 = 0.0F;

      for(int int_1 = 0; int_1 < 3; ++int_1) {
         float_1 += this.components[int_1] * vector3f_1.components[int_1];
      }

      return float_1;
   }

   @Environment(EnvType.CLIENT)
   public void reciprocal() {
      float float_1 = 0.0F;

      int int_2;
      for(int_2 = 0; int_2 < 3; ++int_2) {
         float_1 += this.components[int_2] * this.components[int_2];
      }

      for(int_2 = 0; int_2 < 3; ++int_2) {
         float[] var10000 = this.components;
         var10000[int_2] /= float_1;
      }

   }

   @Environment(EnvType.CLIENT)
   public void cross(Vector3f vector3f_1) {
      float float_1 = this.components[0];
      float float_2 = this.components[1];
      float float_3 = this.components[2];
      float float_4 = vector3f_1.getX();
      float float_5 = vector3f_1.getY();
      float float_6 = vector3f_1.getZ();
      this.components[0] = float_2 * float_6 - float_3 * float_5;
      this.components[1] = float_3 * float_4 - float_1 * float_6;
      this.components[2] = float_1 * float_5 - float_2 * float_4;
   }

   public void method_19262(Quaternion quaternion_1) {
      Quaternion quaternion_2 = new Quaternion(quaternion_1);
      quaternion_2.copyFrom(new Quaternion(this.getX(), this.getY(), this.getZ(), 0.0F));
      Quaternion quaternion_3 = new Quaternion(quaternion_1);
      quaternion_3.reverse();
      quaternion_2.copyFrom(quaternion_3);
      this.set(quaternion_2.getX(), quaternion_2.getY(), quaternion_2.getZ());
   }
}
