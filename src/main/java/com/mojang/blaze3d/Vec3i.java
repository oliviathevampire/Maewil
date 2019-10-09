package com.mojang.blaze3d;

import com.google.common.base.MoreObjects;
import javax.annotation.concurrent.Immutable;

@Immutable
public class Vec3i implements Comparable<Vec3i> {
   public static final Vec3i ZERO = new Vec3i(0, 0, 0);
   private final int x;
   private final int y;
   private final int z;

   public Vec3i(int int_1, int int_2, int int_3) {
      this.x = int_1;
      this.y = int_2;
      this.z = int_3;
   }

   public Vec3i(double double_1, double double_2, double double_3) {
      this(MathHelper.floor(double_1), MathHelper.floor(double_2), MathHelper.floor(double_3));
   }

   public boolean equals(Object object_1) {
      if (this == object_1) {
         return true;
      } else if (!(object_1 instanceof Vec3i)) {
         return false;
      } else {
         Vec3i vec3i_1 = (Vec3i)object_1;
         if (this.getX() != vec3i_1.getX()) {
            return false;
         } else if (this.getY() != vec3i_1.getY()) {
            return false;
         } else {
            return this.getZ() == vec3i_1.getZ();
         }
      }
   }

   public int hashCode() {
      return (this.getY() + this.getZ() * 31) * 31 + this.getX();
   }

   public int compareTo(Vec3i vec3i_1) {
      if (this.getY() == vec3i_1.getY()) {
         return this.getZ() == vec3i_1.getZ() ? this.getX() - vec3i_1.getX() : this.getZ() - vec3i_1.getZ();
      } else {
         return this.getY() - vec3i_1.getY();
      }
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   public Vec3i crossProduct(Vec3i vec3i_1) {
      return new Vec3i(this.getY() * vec3i_1.getZ() - this.getZ() * vec3i_1.getY(), this.getZ() * vec3i_1.getX() - this.getX() * vec3i_1.getZ(), this.getX() * vec3i_1.getY() - this.getY() * vec3i_1.getX());
   }

   public boolean isWithinDistance(Vec3i vec3i_1, double double_1) {
      return this.getSquaredDistance((double)vec3i_1.x, (double)vec3i_1.y, (double)vec3i_1.z, false) < double_1 * double_1;
   }

   public boolean isWithinDistance(Position position_1, double double_1) {
      return this.getSquaredDistance(position_1.getX(), position_1.getY(), position_1.getZ(), true) < double_1 * double_1;
   }

   public double getSquaredDistance(Vec3i vec3i_1) {
      return this.getSquaredDistance((double)vec3i_1.getX(), (double)vec3i_1.getY(), (double)vec3i_1.getZ(), true);
   }

   public double getSquaredDistance(Position position_1, boolean boolean_1) {
      return this.getSquaredDistance(position_1.getX(), position_1.getY(), position_1.getZ(), boolean_1);
   }

   public double getSquaredDistance(double double_1, double double_2, double double_3, boolean boolean_1) {
      double double_4 = boolean_1 ? 0.5D : 0.0D;
      double double_5 = (double)this.getX() + double_4 - double_1;
      double double_6 = (double)this.getY() + double_4 - double_2;
      double double_7 = (double)this.getZ() + double_4 - double_3;
      return double_5 * double_5 + double_6 * double_6 + double_7 * double_7;
   }

   public int getManhattanDistance(Vec3i vec3i_1) {
      float float_1 = (float)Math.abs(vec3i_1.getX() - this.x);
      float float_2 = (float)Math.abs(vec3i_1.getY() - this.y);
      float float_3 = (float)Math.abs(vec3i_1.getZ() - this.z);
      return (int)(float_1 + float_2 + float_3);
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
   }

}
