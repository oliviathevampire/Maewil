package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;

@Environment(EnvType.CLIENT)
public class class_4493 {
   private static final FloatBuffer field_20466 = (FloatBuffer)GLX.make(MemoryUtil.memAllocFloat(16), (floatBuffer_1) -> {
      Untracker.untrack(MemoryUtil.memAddress(floatBuffer_1));
   });
   private static final FloatBuffer field_20467 = (FloatBuffer)GLX.make(MemoryUtil.memAllocFloat(4), (floatBuffer_1) -> {
      Untracker.untrack(MemoryUtil.memAddress(floatBuffer_1));
   });
   private static final class_4493.AlphaTestState field_20468 = new class_4493.AlphaTestState();
   private static final class_4493.CapabilityTracker field_20469 = new class_4493.CapabilityTracker(2896);
   private static final class_4493.CapabilityTracker[] field_20470 = (class_4493.CapabilityTracker[])IntStream.range(0, 8).mapToObj((int_1) -> {
      return new class_4493.CapabilityTracker(16384 + int_1);
   }).toArray((int_1) -> {
      return new class_4493.CapabilityTracker[int_1];
   });
   private static final class_4493.ColorMaterialState field_20471 = new class_4493.ColorMaterialState();
   private static final class_4493.BlendFuncState field_20472 = new class_4493.BlendFuncState();
   private static final class_4493.DepthTestState field_20473 = new class_4493.DepthTestState();
   private static final class_4493.FogState field_20474 = new class_4493.FogState();
   private static final class_4493.CullFaceState field_20475 = new class_4493.CullFaceState();
   private static final class_4493.PolygonOffsetState field_20476 = new class_4493.PolygonOffsetState();
   private static final class_4493.LogicOpState field_20477 = new class_4493.LogicOpState();
   private static final class_4493.TexGenState field_20478 = new class_4493.TexGenState();
   private static final class_4493.ClearState field_20479 = new class_4493.ClearState();
   private static final class_4493.StencilState field_20480 = new class_4493.StencilState();
   private static final class_4493.CapabilityTracker field_20481 = new class_4493.CapabilityTracker(2977);
   private static int field_20482;
   private static final class_4493.Texture2DState[] field_20483 = (class_4493.Texture2DState[])IntStream.range(0, 8).mapToObj((int_1) -> {
      return new class_4493.Texture2DState();
   }).toArray((int_1) -> {
      return new class_4493.Texture2DState[int_1];
   });
   private static int field_20484 = 7425;
   private static final class_4493.CapabilityTracker field_20485 = new class_4493.CapabilityTracker(32826);
   private static final class_4493.ColorMask field_20486 = new class_4493.ColorMask();
   private static final class_4493.Color4 field_20487 = new class_4493.Color4();
   private static class_4493.FBOMode field_20488;

   public static void method_21935() {
      GL11.glPushAttrib(8256);
   }

   public static void method_21976() {
      GL11.glPushAttrib(270336);
   }

   public static void method_21997() {
      GL11.glPopAttrib();
   }

   public static void method_22012() {
      field_20468.capState.disable();
   }

   public static void method_22021() {
      field_20468.capState.enable();
   }

   public static void method_21945(int int_1, float float_1) {
      if (int_1 != field_20468.func || float_1 != field_20468.ref) {
         field_20468.func = int_1;
         field_20468.ref = float_1;
         GL11.glAlphaFunc(int_1, float_1);
      }

   }

   public static void method_22028() {
      field_20469.enable();
   }

   public static void method_22034() {
      field_20469.disable();
   }

   public static void method_21944(int int_1) {
      field_20470[int_1].enable();
   }

   public static void method_21982(int int_1) {
      field_20470[int_1].disable();
   }

   public static void method_22040() {
      field_20471.capState.enable();
   }

   public static void method_22044() {
      field_20471.capState.disable();
   }

   public static void method_21947(int int_1, int int_2) {
      if (int_1 != field_20471.face || int_2 != field_20471.mode) {
         field_20471.face = int_1;
         field_20471.mode = int_2;
         GL11.glColorMaterial(int_1, int_2);
      }

   }

   public static void method_21960(int int_1, int int_2, FloatBuffer floatBuffer_1) {
      GL11.glLightfv(int_1, int_2, floatBuffer_1);
   }

   public static void method_21963(int int_1, FloatBuffer floatBuffer_1) {
      GL11.glLightModelfv(int_1, floatBuffer_1);
   }

   public static void method_21942(float float_1, float float_2, float float_3) {
      GL11.glNormal3f(float_1, float_2, float_3);
   }

   public static void method_22047() {
      field_20473.capState.disable();
   }

   public static void method_22050() {
      field_20473.capState.enable();
   }

   public static void method_22001(int int_1) {
      if (int_1 != field_20473.func) {
         field_20473.func = int_1;
         GL11.glDepthFunc(int_1);
      }

   }

   public static void method_21974(boolean boolean_1) {
      if (boolean_1 != field_20473.mask) {
         field_20473.mask = boolean_1;
         GL11.glDepthMask(boolean_1);
      }

   }

   public static void method_22053() {
      field_20472.capState.disable();
   }

   public static void method_22056() {
      field_20472.capState.enable();
   }

   public static void method_21984(int int_1, int int_2) {
      if (int_1 != field_20472.sfactor || int_2 != field_20472.dfactor) {
         field_20472.sfactor = int_1;
         field_20472.dfactor = int_2;
         GL11.glBlendFunc(int_1, int_2);
      }

   }

   public static void method_21950(int int_1, int int_2, int int_3, int int_4) {
      if (int_1 != field_20472.sfactor || int_2 != field_20472.dfactor || int_3 != field_20472.srcAlpha || int_4 != field_20472.dstAlpha) {
         field_20472.sfactor = int_1;
         field_20472.dfactor = int_2;
         field_20472.srcAlpha = int_3;
         field_20472.dstAlpha = int_4;
         method_22018(int_1, int_2, int_3, int_4);
      }

   }

   public static void method_22015(int int_1) {
      GL14.glBlendEquation(int_1);
   }

