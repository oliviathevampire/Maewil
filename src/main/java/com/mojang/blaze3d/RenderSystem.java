package com.mojang.blaze3d;

import com.google.common.collect.Queues;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.GLFWErrorCallbackI;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.LongSupplier;

@Environment(EnvType.CLIENT)
public class RenderSystem {
   private static final ConcurrentLinkedQueue<Runnable> recordingQueue = Queues.newConcurrentLinkedQueue();
   private static Thread clientThread;
   private static Thread renderThread;

   public static void initClientThread() {
      if (clientThread == null && renderThread != Thread.currentThread()) {
         clientThread = Thread.currentThread();
      } else {
         throw new IllegalStateException("Could not initialize tick thread");
      }
   }

   public static boolean isOnClientThread() {
      return clientThread == Thread.currentThread();
   }

   public static void initRenderThread() {
      if (renderThread == null && clientThread != Thread.currentThread()) {
         renderThread = Thread.currentThread();
      } else {
         throw new IllegalStateException("Could not initialize render thread");
      }
   }

   public static boolean isOnRenderThread() {
      return renderThread == Thread.currentThread();
   }

   public static void pushLightingAttributes() {
      class_4493.method_21935();
   }

   public static void pushTextureAttributes() {
      class_4493.method_21976();
   }

   public static void popAttributes() {
      class_4493.method_21997();
   }

   public static void disableAlphaTest() {
      class_4493.method_22012();
   }

   public static void enableAlphaTest() {
      class_4493.method_22021();
   }

   public static void alphaFunc(int int_1, float float_1) {
      class_4493.method_21945(int_1, float_1);
   }

   public static void enableLighting() {
      class_4493.method_22028();
   }

   public static void disableLighting() {
      class_4493.method_22034();
   }

   public static void enableLight(int int_1) {
      class_4493.method_21944(int_1);
   }

   public static void disableLight(int int_1) {
      class_4493.method_21982(int_1);
   }

   public static void enableColorMaterial() {
      class_4493.method_22040();
   }

   public static void disableColorMaterial() {
      class_4493.method_22044();
   }

   public static void colorMaterial(int int_1, int int_2) {
      class_4493.method_21947(int_1, int_2);
   }

   public static void light(int int_1, int int_2, FloatBuffer floatBuffer_1) {
      class_4493.method_21960(int_1, int_2, floatBuffer_1);
   }

   public static void lightModel(int int_1, FloatBuffer floatBuffer_1) {
      class_4493.method_21963(int_1, floatBuffer_1);
   }

   public static void normal3f(float float_1, float float_2, float float_3) {
      class_4493.method_21942(float_1, float_2, float_3);
   }

   public static void disableDepthTest() {
      class_4493.method_22047();
   }

   public static void enableDepthTest() {
      class_4493.method_22050();
   }

   public static void depthFunc(int int_1) {
      class_4493.method_22001(int_1);
   }

   public static void depthMask(boolean boolean_1) {
      class_4493.method_21974(boolean_1);
   }

   public static void enableBlend() {
      class_4493.method_22056();
   }

   public static void disableBlend() {
      class_4493.method_22053();
   }

   public static void blendFunc(class_4493.class_4535 class_4493$class_4535_1, class_4493.class_4534 class_4493$class_4534_1) {
      class_4493.method_21984(class_4493$class_4535_1.value, class_4493$class_4534_1.value);
   }

   public static void blendFunc(int int_1, int int_2) {
      class_4493.method_21984(int_1, int_2);
   }

   public static void blendFuncSeparate(class_4493.class_4535 class_4493$class_4535_1, class_4493.class_4534 class_4493$class_4534_1, class_4493.class_4535 class_4493$class_4535_2, class_4493.class_4534 class_4493$class_4534_2) {
      class_4493.method_21950(class_4493$class_4535_1.value, class_4493$class_4534_1.value, class_4493$class_4535_2.value, class_4493$class_4534_2.value);
   }

   public static void blendFuncSeparate(int int_1, int int_2, int int_3, int int_4) {
      class_4493.method_21950(int_1, int_2, int_3, int_4);
   }

   public static void blendEquation(int int_1) {
      class_4493.method_22015(int_1);
   }

   public static void setupSolidRenderingTextureCombine(int int_1) {
      class_4493.method_22022(int_1);
   }

   public static void tearDownSolidRenderingTextureCombine() {
      class_4493.method_22059();
   }

   public static void enableFog() {
      class_4493.method_22072();
   }

   public static void disableFog() {
      class_4493.method_22074();
   }

