package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.nio.FloatBuffer;

@Environment(EnvType.CLIENT)
public class GuiLighting {
   private static final FloatBuffer buffer = GlAllocationUtils.allocateFloatBuffer(4);
   private static final Vector3f towardLight = createNormalVector(0.2F, 1.0F, -0.7F);
   private static final Vector3f oppositeLight = createNormalVector(-0.2F, 1.0F, 0.7F);

   private static Vector3f createNormalVector(float float_1, float float_2, float float_3) {
      Vector3f vector3f_1 = new Vector3f(float_1, float_2, float_3);
      vector3f_1.reciprocal();
      return vector3f_1;
   }

   public static void disable() {
      RenderSystem.disableLighting();
      RenderSystem.disableLight(0);
      RenderSystem.disableLight(1);
      RenderSystem.disableColorMaterial();
   }

   public static void enable() {
      RenderSystem.enableLighting();
      RenderSystem.enableLight(0);
      RenderSystem.enableLight(1);
      RenderSystem.enableColorMaterial();
      RenderSystem.colorMaterial(1032, 5634);
      RenderSystem.light(16384, 4611, singletonBuffer(towardLight.getX(), towardLight.getY(), towardLight.getZ(), 0.0F));
      float float_1 = 0.6F;
      RenderSystem.light(16384, 4609, singletonBuffer(0.6F, 0.6F, 0.6F, 1.0F));
      RenderSystem.light(16384, 4608, singletonBuffer(0.0F, 0.0F, 0.0F, 1.0F));
      RenderSystem.light(16384, 4610, singletonBuffer(0.0F, 0.0F, 0.0F, 1.0F));
      RenderSystem.light(16385, 4611, singletonBuffer(oppositeLight.getX(), oppositeLight.getY(), oppositeLight.getZ(), 0.0F));
      RenderSystem.light(16385, 4609, singletonBuffer(0.6F, 0.6F, 0.6F, 1.0F));
      RenderSystem.light(16385, 4608, singletonBuffer(0.0F, 0.0F, 0.0F, 1.0F));
      RenderSystem.light(16385, 4610, singletonBuffer(0.0F, 0.0F, 0.0F, 1.0F));
      RenderSystem.shadeModel(7424);
      float float_2 = 0.4F;
      RenderSystem.lightModel(2899, singletonBuffer(0.4F, 0.4F, 0.4F, 1.0F));
   }

   public static FloatBuffer singletonBuffer(float float_1, float float_2, float float_3, float float_4) {
      buffer.clear();
      buffer.put(float_1).put(float_2).put(float_3).put(float_4);
      buffer.flip();
      return buffer;
   }

   public static void enableForItems() {
      RenderSystem.pushMatrix();
      RenderSystem.rotatef(-30.0F, 0.0F, 1.0F, 0.0F);
      RenderSystem.rotatef(165.0F, 1.0F, 0.0F, 0.0F);
      enable();
      RenderSystem.popMatrix();
   }
}