   public static void method_22022(int int_1) {
      field_20467.put(0, (float)(int_1 >> 16 & 255) / 255.0F);
      field_20467.put(1, (float)(int_1 >> 8 & 255) / 255.0F);
      field_20467.put(2, (float)(int_1 >> 0 & 255) / 255.0F);
      field_20467.put(3, (float)(int_1 >> 24 & 255) / 255.0F);
      RenderSystem.texEnv(8960, 8705, field_20467);
      RenderSystem.texEnv(8960, 8704, 34160);
      RenderSystem.texEnv(8960, 34161, 7681);
      RenderSystem.texEnv(8960, 34176, 34166);
      RenderSystem.texEnv(8960, 34192, 768);
      RenderSystem.texEnv(8960, 34162, 7681);
      RenderSystem.texEnv(8960, 34184, 5890);
      RenderSystem.texEnv(8960, 34200, 770);
   }

   public static void method_22059() {
      RenderSystem.texEnv(8960, 8704, 8448);
      RenderSystem.texEnv(8960, 34161, 8448);
      RenderSystem.texEnv(8960, 34162, 8448);
      RenderSystem.texEnv(8960, 34176, 5890);
      RenderSystem.texEnv(8960, 34184, 5890);
      RenderSystem.texEnv(8960, 34192, 768);
      RenderSystem.texEnv(8960, 34200, 770);
   }

   public static String method_21973(GLCapabilities gLCapabilities_1) {
      if (gLCapabilities_1.OpenGL30) {
         field_20488 = class_4493.FBOMode.BASE;
         class_4492.field_20457 = 36160;
         class_4492.field_20458 = 36161;
         class_4492.field_20459 = 36064;
         class_4492.field_20460 = 36096;
         class_4492.field_20461 = 36053;
         class_4492.field_20462 = 36054;
         class_4492.field_20463 = 36055;
         class_4492.field_20464 = 36059;
         class_4492.field_20465 = 36060;
         return "OpenGL 3.0";
      } else if (gLCapabilities_1.GL_ARB_framebuffer_object) {
         field_20488 = class_4493.FBOMode.ARB;
         class_4492.field_20457 = 36160;
         class_4492.field_20458 = 36161;
         class_4492.field_20459 = 36064;
         class_4492.field_20460 = 36096;
         class_4492.field_20461 = 36053;
         class_4492.field_20463 = 36055;
         class_4492.field_20462 = 36054;
         class_4492.field_20464 = 36059;
         class_4492.field_20465 = 36060;
         return "ARB_framebuffer_object extension";
      } else if (gLCapabilities_1.GL_EXT_framebuffer_object) {
         field_20488 = class_4493.FBOMode.EXT;
         class_4492.field_20457 = 36160;
         class_4492.field_20458 = 36161;
         class_4492.field_20459 = 36064;
         class_4492.field_20460 = 36096;
         class_4492.field_20461 = 36053;
         class_4492.field_20463 = 36055;
         class_4492.field_20462 = 36054;
         class_4492.field_20464 = 36059;
         class_4492.field_20465 = 36060;
         return "EXT_framebuffer_object extension";
      } else {
         throw new IllegalStateException("Could not initialize framebuffer support.");
      }
   }

   public static int method_22002(int int_1, int int_2) {
      return GL20.glGetProgrami(int_1, int_2);
   }

   public static void method_22016(int int_1, int int_2) {
      GL20.glAttachShader(int_1, int_2);
   }

   public static void method_22029(int int_1) {
      GL20.glDeleteShader(int_1);
   }

   public static int method_22035(int int_1) {
      return GL20.glCreateShader(int_1);
   }

   public static void method_21961(int int_1, CharSequence charSequence_1) {
      GL20.glShaderSource(int_1, charSequence_1);
   }

   public static void method_22041(int int_1) {
      GL20.glCompileShader(int_1);
   }

   public static int method_22023(int int_1, int int_2) {
      return GL20.glGetShaderi(int_1, int_2);
   }

   public static void method_22045(int int_1) {
      GL20.glUseProgram(int_1);
   }

   public static int method_22062() {
      return GL20.glCreateProgram();
   }

   public static void method_22048(int int_1) {
      GL20.glDeleteProgram(int_1);
   }

   public static void method_22051(int int_1) {
      GL20.glLinkProgram(int_1);
   }

   public static int method_21990(int int_1, CharSequence charSequence_1) {
      return GL20.glGetUniformLocation(int_1, charSequence_1);
   }

   public static void method_21964(int int_1, IntBuffer intBuffer_1) {
      GL20.glUniform1iv(int_1, intBuffer_1);
   }

   public static void method_22030(int int_1, int int_2) {
      GL20.glUniform1i(int_1, int_2);
   }

   public static void method_21991(int int_1, FloatBuffer floatBuffer_1) {
      GL20.glUniform1fv(int_1, floatBuffer_1);
   }

   public static void method_21992(int int_1, IntBuffer intBuffer_1) {
      GL20.glUniform2iv(int_1, intBuffer_1);
   }

   public static void method_22007(int int_1, FloatBuffer floatBuffer_1) {
      GL20.glUniform2fv(int_1, floatBuffer_1);
   }

   public static void method_22008(int int_1, IntBuffer intBuffer_1) {
      GL20.glUniform3iv(int_1, intBuffer_1);
   }

   public static void method_22019(int int_1, FloatBuffer floatBuffer_1) {
      GL20.glUniform3fv(int_1, floatBuffer_1);
   }

   public static void method_22020(int int_1, IntBuffer intBuffer_1) {
      GL20.glUniform4iv(int_1, intBuffer_1);
   }

   public static void method_22026(int int_1, FloatBuffer floatBuffer_1) {
      GL20.glUniform4fv(int_1, floatBuffer_1);
   }

   public static void method_21966(int int_1, boolean boolean_1, FloatBuffer floatBuffer_1) {
      GL20.glUniformMatrix2fv(int_1, boolean_1, floatBuffer_1);
   }

   public static void method_21993(int int_1, boolean boolean_1, FloatBuffer floatBuffer_1) {
      GL20.glUniformMatrix3fv(int_1, boolean_1, floatBuffer_1);
   }

   public static void method_22009(int int_1, boolean boolean_1, FloatBuffer floatBuffer_1) {
      GL20.glUniformMatrix4fv(int_1, boolean_1, floatBuffer_1);
   }

   public static int method_22006(int int_1, CharSequence charSequence_1) {
      return GL20.glGetAttribLocation(int_1, charSequence_1);
   }