   public static void fogMode(class_4493.FogMode class_4493$FogMode_1) {
      class_4493.method_22071(class_4493$FogMode_1.glValue);
   }

   public static void fogMode(int int_1) {
      class_4493.method_22071(int_1);
   }

   public static void fogDensity(float float_1) {
      class_4493.method_21940(float_1);
   }

   public static void fogStart(float float_1) {
      class_4493.method_21978(float_1);
   }

   public static void fogEnd(float float_1) {
      class_4493.method_21998(float_1);
   }

   public static void fog(int int_1, FloatBuffer floatBuffer_1) {
      class_4493.method_22033(int_1, floatBuffer_1);
   }

   public static void fogi(int int_1, int int_2) {
      class_4493.method_22055(int_1, int_2);
   }

   public static void enableCull() {
      class_4493.method_22076();
   }

   public static void disableCull() {
      class_4493.method_22078();
   }

   public static void cullFace(class_4493.FaceSides class_4493$FaceSides_1) {
      class_4493.method_22073(class_4493$FaceSides_1.glValue);
   }

   public static void cullFace(int int_1) {
      class_4493.method_22073(int_1);
   }

   public static void polygonMode(int int_1, int int_2) {
      class_4493.method_22058(int_1, int_2);
   }

   public static void enablePolygonOffset() {
      class_4493.method_22080();
   }

   public static void disablePolygonOffset() {
      class_4493.method_22082();
   }

   public static void enableLineOffset() {
      class_4493.method_22084();
   }

   public static void disableLineOffset() {
      class_4493.method_22086();
   }

   public static void polygonOffset(float float_1, float float_2) {
      class_4493.method_21941(float_1, float_2);
   }

   public static void enableColorLogicOp() {
      class_4493.method_21906();
   }

   public static void disableColorLogicOp() {
      class_4493.method_21908();
   }

   public static void logicOp(class_4493.LogicOp class_4493$LogicOp_1) {
      class_4493.method_22075(class_4493$LogicOp_1.glValue);
   }

   public static void logicOp(int int_1) {
      class_4493.method_22075(int_1);
   }

   public static void enableTexGen(class_4493.TexCoord class_4493$TexCoord_1) {
      class_4493.method_21968(class_4493$TexCoord_1);
   }

   public static void disableTexGen(class_4493.TexCoord class_4493$TexCoord_1) {
      class_4493.method_21995(class_4493$TexCoord_1);
   }

   public static void texGenMode(class_4493.TexCoord class_4493$TexCoord_1, int int_1) {
      class_4493.method_21969(class_4493$TexCoord_1, int_1);
   }

   public static void texGenParam(class_4493.TexCoord class_4493$TexCoord_1, int int_1, FloatBuffer floatBuffer_1) {
      class_4493.method_21970(class_4493$TexCoord_1, int_1, floatBuffer_1);
   }

   public static void activeTexture(int int_1) {
      class_4493.method_22077(int_1);
   }

   public static void enableTexture() {
      class_4493.method_21910();
   }

   public static void disableTexture() {
      class_4493.method_21912();
   }

   public static void texEnv(int int_1, int int_2, FloatBuffer floatBuffer_1) {
      class_4493.method_21989(int_1, int_2, floatBuffer_1);
   }

   public static void texEnv(int int_1, int int_2, int int_3) {
      class_4493.method_21949(int_1, int_2, int_3);
   }

   public static void texEnv(int int_1, int int_2, float float_1) {
      class_4493.method_21948(int_1, int_2, float_1);
   }

   public static void texParameter(int int_1, int int_2, float float_1) {
      class_4493.method_21985(int_1, int_2, float_1);
   }

   public static void texParameter(int int_1, int int_2, int int_3) {
      class_4493.method_21986(int_1, int_2, int_3);
   }

   public static int getTexLevelParameter(int int_1, int int_2, int int_3) {
      return class_4493.method_22003(int_1, int_2, int_3);
   }

   public static int genTexture() {
      return class_4493.method_21914();
   }

   public static void deleteTexture(int int_1) {
      class_4493.method_22079(int_1);
   }

   public static void bindTexture(int int_1) {
      class_4493.method_22081(int_1);
   }

   public static void texImage2D(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, int int_8, @Nullable IntBuffer intBuffer_1) {
      class_4493.method_21954(int_1, int_2, int_3, int_4, int_5, int_6, int_7, int_8, intBuffer_1);
   }

   public static void texSubImage2D(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, int int_8, long long_1) {
      class_4493.method_21953(int_1, int_2, int_3, int_4, int_5, int_6, int_7, int_8, long_1);
   }

