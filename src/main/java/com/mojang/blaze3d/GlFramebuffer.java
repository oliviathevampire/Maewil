package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.nio.IntBuffer;

@Environment(EnvType.CLIENT)
public class GlFramebuffer {
   public int texWidth;
   public int texHeight;
   public int viewWidth;
   public int viewHeight;
   public final boolean useDepthAttachment;
   public int fbo;
   public int colorAttachment;
   public int depthAttachment;
   public final float[] clearColor;
   public int texFilter;

   public GlFramebuffer(int int_1, int int_2, boolean boolean_1, boolean boolean_2) {
      this.useDepthAttachment = boolean_1;
      this.fbo = -1;
      this.colorAttachment = -1;
      this.depthAttachment = -1;
      this.clearColor = new float[4];
      this.clearColor[0] = 1.0F;
      this.clearColor[1] = 1.0F;
      this.clearColor[2] = 1.0F;
      this.clearColor[3] = 0.0F;
      this.resize(int_1, int_2, boolean_2);
   }

   public void resize(int int_1, int int_2, boolean boolean_1) {
      RenderSystem.enableDepthTest();
      if (this.fbo >= 0) {
         this.delete();
      }

      this.initFbo(int_1, int_2, boolean_1);
      class_4493.method_22042(class_4492.field_20457, 0);
   }

   public void delete() {
      this.endRead();
      this.endWrite();
      if (this.depthAttachment > -1) {
         class_4493.method_22057(this.depthAttachment);
         this.depthAttachment = -1;
      }

      if (this.colorAttachment > -1) {
         class_4536.releaseTextureId(this.colorAttachment);
         this.colorAttachment = -1;
      }

      if (this.fbo > -1) {
         class_4493.method_22042(class_4492.field_20457, 0);
         class_4493.method_22060(this.fbo);
         this.fbo = -1;
      }

   }

   public void initFbo(int int_1, int int_2, boolean boolean_1) {
      this.viewWidth = int_1;
      this.viewHeight = int_2;
      this.texWidth = int_1;
      this.texHeight = int_2;
      this.fbo = class_4493.method_22068();
      this.colorAttachment = class_4536.generateTextureId();
      if (this.useDepthAttachment) {
         this.depthAttachment = class_4493.method_22070();
      }

      this.setTexFilter(9728);
      RenderSystem.bindTexture(this.colorAttachment);
      RenderSystem.texImage2D(3553, 0, 32856, this.texWidth, this.texHeight, 0, 6408, 5121, (IntBuffer)null);
      class_4493.method_22042(class_4492.field_20457, this.fbo);
      class_4493.method_21951(class_4492.field_20457, class_4492.field_20459, 3553, this.colorAttachment, 0);
      if (this.useDepthAttachment) {
         class_4493.method_22046(class_4492.field_20458, this.depthAttachment);
         class_4493.method_21987(class_4492.field_20458, 33190, this.texWidth, this.texHeight);
         class_4493.method_22004(class_4492.field_20457, class_4492.field_20460, class_4492.field_20458, this.depthAttachment);
      }

      this.checkFramebufferStatus();
      this.clear(boolean_1);
      this.endRead();
   }

   public void setTexFilter(int int_1) {
      this.texFilter = int_1;
      RenderSystem.bindTexture(this.colorAttachment);
      RenderSystem.texParameter(3553, 10241, int_1);
      RenderSystem.texParameter(3553, 10240, int_1);
      RenderSystem.texParameter(3553, 10242, 10496);
      RenderSystem.texParameter(3553, 10243, 10496);
      RenderSystem.bindTexture(0);
   }

   public void checkFramebufferStatus() {
      int int_1 = class_4493.method_22063(class_4492.field_20457);
      if (int_1 != class_4492.field_20461) {
         if (int_1 == class_4492.field_20462) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
         } else if (int_1 == class_4492.field_20463) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
         } else if (int_1 == class_4492.field_20464) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
         } else if (int_1 == class_4492.field_20465) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
         } else {
            throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + int_1);
         }
      }
   }

   public void beginRead() {
      RenderSystem.bindTexture(this.colorAttachment);
   }

   public void endRead() {
      RenderSystem.bindTexture(0);
   }

   public void beginWrite(boolean boolean_1) {
      class_4493.method_22042(class_4492.field_20457, this.fbo);
      if (boolean_1) {
         RenderSystem.viewport(0, 0, this.viewWidth, this.viewHeight);
      }

   }

   public void endWrite() {
      class_4493.method_22042(class_4492.field_20457, 0);
   }

   public void setClearColor(float float_1, float float_2, float float_3, float float_4) {
      this.clearColor[0] = float_1;
      this.clearColor[1] = float_2;
      this.clearColor[2] = float_3;
      this.clearColor[3] = float_4;
   }

   public void draw(int int_1, int int_2) {
      this.draw(int_1, int_2, true);
   }

   public void draw(int int_1, int int_2, boolean boolean_1) {
      RenderSystem.colorMask(true, true, true, false);
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.matrixMode(5889);
      RenderSystem.loadIdentity();
      RenderSystem.ortho(0.0D, (double)int_1, (double)int_2, 0.0D, 1000.0D, 3000.0D);
      RenderSystem.matrixMode(5888);
      RenderSystem.loadIdentity();
      RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
      RenderSystem.viewport(0, 0, int_1, int_2);
      RenderSystem.enableTexture();
      RenderSystem.disableLighting();
      RenderSystem.disableAlphaTest();
      if (boolean_1) {
         RenderSystem.disableBlend();
         RenderSystem.enableColorMaterial();
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.beginRead();
      float float_1 = (float)int_1;
      float float_2 = (float)int_2;
      float float_3 = (float)this.viewWidth / (float)this.texWidth;
      float float_4 = (float)this.viewHeight / (float)this.texHeight;
      Tessellator tessellator_1 = Tessellator.getInstance();
      BufferBuilder bufferBuilder_1 = tessellator_1.getBufferBuilder();
      bufferBuilder_1.begin(7, VertexFormats.POSITION_UV_COLOR);
      bufferBuilder_1.vertex(0.0D, (double)float_2, 0.0D).texture(0.0D, 0.0D).color(255, 255, 255, 255).next();
      bufferBuilder_1.vertex((double)float_1, (double)float_2, 0.0D).texture((double)float_3, 0.0D).color(255, 255, 255, 255).next();
      bufferBuilder_1.vertex((double)float_1, 0.0D, 0.0D).texture((double)float_3, (double)float_4).color(255, 255, 255, 255).next();
      bufferBuilder_1.vertex(0.0D, 0.0D, 0.0D).texture(0.0D, (double)float_4).color(255, 255, 255, 255).next();
      tessellator_1.draw();
      this.endRead();
      RenderSystem.depthMask(true);
      RenderSystem.colorMask(true, true, true, true);
   }

   public void clear(boolean boolean_1) {
      this.beginWrite(true);
      RenderSystem.clearColor(this.clearColor[0], this.clearColor[1], this.clearColor[2], this.clearColor[3]);
      int int_1 = 16384;
      if (this.useDepthAttachment) {
         RenderSystem.clearDepth(1.0D);
         int_1 |= 256;
      }

      RenderSystem.clear(int_1, boolean_1);
      this.endWrite();
   }
}