   public static int method_22065() {
      return GL15.glGenBuffers();
   }

   public static void method_22036(int int_1, int int_2) {
      GL15.glBindBuffer(int_1, int_2);
   }

   public static void method_21962(int int_1, ByteBuffer byteBuffer_1, int int_2) {
      GL15.glBufferData(int_1, byteBuffer_1, int_2);
   }

   public static void method_22054(int int_1) {
      GL15.glDeleteBuffers(int_1);
   }

   public static void method_22042(int int_1, int int_2) {
      switch(field_20488) {
      case BASE:
         GL30.glBindFramebuffer(int_1, int_2);
         break;
      case ARB:
         ARBFramebufferObject.glBindFramebuffer(int_1, int_2);
         break;
      case EXT:
         EXTFramebufferObject.glBindFramebufferEXT(int_1, int_2);
      }

   }

   public static void method_22046(int int_1, int int_2) {
      switch(field_20488) {
      case BASE:
         GL30.glBindRenderbuffer(int_1, int_2);
         break;
      case ARB:
         ARBFramebufferObject.glBindRenderbuffer(int_1, int_2);
         break;
      case EXT:
         EXTFramebufferObject.glBindRenderbufferEXT(int_1, int_2);
      }

   }

   public static void method_22057(int int_1) {
      switch(field_20488) {
      case BASE:
         GL30.glDeleteRenderbuffers(int_1);
         break;
      case ARB:
         ARBFramebufferObject.glDeleteRenderbuffers(int_1);
         break;
      case EXT:
         EXTFramebufferObject.glDeleteRenderbuffersEXT(int_1);
      }

   }

   public static void method_22060(int int_1) {
      switch(field_20488) {
      case BASE:
         GL30.glDeleteFramebuffers(int_1);
         break;
      case ARB:
         ARBFramebufferObject.glDeleteFramebuffers(int_1);
         break;
      case EXT:
         EXTFramebufferObject.glDeleteFramebuffersEXT(int_1);
      }

   }

   public static int method_22068() {
      switch(field_20488) {
      case BASE:
         return GL30.glGenFramebuffers();
      case ARB:
         return ARBFramebufferObject.glGenFramebuffers();
      case EXT:
         return EXTFramebufferObject.glGenFramebuffersEXT();
      default:
         return -1;
      }
   }

   public static int method_22070() {
      switch(field_20488) {
      case BASE:
         return GL30.glGenRenderbuffers();
      case ARB:
         return ARBFramebufferObject.glGenRenderbuffers();
      case EXT:
         return EXTFramebufferObject.glGenRenderbuffersEXT();
      default:
         return -1;
      }
   }

   public static void method_21987(int int_1, int int_2, int int_3, int int_4) {
      switch(field_20488) {
      case BASE:
         GL30.glRenderbufferStorage(int_1, int_2, int_3, int_4);
         break;
      case ARB:
         ARBFramebufferObject.glRenderbufferStorage(int_1, int_2, int_3, int_4);
         break;
      case EXT:
         EXTFramebufferObject.glRenderbufferStorageEXT(int_1, int_2, int_3, int_4);
      }

   }

   public static void method_22004(int int_1, int int_2, int int_3, int int_4) {
      switch(field_20488) {
      case BASE:
         GL30.glFramebufferRenderbuffer(int_1, int_2, int_3, int_4);
         break;
      case ARB:
         ARBFramebufferObject.glFramebufferRenderbuffer(int_1, int_2, int_3, int_4);
         break;
      case EXT:
         EXTFramebufferObject.glFramebufferRenderbufferEXT(int_1, int_2, int_3, int_4);
      }

   }

   public static int method_22063(int int_1) {
      switch(field_20488) {
      case BASE:
         return GL30.glCheckFramebufferStatus(int_1);
      case ARB:
         return ARBFramebufferObject.glCheckFramebufferStatus(int_1);
      case EXT:
         return EXTFramebufferObject.glCheckFramebufferStatusEXT(int_1);
      default:
         return -1;
      }
   }

   public static void method_21951(int int_1, int int_2, int int_3, int int_4, int int_5) {
      switch(field_20488) {
      case BASE:
         GL30.glFramebufferTexture2D(int_1, int_2, int_3, int_4, int_5);
         break;
      case ARB:
         ARBFramebufferObject.glFramebufferTexture2D(int_1, int_2, int_3, int_4, int_5);
         break;
      case EXT:
         EXTFramebufferObject.glFramebufferTexture2DEXT(int_1, int_2, int_3, int_4, int_5);
      }

   }

   public static void method_22066(int int_1) {
      GL13.glActiveTexture(int_1);
   }

   public static void method_22069(int int_1) {
      GL13.glClientActiveTexture(int_1);
   }

   public static void method_21946(int int_1, float float_1, float float_2) {
      GL13.glMultiTexCoord2f(int_1, float_1, float_2);
   }

   public static void method_22018(int int_1, int int_2, int int_3, int int_4) {
      GL14.glBlendFuncSeparate(int_1, int_2, int_3, int_4);
   }

   public static String method_22049(int int_1, int int_2) {
      return GL20.glGetShaderInfoLog(int_1, int_2);
   }

   public static String method_22052(int int_1, int int_2) {
      return GL20.glGetProgramInfoLog(int_1, int_2);
   }

   public static void method_22072() {
      field_20474.capState.enable();
   }

   public static void method_22074() {
      field_20474.capState.disable();
   }

   public static void method_22071(int int_1) {
      if (int_1 != field_20474.mode) {
         field_20474.mode = int_1;
         method_22055(2917, int_1);
      }

   }

   public static void method_21940(float float_1) {
      if (float_1 != field_20474.density) {
         field_20474.density = float_1;
         GL11.glFogf(2914, float_1);
      }

   }

   public static void method_21978(float float_1) {
      if (float_1 != field_20474.start) {
         field_20474.start = float_1;
         GL11.glFogf(2915, float_1);
      }

   }

   public static void method_21998(float float_1) {
      if (float_1 != field_20474.end) {
         field_20474.end = float_1;
         GL11.glFogf(2916, float_1);
      }

   }

   public static void method_22033(int int_1, FloatBuffer floatBuffer_1) {
      GL11.glFogfv(int_1, floatBuffer_1);
   }

