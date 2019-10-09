package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Random;
import java.util.UUID;
import java.util.function.IntPredicate;

public class MathHelper {
   public static final float SQUARE_ROOT_OF_TWO = sqrt(2.0F);
   private static final float[] SINE_TABLE = (float[])SystemUtil.consume(new float[65536], (floats_1) -> {
      for(int int_1 = 0; int_1 < floats_1.length; ++int_1) {
         floats_1[int_1] = (float)Math.sin((double)int_1 * 3.141592653589793D * 2.0D / 65536.0D);
      }

   });
   private static final Random RANDOM = new Random();
   private static final int[] MULTIPLY_DE_BRUJIN_BIT_POSITION = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
   private static final double SMALLEST_FRACTION_FREE_DOUBLE = Double.longBitsToDouble(4805340802404319232L);
   private static final double[] ARCSINE_TABLE = new double[257];
   private static final double[] COSINE_TABLE = new double[257];

   public static float sin(float float_1) {
      return SINE_TABLE[(int)(float_1 * 10430.378F) & '\uffff'];
   }

   public static float cos(float float_1) {
      return SINE_TABLE[(int)(float_1 * 10430.378F + 16384.0F) & '\uffff'];
   }

   public static float sqrt(float float_1) {
      return (float)Math.sqrt((double)float_1);
   }

   public static float sqrt(double double_1) {
      return (float)Math.sqrt(double_1);
   }

   public static int floor(float float_1) {
      int int_1 = (int)float_1;
      return float_1 < (float)int_1 ? int_1 - 1 : int_1;
   }

   @Environment(EnvType.CLIENT)
   public static int fastFloor(double double_1) {
      return (int)(double_1 + 1024.0D) - 1024;
   }

   public static int floor(double double_1) {
      int int_1 = (int)double_1;
      return double_1 < (double)int_1 ? int_1 - 1 : int_1;
   }

   public static long lfloor(double double_1) {
      long long_1 = (long)double_1;
      return double_1 < (double)long_1 ? long_1 - 1L : long_1;
   }

   @Environment(EnvType.CLIENT)
   public static int absFloor(double double_1) {
      return (int)(double_1 >= 0.0D ? double_1 : -double_1 + 1.0D);
   }

   public static float abs(float float_1) {
      return Math.abs(float_1);
   }

   public static int abs(int int_1) {
      return Math.abs(int_1);
   }

   public static int ceil(float float_1) {
      int int_1 = (int)float_1;
      return float_1 > (float)int_1 ? int_1 + 1 : int_1;
   }

   public static int ceil(double double_1) {
      int int_1 = (int)double_1;
      return double_1 > (double)int_1 ? int_1 + 1 : int_1;
   }

   public static int clamp(int int_1, int int_2, int int_3) {
      if (int_1 < int_2) {
         return int_2;
      } else {
         return int_1 > int_3 ? int_3 : int_1;
      }
   }

   public static float clamp(float float_1, float float_2, float float_3) {
      if (float_1 < float_2) {
         return float_2;
      } else {
         return float_1 > float_3 ? float_3 : float_1;
      }
   }

   public static double clamp(double double_1, double double_2, double double_3) {
      if (double_1 < double_2) {
         return double_2;
      } else {
         return double_1 > double_3 ? double_3 : double_1;
      }
   }

   public static double clampedLerp(double double_1, double double_2, double double_3) {
      if (double_3 < 0.0D) {
         return double_1;
      } else {
         return double_3 > 1.0D ? double_2 : lerp(double_3, double_1, double_2);
      }
   }

   public static double absMax(double double_1, double double_2) {
      if (double_1 < 0.0D) {
         double_1 = -double_1;
      }

      if (double_2 < 0.0D) {
         double_2 = -double_2;
      }

      return double_1 > double_2 ? double_1 : double_2;
   }

   public static int floorDiv(int int_1, int int_2) {
      return Math.floorDiv(int_1, int_2);
   }

   public static int nextInt(Random random_1, int int_1, int int_2) {
      return int_1 >= int_2 ? int_1 : random_1.nextInt(int_2 - int_1 + 1) + int_1;
   }

   public static float nextFloat(Random random_1, float float_1, float float_2) {
      return float_1 >= float_2 ? float_1 : random_1.nextFloat() * (float_2 - float_1) + float_1;
   }

   public static double nextDouble(Random random_1, double double_1, double double_2) {
      return double_1 >= double_2 ? double_1 : random_1.nextDouble() * (double_2 - double_1) + double_1;
   }

