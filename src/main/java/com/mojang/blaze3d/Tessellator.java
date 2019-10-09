package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Tessellator {
   private final BufferBuilder buffer;
   private final BufferRenderer renderer = new BufferRenderer();
   private static final Tessellator INSTANCE = new Tessellator(2097152);

   public static Tessellator getInstance() {
      return INSTANCE;
   }

   public Tessellator(int int_1) {
      this.buffer = new BufferBuilder(int_1);
   }

   public void draw() {
      this.buffer.end();
      this.renderer.draw(this.buffer);
   }

   public BufferBuilder getBufferBuilder() {
      return this.buffer;
   }
}
