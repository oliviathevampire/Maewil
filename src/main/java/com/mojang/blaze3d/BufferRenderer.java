package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.nio.ByteBuffer;
import java.util.List;

@Environment(EnvType.CLIENT)
public class BufferRenderer {
   public void draw(BufferBuilder bufferBuilder_1) {
      if (bufferBuilder_1.getVertexCount() > 0) {
         VertexFormat vertexFormat_1 = bufferBuilder_1.getVertexFormat();
         int int_1 = vertexFormat_1.getVertexSize();
         ByteBuffer byteBuffer_1 = bufferBuilder_1.getByteBuffer();
         List<VertexFormatElement> list_1 = vertexFormat_1.getElements();

         int int_5;
         int int_7;
         for(int_5 = 0; int_5 < list_1.size(); ++int_5) {
            VertexFormatElement vertexFormatElement_1 = (VertexFormatElement)list_1.get(int_5);
            VertexFormatElement.Type vertexFormatElement$Type_1 = vertexFormatElement_1.getType();
            int int_3 = vertexFormatElement_1.getFormat().getGlId();
            int_7 = vertexFormatElement_1.getIndex();
            byteBuffer_1.position(vertexFormat_1.getElementOffset(int_5));
            switch(vertexFormatElement$Type_1) {
            case POSITION:
               RenderSystem.vertexPointer(vertexFormatElement_1.getCount(), int_3, int_1, byteBuffer_1);
               RenderSystem.enableClientState(32884);
               break;
            case UV:
               RenderSystem.glClientActiveTexture('蓀' + int_7);
               RenderSystem.texCoordPointer(vertexFormatElement_1.getCount(), int_3, int_1, byteBuffer_1);
               RenderSystem.enableClientState(32888);
               RenderSystem.glClientActiveTexture(33984);
               break;
            case COLOR:
               RenderSystem.colorPointer(vertexFormatElement_1.getCount(), int_3, int_1, byteBuffer_1);
               RenderSystem.enableClientState(32886);
               break;
            case NORMAL:
               RenderSystem.normalPointer(int_3, int_1, byteBuffer_1);
               RenderSystem.enableClientState(32885);
            }
         }

         RenderSystem.drawArrays(bufferBuilder_1.getDrawMode(), 0, bufferBuilder_1.getVertexCount());
         int_5 = 0;

         for(int int_6 = list_1.size(); int_5 < int_6; ++int_5) {
            VertexFormatElement vertexFormatElement_2 = (VertexFormatElement)list_1.get(int_5);
            VertexFormatElement.Type vertexFormatElement$Type_2 = vertexFormatElement_2.getType();
            int_7 = vertexFormatElement_2.getIndex();
            switch(vertexFormatElement$Type_2) {
            case POSITION:
               RenderSystem.disableClientState(32884);
               break;
            case UV:
               RenderSystem.glClientActiveTexture('蓀' + int_7);
               RenderSystem.disableClientState(32888);
               RenderSystem.glClientActiveTexture(33984);
               break;
            case COLOR:
               RenderSystem.disableClientState(32886);
               RenderSystem.clearCurrentColor();
               break;
            case NORMAL:
               RenderSystem.disableClientState(32885);
            }
         }
      }

      bufferBuilder_1.clear();
   }
}