   public static double average(long[] longs_1) {
      long long_1 = 0L;
      long[] var3 = longs_1;
      int var4 = longs_1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         long long_2 = var3[var5];
         long_1 += long_2;
      }

      return (double)long_1 / (double)longs_1.length;
   }

   @Environment(EnvType.CLIENT)
   public static boolean equalsApproximate(float float_1, float float_2) {
      return Math.abs(float_2 - float_1) < 1.0E-5F;
   }

   public static boolean method_20390(double double_1, double double_2) {
      return Math.abs(double_2 - double_1) < 9.999999747378752E-6D;
   }

   public static int floorMod(int int_1, int int_2) {
      return Math.floorMod(int_1, int_2);
   }

   @Environment(EnvType.CLIENT)
   public static float floorMod(float float_1, float float_2) {
      return (float_1 % float_2 + float_2) % float_2;
   }

   @Environment(EnvType.CLIENT)
   public static double floorMod(double double_1, double double_2) {
      return (double_1 % double_2 + double_2) % double_2;
   }

   @Environment(EnvType.CLIENT)
   public static int wrapDegrees(int int_1) {
      int int_2 = int_1 % 360;
      if (int_2 >= 180) {
         int_2 -= 360;
      }

      if (int_2 < -180) {
         int_2 += 360;
      }

      return int_2;
   }

   public static float wrapDegrees(float float_1) {
      float float_2 = float_1 % 360.0F;
      if (float_2 >= 180.0F) {
         float_2 -= 360.0F;
      }

      if (float_2 < -180.0F) {
         float_2 += 360.0F;
      }

      return float_2;
   }

   public static double wrapDegrees(double double_1) {
      double double_2 = double_1 % 360.0D;
      if (double_2 >= 180.0D) {
         double_2 -= 360.0D;
      }

      if (double_2 < -180.0D) {
         double_2 += 360.0D;
      }

      return double_2;
   }

   public static float subtractAngles(float float_1, float float_2) {
      return wrapDegrees(float_2 - float_1);
   }

   public static float angleBetween(float float_1, float float_2) {
      return abs(subtractAngles(float_1, float_2));
   }

   public static float method_20306(float float_1, float float_2, float float_3) {
      float float_4 = subtractAngles(float_1, float_2);
      float float_5 = clamp(float_4, -float_3, float_3);
      return float_2 - float_5;
   }

   public static float method_15348(float float_1, float float_2, float float_3) {
      float_3 = abs(float_3);
      return float_1 < float_2 ? clamp(float_1 + float_3, float_1, float_2) : clamp(float_1 - float_3, float_2, float_1);
   }

   public static float method_15388(float float_1, float float_2, float float_3) {
      float float_4 = subtractAngles(float_1, float_2);
      return method_15348(float_1, float_1 + float_4, float_3);
   }

   @Environment(EnvType.CLIENT)
   public static int parseInt(String string_1, int int_1) {
      return NumberUtils.toInt(string_1, int_1);
   }

   @Environment(EnvType.CLIENT)
   public static int parseInt(String string_1, int int_1, int int_2) {
      return Math.max(int_2, parseInt(string_1, int_1));
   }

   @Environment(EnvType.CLIENT)
   public static double parseDouble(String string_1, double double_1) {
      try {
         return Double.parseDouble(string_1);
      } catch (Throwable var4) {
         return double_1;
      }
   }

   @Environment(EnvType.CLIENT)
   public static double parseDouble(String string_1, double double_1, double double_2) {
      return Math.max(double_2, parseDouble(string_1, double_1));
   }

   public static int smallestEncompassingPowerOfTwo(int int_1) {
      int int_2 = int_1 - 1;
      int_2 |= int_2 >> 1;
      int_2 |= int_2 >> 2;
      int_2 |= int_2 >> 4;
      int_2 |= int_2 >> 8;
      int_2 |= int_2 >> 16;
      return int_2 + 1;
   }

   private static boolean isPowerOfTwo(int int_1) {
      return int_1 != 0 && (int_1 & int_1 - 1) == 0;
   }

   public static int log2DeBrujin(int int_1) {
      int_1 = isPowerOfTwo(int_1) ? int_1 : smallestEncompassingPowerOfTwo(int_1);
      return MULTIPLY_DE_BRUJIN_BIT_POSITION[(int)((long)int_1 * 125613361L >> 27) & 31];
   }

   public static int log2(int int_1) {
      return log2DeBrujin(int_1) - (isPowerOfTwo(int_1) ? 0 : 1);
   }

   public static int roundUp(int int_1, int int_2) {
      if (int_2 == 0) {
         return 0;
      } else if (int_1 == 0) {
         return int_2;
      } else {
         if (int_1 < 0) {
            int_2 *= -1;
         }

         int int_3 = int_1 % int_2;
         return int_3 == 0 ? int_1 : int_1 + int_2 - int_3;
      }
   }

   @Environment(EnvType.CLIENT)
   public static int packRgb(float float_1, float float_2, float float_3) {
      return packRgb(floor(float_1 * 255.0F), floor(float_2 * 255.0F), floor(float_3 * 255.0F));
   }

   @Environment(EnvType.CLIENT)
   public static int packRgb(int int_1, int int_2, int int_3) {
      int int_4 = (int_1 << 8) + int_2;
      int_4 = (int_4 << 8) + int_3;
      return int_4;
   }

   @Environment(EnvType.CLIENT)
   public static int multiplyColors(int int_1, int int_2) {
      int int_3 = (int_1 & 16711680) >> 16;
      int int_4 = (int_2 & 16711680) >> 16;
      int int_5 = (int_1 & '\uff00') >> 8;
      int int_6 = (int_2 & '\uff00') >> 8;
      int int_7 = (int_1 & 255) >> 0;
      int int_8 = (int_2 & 255) >> 0;
      int int_9 = (int)((float)int_3 * (float)int_4 / 255.0F);
      int int_10 = (int)((float)int_5 * (float)int_6 / 255.0F);
      int int_11 = (int)((float)int_7 * (float)int_8 / 255.0F);
      return int_1 & -16777216 | int_9 << 16 | int_10 << 8 | int_11;
   }

   public static double fractionalPart(double double_1) {
      return double_1 - (double)lfloor(double_1);
   }

   public static long hashCode(Vec3i vec3i_1) {
      return hashCode(vec3i_1.getX(), vec3i_1.getY(), vec3i_1.getZ());
   }

   public static long hashCode(int int_1, int int_2, int int_3) {
      long long_1 = (long)(int_1 * 3129871) ^ (long)int_3 * 116129781L ^ (long)int_2;
      long_1 = long_1 * long_1 * 42317861L + long_1 * 11L;
      return long_1 >> 16;
   }

   public static UUID randomUuid(Random random_1) {
      long long_1 = random_1.nextLong() & -61441L | 16384L;
      long long_2 = random_1.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(long_1, long_2);
   }

   public static UUID randomUUID() {
      return randomUuid(RANDOM);
   }

   public static double minusDiv(double double_1, double double_2, double double_3) {
      return (double_1 - double_2) / (double_3 - double_2);
   }

   public static double atan2(double double_1, double double_2) {
      double double_3 = double_2 * double_2 + double_1 * double_1;
      if (Double.isNaN(double_3)) {
         return Double.NaN;
      } else {
         boolean boolean_1 = double_1 < 0.0D;
         if (boolean_1) {
            double_1 = -double_1;
         }

         boolean boolean_2 = double_2 < 0.0D;
         if (boolean_2) {
            double_2 = -double_2;
         }

         boolean boolean_3 = double_1 > double_2;
         double double_5;
         if (boolean_3) {
            double_5 = double_2;
            double_2 = double_1;
            double_1 = double_5;
         }

         double_5 = fastInverseSqrt(double_3);
         double_2 *= double_5;
         double_1 *= double_5;
         double double_6 = SMALLEST_FRACTION_FREE_DOUBLE + double_1;
         int int_1 = (int)Double.doubleToRawLongBits(double_6);
         double double_7 = ARCSINE_TABLE[int_1];
         double double_8 = COSINE_TABLE[int_1];
         double double_9 = double_6 - SMALLEST_FRACTION_FREE_DOUBLE;
         double double_10 = double_1 * double_8 - double_2 * double_9;
         double double_11 = (6.0D + double_10 * double_10) * double_10 * 0.16666666666666666D;
         double double_12 = double_7 + double_11;
         if (boolean_3) {
            double_12 = 1.5707963267948966D - double_12;
         }

         if (boolean_2) {
            double_12 = 3.141592653589793D - double_12;
         }

         if (boolean_1) {
            double_12 = -double_12;
         }

         return double_12;
      }
   }

   public static double fastInverseSqrt(double double_1) {
      double double_2 = 0.5D * double_1;
      long long_1 = Double.doubleToRawLongBits(double_1);
      long_1 = 6910469410427058090L - (long_1 >> 1);
      double_1 = Double.longBitsToDouble(long_1);
      double_1 *= 1.5D - double_2 * double_1 * double_1;
      return double_1;
   }

   @Environment(EnvType.CLIENT)
   public static int hsvToRgb(float float_1, float float_2, float float_3) {
      int int_1 = (int)(float_1 * 6.0F) % 6;
      float float_4 = float_1 * 6.0F - (float)int_1;
      float float_5 = float_3 * (1.0F - float_2);
      float float_6 = float_3 * (1.0F - float_4 * float_2);
      float float_7 = float_3 * (1.0F - (1.0F - float_4) * float_2);
      float float_26;
      float float_27;
      float float_28;
      switch(int_1) {
      case 0:
         float_26 = float_3;
         float_27 = float_7;
         float_28 = float_5;
         break;
      case 1:
         float_26 = float_6;
         float_27 = float_3;
         float_28 = float_5;
         break;
      case 2:
         float_26 = float_5;
         float_27 = float_3;
         float_28 = float_7;
         break;
      case 3:
         float_26 = float_5;
         float_27 = float_6;
         float_28 = float_3;
         break;
      case 4:
         float_26 = float_7;
         float_27 = float_5;
         float_28 = float_3;
         break;
      case 5:
         float_26 = float_3;
         float_27 = float_5;
         float_28 = float_6;
         break;
      default:
         throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + float_1 + ", " + float_2 + ", " + float_3);
      }

      int int_2 = clamp((int)(float_26 * 255.0F), 0, 255);
      int int_3 = clamp((int)(float_27 * 255.0F), 0, 255);
      int int_4 = clamp((int)(float_28 * 255.0F), 0, 255);
      return int_2 << 16 | int_3 << 8 | int_4;
   }

   public static int method_15354(int int_1) {
      int_1 ^= int_1 >>> 16;
      int_1 *= -2048144789;
      int_1 ^= int_1 >>> 13;
      int_1 *= -1028477387;
      int_1 ^= int_1 >>> 16;
      return int_1;
   }

   public static int binarySearch(int int_1, int int_2, IntPredicate intPredicate_1) {
      int int_3 = int_2 - int_1;

      while(int_3 > 0) {
         int int_4 = int_3 / 2;
         int int_5 = int_1 + int_4;
         if (intPredicate_1.test(int_5)) {
            int_3 = int_4;
         } else {
            int_1 = int_5 + 1;
            int_3 -= int_4 + 1;
         }
      }

      return int_1;
   }

   public static float lerp(float float_1, float float_2, float float_3) {
      return float_2 + float_1 * (float_3 - float_2);
   }

   public static double lerp(double double_1, double double_2, double double_3) {
      return double_2 + double_1 * (double_3 - double_2);
   }

   public static double lerp2(double double_1, double double_2, double double_3, double double_4, double double_5, double double_6) {
      return lerp(double_2, lerp(double_1, double_3, double_4), lerp(double_1, double_5, double_6));
   }

   public static double lerp3(double double_1, double double_2, double double_3, double double_4, double double_5, double double_6, double double_7, double double_8, double double_9, double double_10, double double_11) {
      return lerp(double_3, lerp2(double_1, double_2, double_4, double_5, double_6, double_7), lerp2(double_1, double_2, double_8, double_9, double_10, double_11));
   }

   public static double perlinFade(double double_1) {
      return double_1 * double_1 * double_1 * (double_1 * (double_1 * 6.0D - 15.0D) + 10.0D);
   }

   public static int sign(double double_1) {
      if (double_1 == 0.0D) {
         return 0;
      } else {
         return double_1 > 0.0D ? 1 : -1;
      }
   }

   @Environment(EnvType.CLIENT)
   public static float lerpAngleDegrees(float float_1, float float_2, float float_3) {
      return float_2 + float_1 * wrapDegrees(float_3 - float_2);
   }

   static {
      for(int int_1 = 0; int_1 < 257; ++int_1) {
         double double_1 = (double)int_1 / 256.0D;
         double double_2 = Math.asin(double_1);
         COSINE_TABLE[int_1] = Math.cos(double_2);
         ARCSINE_TABLE[int_1] = double_2;
      }

   }
}