   public static void method_22055(int int_1, int int_2) {
      GL11.glFogi(int_1, int_2);
   }

   public static void method_22076() {
      field_20475.capState.enable();
   }

   public static void method_22078() {
      field_20475.capState.disable();
   }

   public static void method_22073(int int_1) {
      if (int_1 != field_20475.mode) {
         field_20475.mode = int_1;
         GL11.glCullFace(int_1);
      }

   }

   public static void method_22058(int int_1, int int_2) {
      GL11.glPolygonMode(int_1, int_2);
   }

   public static void method_22080() {
      field_20476.capFill.enable();
   }

   public static void method_22082() {
      field_20476.capFill.disable();
   }

   public static void method_22084() {
      field_20476.capLine.enable();
   }

   public static void method_22086() {
      field_20476.capLine.disable();
   }

   public static void method_21941(float float_1, float float_2) {
      if (float_1 != field_20476.factor || float_2 != field_20476.units) {
         field_20476.factor = float_1;
         field_20476.units = float_2;
         GL11.glPolygonOffset(float_1, float_2);
      }

   }

   public static void method_21906() {
      field_20477.capState.enable();
   }

   public static void method_21908() {
      field_20477.capState.disable();
   }

   public static void method_22075(int int_1) {
      if (int_1 != field_20477.opcode) {
         field_20477.opcode = int_1;
         GL11.glLogicOp(int_1);
      }

   }

   public static void method_21968(class_4493.TexCoord class_4493$TexCoord_1) {
      method_22010(class_4493$TexCoord_1).capState.enable();
   }

   public static void method_21995(class_4493.TexCoord class_4493$TexCoord_1) {
      method_22010(class_4493$TexCoord_1).capState.disable();
   }

   public static void method_21969(class_4493.TexCoord class_4493$TexCoord_1, int int_1) {
      class_4493.TexGenCoordState class_4493$TexGenCoordState_1 = method_22010(class_4493$TexCoord_1);
      if (int_1 != class_4493$TexGenCoordState_1.mode) {
         class_4493$TexGenCoordState_1.mode = int_1;
         GL11.glTexGeni(class_4493$TexGenCoordState_1.coord, 9472, int_1);
      }

   }

   public static void method_21970(class_4493.TexCoord class_4493$TexCoord_1, int int_1, FloatBuffer floatBuffer_1) {
      GL11.glTexGenfv(method_22010(class_4493$TexCoord_1).coord, int_1, floatBuffer_1);
   }

   private static class_4493.TexGenCoordState method_22010(class_4493.TexCoord class_4493$TexCoord_1) {
      switch(class_4493$TexCoord_1) {
      case S:
         return field_20478.s;
      case T:
         return field_20478.t;
      case R:
         return field_20478.r;
      case Q:
         return field_20478.q;
      default:
         return field_20478.s;
      }
   }

   public static void method_22077(int int_1) {
      if (field_20482 != int_1 - '蓀') {
         field_20482 = int_1 - '蓀';
         method_22066(int_1);
      }

   }

   public static void method_21910() {
      field_20483[field_20482].capState.enable();
   }

   public static void method_21912() {
      field_20483[field_20482].capState.disable();
   }

   public static void method_21989(int int_1, int int_2, FloatBuffer floatBuffer_1) {
      GL11.glTexEnvfv(int_1, int_2, floatBuffer_1);
   }

   public static void method_21949(int int_1, int int_2, int int_3) {
      GL11.glTexEnvi(int_1, int_2, int_3);
   }

   public static void method_21948(int int_1, int int_2, float float_1) {
      GL11.glTexEnvf(int_1, int_2, float_1);
   }

   public static void method_21985(int int_1, int int_2, float float_1) {
      GL11.glTexParameterf(int_1, int_2, float_1);
   }

   public static void method_21986(int int_1, int int_2, int int_3) {
      GL11.glTexParameteri(int_1, int_2, int_3);
   }

   public static int method_22003(int int_1, int int_2, int int_3) {
      return GL11.glGetTexLevelParameteri(int_1, int_2, int_3);
   }

   public static int method_21914() {
      return GL11.glGenTextures();
   }