   public static void copyTexSubImage2D(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, int int_8) {
      class_4493.method_21952(int_1, int_2, int_3, int_4, int_5, int_6, int_7, int_8);
   }

   public static void getTexImage(int int_1, int int_2, int int_3, int int_4, long long_1) {
      class_4493.method_21957(int_1, int_2, int_3, int_4, long_1);
   }

   public static void enableNormalize() {
      class_4493.method_21916();
   }

   public static void disableNormalize() {
      class_4493.method_21918();
   }

   public static void shadeModel(int int_1) {
      class_4493.method_22083(int_1);
   }

   public static void enableRescaleNormal() {
      class_4493.method_21920();
   }

   public static void disableRescaleNormal() {
      class_4493.method_21922();
   }

   public static void viewport(int int_1, int int_2, int int_3, int int_4) {
      class_4493.method_22025(int_1, int_2, int_3, int_4);
   }

   public static void colorMask(boolean boolean_1, boolean boolean_2, boolean boolean_3, boolean boolean_4) {
      class_4493.method_21975(boolean_1, boolean_2, boolean_3, boolean_4);
   }

   public static void stencilFunc(int int_1, int int_2, int int_3) {
      class_4493.method_22017(int_1, int_2, int_3);
   }

   public static void stencilMask(int int_1) {
      class_4493.method_22085(int_1);
   }

   public static void stencilOp(int int_1, int int_2, int int_3) {
      class_4493.method_22024(int_1, int_2, int_3);
   }

   public static void clearDepth(double double_1) {
      class_4493.method_21936(double_1);
   }

   public static void clearColor(float float_1, float float_2, float float_3, float float_4) {
      class_4493.method_21943(float_1, float_2, float_3, float_4);
   }

   public static void clearStencil(int int_1) {
      class_4493.method_22087(int_1);
   }

   public static void clear(int int_1, boolean boolean_1) {
      class_4493.method_21965(int_1, boolean_1);
   }

   public static void matrixMode(int int_1) {
      class_4493.method_21907(int_1);
   }

   public static void loadIdentity() {
      class_4493.method_21924();
   }

   public static void pushMatrix() {
      class_4493.method_21926();
   }

   public static void popMatrix() {
      class_4493.method_21928();
   }

   public static void getMatrix(int int_1, FloatBuffer floatBuffer_1) {
      class_4493.method_22039(int_1, floatBuffer_1);
   }

   public static Matrix4f getMatrix4f(int int_1) {
      return class_4493.method_21909(int_1);
   }

   public static void ortho(double double_1, double double_2, double double_3, double double_4, double double_5, double double_6) {
      class_4493.method_21939(double_1, double_2, double_3, double_4, double_5, double_6);
   }

   public static void rotatef(float float_1, float float_2, float float_3, float float_4) {
      class_4493.method_21981(float_1, float_2, float_3, float_4);
   }

   public static void rotated(double double_1, double double_2, double double_3, double double_4) {
      class_4493.method_21938(double_1, double_2, double_3, double_4);
   }

   public static void scalef(float float_1, float float_2, float float_3) {
      class_4493.method_21980(float_1, float_2, float_3);
   }

   public static void scaled(double double_1, double double_2, double double_3) {
      class_4493.method_21937(double_1, double_2, double_3);
   }

   public static void translatef(float float_1, float float_2, float float_3) {
      class_4493.method_21999(float_1, float_2, float_3);
   }

   public static void translated(double double_1, double double_2, double double_3) {
      class_4493.method_21977(double_1, double_2, double_3);
   }

   public static void multMatrix(FloatBuffer floatBuffer_1) {
      class_4493.method_21972(floatBuffer_1);
   }

   public static void multMatrix(Matrix4f matrix4f_1) {
      class_4493.method_21971(matrix4f_1);
   }

   public static void color4f(float float_1, float float_2, float float_3, float float_4) {
      class_4493.method_22000(float_1, float_2, float_3, float_4);
   }

   public static void color3f(float float_1, float float_2, float float_3) {
      class_4493.method_22000(float_1, float_2, float_3, 1.0F);
   }

   public static void texCoord2f(float float_1, float float_2) {
      class_4493.method_21979(float_1, float_2);
   }

   public static void vertex3f(float float_1, float float_2, float float_3) {
      class_4493.method_22014(float_1, float_2, float_3);
   }

   public static void clearCurrentColor() {
      class_4493.method_21930();
   }

