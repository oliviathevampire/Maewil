package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class VertexFormatElement {
   private static final Logger LOGGER = LogManager.getLogger();
   private final VertexFormatElement.Format format;
   private final VertexFormatElement.Type type;
   private final int index;
   private final int count;

   public VertexFormatElement(int int_1, VertexFormatElement.Format vertexFormatElement$Format_1, VertexFormatElement.Type vertexFormatElement$Type_1, int int_2) {
      if (this.isValidType(int_1, vertexFormatElement$Type_1)) {
         this.type = vertexFormatElement$Type_1;
      } else {
         LOGGER.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
         this.type = VertexFormatElement.Type.UV;
      }

      this.format = vertexFormatElement$Format_1;
      this.index = int_1;
      this.count = int_2;
   }

   private final boolean isValidType(int int_1, VertexFormatElement.Type vertexFormatElement$Type_1) {
      return int_1 == 0 || vertexFormatElement$Type_1 == VertexFormatElement.Type.UV;
   }

   public final VertexFormatElement.Format getFormat() {
      return this.format;
   }

   public final VertexFormatElement.Type getType() {
      return this.type;
   }

   public final int getCount() {
      return this.count;
   }

   public final int getIndex() {
      return this.index;
   }

   public String toString() {
      return this.count + "," + this.type.getName() + "," + this.format.getName();
   }

   public final int getSize() {
      return this.format.getSize() * this.count;
   }

   public final boolean isPosition() {
      return this.type == VertexFormatElement.Type.POSITION;
   }

   public boolean equals(Object object_1) {
      if (this == object_1) {
         return true;
      } else if (object_1 != null && this.getClass() == object_1.getClass()) {
         VertexFormatElement vertexFormatElement_1 = (VertexFormatElement)object_1;
         if (this.count != vertexFormatElement_1.count) {
            return false;
         } else if (this.index != vertexFormatElement_1.index) {
            return false;
         } else if (this.format != vertexFormatElement_1.format) {
            return false;
         } else {
            return this.type == vertexFormatElement_1.type;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int int_1 = this.format.hashCode();
      int_1 = 31 * int_1 + this.type.hashCode();
      int_1 = 31 * int_1 + this.index;
      int_1 = 31 * int_1 + this.count;
      return int_1;
   }

   @Environment(EnvType.CLIENT)
   public static enum Format {
      FLOAT(4, "Float", 5126),
      UBYTE(1, "Unsigned Byte", 5121),
      BYTE(1, "Byte", 5120),
      USHORT(2, "Unsigned Short", 5123),
      SHORT(2, "Short", 5122),
      UINT(4, "Unsigned Int", 5125),
      INT(4, "Int", 5124);

      private final int size;
      private final String name;
      private final int glId;

      private Format(int int_1, String string_1, int int_2) {
         this.size = int_1;
         this.name = string_1;
         this.glId = int_2;
      }

      public int getSize() {
         return this.size;
      }

      public String getName() {
         return this.name;
      }

      public int getGlId() {
         return this.glId;
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum Type {
      POSITION("Position"),
      NORMAL("Normal"),
      COLOR("Vertex Color"),
      UV("UV"),
      MATRIX("Bone Matrix"),
      BLEND_WEIGHT("Blend Weight"),
      PADDING("Padding");

      private final String name;

      private Type(String string_1) {
         this.name = string_1;
      }

      public String getName() {
         return this.name;
      }
   }
}