   public static void method_22079(int int_1) {
      GL11.glDeleteTextures(int_1);
      class_4493.Texture2DState[] var1 = field_20483;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         class_4493.Texture2DState class_4493$Texture2DState_1 = var1[var3];
         if (class_4493$Texture2DState_1.boundTexture == int_1) {
            class_4493$Texture2DState_1.boundTexture = -1;
         }
      }

   }

   public static void method_22081(int int_1) {
      if (int_1 != field_20483[field_20482].boundTexture) {
         field_20483[field_20482].boundTexture = int_1;
         GL11.glBindTexture(3553, int_1);
      }

   }

   public static void method_21954(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, int int_8, @Nullable IntBuffer intBuffer_1) {
      GL11.glTexImage2D(int_1, int_2, int_3, int_4, int_5, int_6, int_7, int_8, intBuffer_1);
   }

   public static void method_21953(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, int int_8, long long_1) {
      GL11.glTexSubImage2D(int_1, int_2, int_3, int_4, int_5, int_6, int_7, int_8, long_1);
   }

   public static void method_21952(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, int int_8) {
      GL11.glCopyTexSubImage2D(int_1, int_2, int_3, int_4, int_5, int_6, int_7, int_8);
   }

   public static void method_21957(int int_1, int int_2, int int_3, int int_4, long long_1) {
      GL11.glGetTexImage(int_1, int_2, int_3, int_4, long_1);
   }

   public static void method_21916() {
      field_20481.enable();
   }

   public static void method_21918() {
      field_20481.disable();
   }

   public static void method_22083(int int_1) {
      if (int_1 != field_20484) {
         field_20484 = int_1;
         GL11.glShadeModel(int_1);
      }

   }

   public static void method_21920() {
      field_20485.enable();
   }

   public static void method_21922() {
      field_20485.disable();
   }

   public static void method_22025(int int_1, int int_2, int int_3, int int_4) {
      class_4493.Viewport.INSTANCE.x = int_1;
      class_4493.Viewport.INSTANCE.y = int_2;
      class_4493.Viewport.INSTANCE.width = int_3;
      class_4493.Viewport.INSTANCE.height = int_4;
      GL11.glViewport(int_1, int_2, int_3, int_4);
   }

   public static void method_21975(boolean boolean_1, boolean boolean_2, boolean boolean_3, boolean boolean_4) {
      if (boolean_1 != field_20486.red || boolean_2 != field_20486.green || boolean_3 != field_20486.blue || boolean_4 != field_20486.alpha) {
         field_20486.red = boolean_1;
         field_20486.green = boolean_2;
         field_20486.blue = boolean_3;
         field_20486.alpha = boolean_4;
         GL11.glColorMask(boolean_1, boolean_2, boolean_3, boolean_4);
      }

   }

   public static void method_22017(int int_1, int int_2, int int_3) {
      if (int_1 != field_20480.subState.func || int_1 != field_20480.subState.field_16203 || int_1 != field_20480.subState.field_5147) {
         field_20480.subState.func = int_1;
         field_20480.subState.field_16203 = int_2;
         field_20480.subState.field_5147 = int_3;
         GL11.glStencilFunc(int_1, int_2, int_3);
      }

   }

   public static void method_22085(int int_1) {
      if (int_1 != field_20480.field_5153) {
         field_20480.field_5153 = int_1;
         GL11.glStencilMask(int_1);
      }

   }

   public static void method_22024(int int_1, int int_2, int int_3) {
      if (int_1 != field_20480.field_5152 || int_2 != field_20480.field_5151 || int_3 != field_20480.field_5150) {
         field_20480.field_5152 = int_1;
         field_20480.field_5151 = int_2;
         field_20480.field_5150 = int_3;
         GL11.glStencilOp(int_1, int_2, int_3);
      }

   }

   public static void method_21936(double double_1) {
      if (double_1 != field_20479.clearDepth) {
         field_20479.clearDepth = double_1;
         GL11.glClearDepth(double_1);
      }

   }

   public static void method_21943(float float_1, float float_2, float float_3, float float_4) {
      if (float_1 != field_20479.clearColor.red || float_2 != field_20479.clearColor.green || float_3 != field_20479.clearColor.blue || float_4 != field_20479.clearColor.alpha) {
         field_20479.clearColor.red = float_1;
         field_20479.clearColor.green = float_2;
         field_20479.clearColor.blue = float_3;
         field_20479.clearColor.alpha = float_4;
         GL11.glClearColor(float_1, float_2, float_3, float_4);
      }

   }

   public static void method_22087(int int_1) {
      if (int_1 != field_20479.field_16202) {
         field_20479.field_16202 = int_1;
         GL11.glClearStencil(int_1);
      }

   }

   public static void method_21965(int int_1, boolean boolean_1) {
      GL11.glClear(int_1);
      if (boolean_1) {
         RenderSystem.getError();
      }

   }

   public static void method_21907(int int_1) {
      GL11.glMatrixMode(int_1);
   }

   public static void method_21924() {
      GL11.glLoadIdentity();
   }

   public static void method_21926() {
      GL11.glPushMatrix();
   }

   public static void method_21928() {
      GL11.glPopMatrix();
   }

   public static void method_22039(int int_1, FloatBuffer floatBuffer_1) {
      GL11.glGetFloatv(int_1, floatBuffer_1);
   }

   public static Matrix4f method_21909(int int_1) {
      method_22039(int_1, field_20466);
      field_20466.rewind();
      Matrix4f matrix4f_1 = new Matrix4f();
      matrix4f_1.setFromBuffer(field_20466);
      field_20466.rewind();
      return matrix4f_1;
   }

   public static void method_21939(double double_1, double double_2, double double_3, double double_4, double double_5, double double_6) {
      GL11.glOrtho(double_1, double_2, double_3, double_4, double_5, double_6);
   }

   public static void method_21981(float float_1, float float_2, float float_3, float float_4) {
      GL11.glRotatef(float_1, float_2, float_3, float_4);
   }

   public static void method_21938(double double_1, double double_2, double double_3, double double_4) {
      GL11.glRotated(double_1, double_2, double_3, double_4);
   }

   public static void method_21980(float float_1, float float_2, float float_3) {
      GL11.glScalef(float_1, float_2, float_3);
   }

   public static void method_21937(double double_1, double double_2, double double_3) {
      GL11.glScaled(double_1, double_2, double_3);
   }

   public static void method_21999(float float_1, float float_2, float float_3) {
      GL11.glTranslatef(float_1, float_2, float_3);
   }

   public static void method_21977(double double_1, double double_2, double double_3) {
      GL11.glTranslated(double_1, double_2, double_3);
   }

   public static void method_21972(FloatBuffer floatBuffer_1) {
      GL11.glMultMatrixf(floatBuffer_1);
   }

   public static void method_21971(Matrix4f matrix4f_1) {
      matrix4f_1.putIntoBuffer(field_20466);
      field_20466.rewind();
      method_21972(field_20466);
   }

   public static void method_22000(float float_1, float float_2, float float_3, float float_4) {
      if (float_1 != field_20487.red || float_2 != field_20487.green || float_3 != field_20487.blue || float_4 != field_20487.alpha) {
         field_20487.red = float_1;
         field_20487.green = float_2;
         field_20487.blue = float_3;
         field_20487.alpha = float_4;
         GL11.glColor4f(float_1, float_2, float_3, float_4);
      }

   }

   public static void method_21979(float float_1, float float_2) {
      GL11.glTexCoord2f(float_1, float_2);
   }

   public static void method_22014(float float_1, float float_2, float float_3) {
      GL11.glVertex3f(float_1, float_2, float_3);
   }

   public static void method_21930() {
      field_20487.red = -1.0F;
      field_20487.green = -1.0F;
      field_20487.blue = -1.0F;
      field_20487.alpha = -1.0F;
   }

   public static void method_22031(int int_1, int int_2, int int_3) {
      GL11.glNormalPointer(int_1, int_2, (long)int_3);
   }

   public static void method_21959(int int_1, int int_2, ByteBuffer byteBuffer_1) {
      GL11.glNormalPointer(int_1, int_2, byteBuffer_1);
   }

   public static void method_22032(int int_1, int int_2, int int_3, int int_4) {
      GL11.glTexCoordPointer(int_1, int_2, int_3, (long)int_4);
   }

   public static void method_21958(int int_1, int int_2, int int_3, ByteBuffer byteBuffer_1) {
      GL11.glTexCoordPointer(int_1, int_2, int_3, byteBuffer_1);
   }

   public static void method_22038(int int_1, int int_2, int int_3, int int_4) {
      GL11.glVertexPointer(int_1, int_2, int_3, (long)int_4);
   }

   public static void method_21988(int int_1, int int_2, int int_3, ByteBuffer byteBuffer_1) {
      GL11.glVertexPointer(int_1, int_2, int_3, byteBuffer_1);
   }

   public static void method_22043(int int_1, int int_2, int int_3, int int_4) {
      GL11.glColorPointer(int_1, int_2, int_3, (long)int_4);
   }

   public static void method_22005(int int_1, int int_2, int int_3, ByteBuffer byteBuffer_1) {
      GL11.glColorPointer(int_1, int_2, int_3, byteBuffer_1);
   }

   public static void method_21911(int int_1) {
      GL11.glDisableClientState(int_1);
   }

   public static void method_21913(int int_1) {
      GL11.glEnableClientState(int_1);
   }

   public static void method_21915(int int_1) {
      GL11.glBegin(int_1);
   }

   public static void method_21932() {
      GL11.glEnd();
   }

   public static void method_22037(int int_1, int int_2, int int_3) {
      GL11.glDrawArrays(int_1, int_2, int_3);
   }

   public static void method_22013(float float_1) {
      GL11.glLineWidth(float_1);
   }

   public static void method_21917(int int_1) {
      GL11.glCallList(int_1);
   }

   public static void method_22061(int int_1, int int_2) {
      GL11.glDeleteLists(int_1, int_2);
   }

   public static void method_22064(int int_1, int int_2) {
      GL11.glNewList(int_1, int_2);
   }

   public static void method_21933() {
      GL11.glEndList();
   }

   public static int method_21919(int int_1) {
      return GL11.glGenLists(int_1);
   }

   public static void method_22067(int int_1, int int_2) {
      GL11.glPixelStorei(int_1, int_2);
   }

   public static void method_21983(int int_1, float float_1) {
      GL11.glPixelTransferf(int_1, float_1);
   }

   public static void method_21956(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, ByteBuffer byteBuffer_1) {
      GL11.glReadPixels(int_1, int_2, int_3, int_4, int_5, int_6, byteBuffer_1);
   }

   public static void method_21955(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, long long_1) {
      GL11.glReadPixels(int_1, int_2, int_3, int_4, int_5, int_6, long_1);
   }

   public static int method_21934() {
      return GL11.glGetError();
   }

   public static String method_21921(int int_1) {
      return GL11.glGetString(int_1);
   }

   public static void method_22027(int int_1, IntBuffer intBuffer_1) {
      GL11.glGetIntegerv(int_1, intBuffer_1);
   }

   public static int method_21923(int int_1) {
      return GL11.glGetInteger(int_1);
   }

   public static void method_21967(class_4493.RenderMode class_4493$RenderMode_1) {
      class_4493$RenderMode_1.begin();
   }

   public static void method_21994(class_4493.RenderMode class_4493$RenderMode_1) {
      class_4493$RenderMode_1.end();
   }

   @Environment(EnvType.CLIENT)
   public static enum RenderMode {
      DEFAULT {
         public void begin() {
            RenderSystem.disableAlphaTest();
            RenderSystem.alphaFunc(519, 0.0F);
            RenderSystem.disableLighting();
            RenderSystem.lightModel(2899, GuiLighting.singletonBuffer(0.2F, 0.2F, 0.2F, 1.0F));

            for(int int_1 = 0; int_1 < 8; ++int_1) {
               RenderSystem.disableLight(int_1);
               RenderSystem.light(16384 + int_1, 4608, GuiLighting.singletonBuffer(0.0F, 0.0F, 0.0F, 1.0F));
               RenderSystem.light(16384 + int_1, 4611, GuiLighting.singletonBuffer(0.0F, 0.0F, 1.0F, 0.0F));
               if (int_1 == 0) {
                  RenderSystem.light(16384 + int_1, 4609, GuiLighting.singletonBuffer(1.0F, 1.0F, 1.0F, 1.0F));
                  RenderSystem.light(16384 + int_1, 4610, GuiLighting.singletonBuffer(1.0F, 1.0F, 1.0F, 1.0F));
               } else {
                  RenderSystem.light(16384 + int_1, 4609, GuiLighting.singletonBuffer(0.0F, 0.0F, 0.0F, 1.0F));
                  RenderSystem.light(16384 + int_1, 4610, GuiLighting.singletonBuffer(0.0F, 0.0F, 0.0F, 1.0F));
               }
            }

            RenderSystem.disableColorMaterial();
            RenderSystem.colorMaterial(1032, 5634);
            RenderSystem.disableDepthTest();
            RenderSystem.depthFunc(513);
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.blendFunc(class_4493.class_4535.ONE, class_4493.class_4534.ZERO);
            RenderSystem.blendFuncSeparate(class_4493.class_4535.ONE, class_4493.class_4534.ZERO, class_4493.class_4535.ONE, class_4493.class_4534.ZERO);
            RenderSystem.blendEquation(32774);
            RenderSystem.disableFog();
            RenderSystem.fogi(2917, 2048);
            RenderSystem.fogDensity(1.0F);
            RenderSystem.fogStart(0.0F);
            RenderSystem.fogEnd(1.0F);
            RenderSystem.fog(2918, GuiLighting.singletonBuffer(0.0F, 0.0F, 0.0F, 0.0F));
            if (GL.getCapabilities().GL_NV_fog_distance) {
               RenderSystem.fogi(2917, 34140);
            }

            RenderSystem.polygonOffset(0.0F, 0.0F);
            RenderSystem.disableColorLogicOp();
            RenderSystem.logicOp(5379);
            RenderSystem.disableTexGen(class_4493.TexCoord.S);
            RenderSystem.texGenMode(class_4493.TexCoord.S, 9216);
            RenderSystem.texGenParam(class_4493.TexCoord.S, 9474, GuiLighting.singletonBuffer(1.0F, 0.0F, 0.0F, 0.0F));
            RenderSystem.texGenParam(class_4493.TexCoord.S, 9217, GuiLighting.singletonBuffer(1.0F, 0.0F, 0.0F, 0.0F));
            RenderSystem.disableTexGen(class_4493.TexCoord.T);
            RenderSystem.texGenMode(class_4493.TexCoord.T, 9216);
            RenderSystem.texGenParam(class_4493.TexCoord.T, 9474, GuiLighting.singletonBuffer(0.0F, 1.0F, 0.0F, 0.0F));
            RenderSystem.texGenParam(class_4493.TexCoord.T, 9217, GuiLighting.singletonBuffer(0.0F, 1.0F, 0.0F, 0.0F));
            RenderSystem.disableTexGen(class_4493.TexCoord.R);
            RenderSystem.texGenMode(class_4493.TexCoord.R, 9216);
            RenderSystem.texGenParam(class_4493.TexCoord.R, 9474, GuiLighting.singletonBuffer(0.0F, 0.0F, 0.0F, 0.0F));
            RenderSystem.texGenParam(class_4493.TexCoord.R, 9217, GuiLighting.singletonBuffer(0.0F, 0.0F, 0.0F, 0.0F));
            RenderSystem.disableTexGen(class_4493.TexCoord.Q);
            RenderSystem.texGenMode(class_4493.TexCoord.Q, 9216);
            RenderSystem.texGenParam(class_4493.TexCoord.Q, 9474, GuiLighting.singletonBuffer(0.0F, 0.0F, 0.0F, 0.0F));
            RenderSystem.texGenParam(class_4493.TexCoord.Q, 9217, GuiLighting.singletonBuffer(0.0F, 0.0F, 0.0F, 0.0F));
            RenderSystem.activeTexture(0);
            RenderSystem.texParameter(3553, 10240, 9729);
            RenderSystem.texParameter(3553, 10241, 9986);
            RenderSystem.texParameter(3553, 10242, 10497);
            RenderSystem.texParameter(3553, 10243, 10497);
            RenderSystem.texParameter(3553, 33085, 1000);
            RenderSystem.texParameter(3553, 33083, 1000);
            RenderSystem.texParameter(3553, 33082, -1000);
            RenderSystem.texParameter(3553, 34049, 0.0F);
            RenderSystem.texEnv(8960, 8704, 8448);
            RenderSystem.texEnv(8960, 8705, GuiLighting.singletonBuffer(0.0F, 0.0F, 0.0F, 0.0F));
            RenderSystem.texEnv(8960, 34161, 8448);
            RenderSystem.texEnv(8960, 34162, 8448);
            RenderSystem.texEnv(8960, 34176, 5890);
            RenderSystem.texEnv(8960, 34177, 34168);
            RenderSystem.texEnv(8960, 34178, 34166);
            RenderSystem.texEnv(8960, 34184, 5890);
            RenderSystem.texEnv(8960, 34185, 34168);
            RenderSystem.texEnv(8960, 34186, 34166);
            RenderSystem.texEnv(8960, 34192, 768);
            RenderSystem.texEnv(8960, 34193, 768);
            RenderSystem.texEnv(8960, 34194, 770);
            RenderSystem.texEnv(8960, 34200, 770);
            RenderSystem.texEnv(8960, 34201, 770);
            RenderSystem.texEnv(8960, 34202, 770);
            RenderSystem.texEnv(8960, 34163, 1.0F);
            RenderSystem.texEnv(8960, 3356, 1.0F);
            RenderSystem.disableNormalize();
            RenderSystem.shadeModel(7425);
            RenderSystem.disableRescaleNormal();
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.clearDepth(1.0D);
            RenderSystem.lineWidth(1.0F);
            RenderSystem.normal3f(0.0F, 0.0F, 1.0F);
            RenderSystem.polygonMode(1028, 6914);
            RenderSystem.polygonMode(1029, 6914);
         }

         public void end() {
         }
      },
      PLAYER_SKIN {
         public void begin() {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
         }

         public void end() {
            RenderSystem.disableBlend();
         }
      },
      TRANSPARENT_MODEL {
         public void begin() {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.15F);
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(class_4493.class_4535.SRC_ALPHA, class_4493.class_4534.ONE_MINUS_SRC_ALPHA);
            RenderSystem.alphaFunc(516, 0.003921569F);
         }

         public void end() {
            RenderSystem.disableBlend();
            RenderSystem.alphaFunc(516, 0.1F);
            RenderSystem.depthMask(true);
         }
      };

      private RenderMode() {
      }

      public abstract void begin();

      public abstract void end();

      // $FF: synthetic method
      RenderMode(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum class_4534 {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_COLOR(768),
      ZERO(0);

      public final int value;

      private class_4534(int int_1) {
         this.value = int_1;
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum class_4535 {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_ALPHA_SATURATE(776),
      SRC_COLOR(768),
      ZERO(0);

      public final int value;

      private class_4535(int int_1) {
         this.value = int_1;
      }
   }

   @Environment(EnvType.CLIENT)
   static class CapabilityTracker {
      private final int cap;
      private boolean state;

      public CapabilityTracker(int int_1) {
         this.cap = int_1;
      }

      public void disable() {
         this.setState(false);
      }

      public void enable() {
         this.setState(true);
      }

      public void setState(boolean boolean_1) {
         if (boolean_1 != this.state) {
            this.state = boolean_1;
            if (boolean_1) {
               GL11.glEnable(this.cap);
            } else {
               GL11.glDisable(this.cap);
            }
         }

      }
   }

   @Environment(EnvType.CLIENT)
   static class Color4 {
      public float red;
      public float green;
      public float blue;
      public float alpha;

      public Color4() {
         this(1.0F, 1.0F, 1.0F, 1.0F);
      }

      public Color4(float float_1, float float_2, float float_3, float float_4) {
         this.red = 1.0F;
         this.green = 1.0F;
         this.blue = 1.0F;
         this.alpha = 1.0F;
         this.red = float_1;
         this.green = float_2;
         this.blue = float_3;
         this.alpha = float_4;
      }
   }

   @Environment(EnvType.CLIENT)
   static class ColorMask {
      public boolean red;
      public boolean green;
      public boolean blue;
      public boolean alpha;

      private ColorMask() {
         this.red = true;
         this.green = true;
         this.blue = true;
         this.alpha = true;
      }

      // $FF: synthetic method
      ColorMask(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum TexCoord {
      S,
      T,
      R,
      Q;
   }

   @Environment(EnvType.CLIENT)
   static class TexGenCoordState {
      public final class_4493.CapabilityTracker capState;
      public final int coord;
      public int mode = -1;

      public TexGenCoordState(int int_1, int int_2) {
         this.coord = int_1;
         this.capState = new class_4493.CapabilityTracker(int_2);
      }
   }

   @Environment(EnvType.CLIENT)
   static class TexGenState {
      public final class_4493.TexGenCoordState s;
      public final class_4493.TexGenCoordState t;
      public final class_4493.TexGenCoordState r;
      public final class_4493.TexGenCoordState q;

      private TexGenState() {
         this.s = new class_4493.TexGenCoordState(8192, 3168);
         this.t = new class_4493.TexGenCoordState(8193, 3169);
         this.r = new class_4493.TexGenCoordState(8194, 3170);
         this.q = new class_4493.TexGenCoordState(8195, 3171);
      }

      // $FF: synthetic method
      TexGenState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class StencilState {
      public final class_4493.StencilSubState subState;
      public int field_5153;
      public int field_5152;
      public int field_5151;
      public int field_5150;

      private StencilState() {
         this.subState = new class_4493.StencilSubState();
         this.field_5153 = -1;
         this.field_5152 = 7680;
         this.field_5151 = 7680;
         this.field_5150 = 7680;
      }

      // $FF: synthetic method
      StencilState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class StencilSubState {
      public int func;
      public int field_16203;
      public int field_5147;

      private StencilSubState() {
         this.func = 519;
         this.field_5147 = -1;
      }

      // $FF: synthetic method
      StencilSubState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class ClearState {
      public double clearDepth;
      public final class_4493.Color4 clearColor;
      public int field_16202;

      private ClearState() {
         this.clearDepth = 1.0D;
         this.clearColor = new class_4493.Color4(0.0F, 0.0F, 0.0F, 0.0F);
      }

      // $FF: synthetic method
      ClearState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class LogicOpState {
      public final class_4493.CapabilityTracker capState;
      public int opcode;

      private LogicOpState() {
         this.capState = new class_4493.CapabilityTracker(3058);
         this.opcode = 5379;
      }

      // $FF: synthetic method
      LogicOpState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class PolygonOffsetState {
      public final class_4493.CapabilityTracker capFill;
      public final class_4493.CapabilityTracker capLine;
      public float factor;
      public float units;

      private PolygonOffsetState() {
         this.capFill = new class_4493.CapabilityTracker(32823);
         this.capLine = new class_4493.CapabilityTracker(10754);
      }

      // $FF: synthetic method
      PolygonOffsetState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class CullFaceState {
      public final class_4493.CapabilityTracker capState;
      public int mode;

      private CullFaceState() {
         this.capState = new class_4493.CapabilityTracker(2884);
         this.mode = 1029;
      }

      // $FF: synthetic method
      CullFaceState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class FogState {
      public final class_4493.CapabilityTracker capState;
      public int mode;
      public float density;
      public float start;
      public float end;

      private FogState() {
         this.capState = new class_4493.CapabilityTracker(2912);
         this.mode = 2048;
         this.density = 1.0F;
         this.end = 1.0F;
      }

      // $FF: synthetic method
      FogState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class DepthTestState {
      public final class_4493.CapabilityTracker capState;
      public boolean mask;
      public int func;

      private DepthTestState() {
         this.capState = new class_4493.CapabilityTracker(2929);
         this.mask = true;
         this.func = 513;
      }

      // $FF: synthetic method
      DepthTestState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class BlendFuncState {
      public final class_4493.CapabilityTracker capState;
      public int sfactor;
      public int dfactor;
      public int srcAlpha;
      public int dstAlpha;

      private BlendFuncState() {
         this.capState = new class_4493.CapabilityTracker(3042);
         this.sfactor = 1;
         this.dfactor = 0;
         this.srcAlpha = 1;
         this.dstAlpha = 0;
      }

      // $FF: synthetic method
      BlendFuncState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class ColorMaterialState {
      public final class_4493.CapabilityTracker capState;
      public int face;
      public int mode;

      private ColorMaterialState() {
         this.capState = new class_4493.CapabilityTracker(2903);
         this.face = 1032;
         this.mode = 5634;
      }

      // $FF: synthetic method
      ColorMaterialState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class AlphaTestState {
      public final class_4493.CapabilityTracker capState;
      public int func;
      public float ref;

      private AlphaTestState() {
         this.capState = new class_4493.CapabilityTracker(3008);
         this.func = 519;
         this.ref = -1.0F;
      }

      // $FF: synthetic method
      AlphaTestState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   static class Texture2DState {
      public final class_4493.CapabilityTracker capState;
      public int boundTexture;

      private Texture2DState() {
         this.capState = new class_4493.CapabilityTracker(3553);
      }

      // $FF: synthetic method
      Texture2DState(Object class_4493$1_1) {
         this();
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum FBOMode {
      BASE,
      ARB,
      EXT;
   }

   @Environment(EnvType.CLIENT)
   public static enum Viewport {
      INSTANCE;

      protected int x;
      protected int y;
      protected int width;
      protected int height;
   }

   @Environment(EnvType.CLIENT)
   public static enum LogicOp {
      AND(5377),
      AND_INVERTED(5380),
      AND_REVERSE(5378),
      CLEAR(5376),
      COPY(5379),
      COPY_INVERTED(5388),
      EQUIV(5385),
      INVERT(5386),
      NAND(5390),
      NOOP(5381),
      NOR(5384),
      OR(5383),
      OR_INVERTED(5389),
      OR_REVERSE(5387),
      SET(5391),
      XOR(5382);

      public final int glValue;

      private LogicOp(int int_1) {
         this.glValue = int_1;
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum FaceSides {
      FRONT(1028),
      BACK(1029),
      FRONT_AND_BACK(1032);

      public final int glValue;

      private FaceSides(int int_1) {
         this.glValue = int_1;
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum FogMode {
      LINEAR(9729),
      EXP(2048),
      EXP2(2049);

      public final int glValue;

      private FogMode(int int_1) {
         this.glValue = int_1;
      }
   }
}