   public static void normalPointer(int int_1, int int_2, int int_3) {
      class_4493.method_22031(int_1, int_2, int_3);
   }

   public static void normalPointer(int int_1, int int_2, ByteBuffer byteBuffer_1) {
      class_4493.method_21959(int_1, int_2, byteBuffer_1);
   }

   public static void texCoordPointer(int int_1, int int_2, int int_3, int int_4) {
      class_4493.method_22032(int_1, int_2, int_3, int_4);
   }

   public static void texCoordPointer(int int_1, int int_2, int int_3, ByteBuffer byteBuffer_1) {
      class_4493.method_21958(int_1, int_2, int_3, byteBuffer_1);
   }

   public static void vertexPointer(int int_1, int int_2, int int_3, int int_4) {
      class_4493.method_22038(int_1, int_2, int_3, int_4);
   }

   public static void vertexPointer(int int_1, int int_2, int int_3, ByteBuffer byteBuffer_1) {
      class_4493.method_21988(int_1, int_2, int_3, byteBuffer_1);
   }

   public static void colorPointer(int int_1, int int_2, int int_3, int int_4) {
      class_4493.method_22043(int_1, int_2, int_3, int_4);
   }

   public static void colorPointer(int int_1, int int_2, int int_3, ByteBuffer byteBuffer_1) {
      class_4493.method_22005(int_1, int_2, int_3, byteBuffer_1);
   }

   public static void disableClientState(int int_1) {
      class_4493.method_21911(int_1);
   }

   public static void enableClientState(int int_1) {
      class_4493.method_21913(int_1);
   }

   public static void begin(int int_1) {
      class_4493.method_21915(int_1);
   }

   public static void end() {
      class_4493.method_21932();
   }

   public static void drawArrays(int int_1, int int_2, int int_3) {
      class_4493.method_22037(int_1, int_2, int_3);
   }

   public static void lineWidth(float float_1) {
      class_4493.method_22013(float_1);
   }

   public static void callList(int int_1) {
      class_4493.method_21917(int_1);
   }

   public static void deleteLists(int int_1, int int_2) {
      class_4493.method_22061(int_1, int_2);
   }

   public static void newList(int int_1, int int_2) {
      class_4493.method_22064(int_1, int_2);
   }

   public static void endList() {
      class_4493.method_21933();
   }

   public static int genLists(int int_1) {
      return class_4493.method_21919(int_1);
   }

   public static void pixelStore(int int_1, int int_2) {
      class_4493.method_22067(int_1, int_2);
   }

   public static void pixelTransfer(int int_1, float float_1) {
      class_4493.method_21983(int_1, float_1);
   }

   public static void readPixels(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, ByteBuffer byteBuffer_1) {
      class_4493.method_21956(int_1, int_2, int_3, int_4, int_5, int_6, byteBuffer_1);
   }

   public static void readPixels(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, long long_1) {
      class_4493.method_21955(int_1, int_2, int_3, int_4, int_5, int_6, long_1);
   }

   public static int getError() {
      return class_4493.method_21934();
   }

   public static String getString(int int_1) {
      return class_4493.method_21921(int_1);
   }

   public static void getInteger(int int_1, IntBuffer intBuffer_1) {
      class_4493.method_22027(int_1, intBuffer_1);
   }

   public static int getInteger(int int_1) {
      return class_4493.method_21923(int_1);
   }

   public static String getBackendDescription() {
      return String.format("LWJGL version %s", GLX._getLWJGLVersion());
   }

   public static String getApiDescription() {
      return GLX.getOpenGLVersionString();
   }

   public static LongSupplier initBackendSystem() {
      return GLX._initGlfw();
   }

   public static void initRenderer(int int_1, boolean boolean_1) {
      GLX._init(int_1, boolean_1);
   }

   public static void setErrorCallback(GLFWErrorCallbackI gLFWErrorCallbackI_1) {
      GLX._setGlfwErrorCallback(gLFWErrorCallbackI_1);
   }

   public static void pollEvents() {
      GLX._pollEvents();
   }

   public static void glClientActiveTexture(int int_1) {
      class_4493.method_22069(int_1);
   }

   public static void renderCrosshair(int int_1) {
      GLX._renderCrosshair(int_1, true, true, true);
   }

   public static void setupNvFogDistance() {
      GLX._setupNvFogDistance();
   }

   public static void glMultiTexCoord2f(int int_1, float float_1, float float_2) {
      class_4493.method_21946(int_1, float_1, float_2);
   }

   public static String getCapsString() {
      return GLX._getCapsString();
   }
}
