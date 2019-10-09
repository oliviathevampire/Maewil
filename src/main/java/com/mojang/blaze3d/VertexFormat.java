package com.mojang.blaze3d;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Environment(EnvType.CLIENT)
public class VertexFormat {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List<VertexFormatElement> elements;
   private final List<Integer> offsets;
   private int size;
   private int offsetColor;
   private final List<Integer> offsetsUv;
   private int offsetNormal;

   public VertexFormat(VertexFormat vertexFormat_1) {
      this();

      for(int int_1 = 0; int_1 < vertexFormat_1.getElementCount(); ++int_1) {
         this.add(vertexFormat_1.getElement(int_1));
      }

      this.size = vertexFormat_1.getVertexSize();
   }

   public VertexFormat() {
      this.elements = Lists.newArrayList();
      this.offsets = Lists.newArrayList();
      this.offsetColor = -1;
      this.offsetsUv = Lists.newArrayList();
      this.offsetNormal = -1;
   }

   public void clear() {
      this.elements.clear();
      this.offsets.clear();
      this.offsetColor = -1;
      this.offsetsUv.clear();
      this.offsetNormal = -1;
      this.size = 0;
   }

   public VertexFormat add(VertexFormatElement vertexFormatElement_1) {
      if (vertexFormatElement_1.isPosition() && this.hasPositionElement()) {
         LOGGER.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
         return this;
      } else {
         this.elements.add(vertexFormatElement_1);
         this.offsets.add(this.size);
         switch(vertexFormatElement_1.getType()) {
         case NORMAL:
            this.offsetNormal = this.size;
            break;
         case COLOR:
            this.offsetColor = this.size;
            break;
         case UV:
            this.offsetsUv.add(vertexFormatElement_1.getIndex(), this.size);
         }

         this.size += vertexFormatElement_1.getSize();
         return this;
      }
   }

   public boolean hasNormalElement() {
      return this.offsetNormal >= 0;
   }

   public int getNormalOffset() {
      return this.offsetNormal;
   }

   public boolean hasColorElement() {
      return this.offsetColor >= 0;
   }

   public int getColorOffset() {
      return this.offsetColor;
   }

   public boolean hasUvElement(int int_1) {
      return this.offsetsUv.size() - 1 >= int_1;
   }

   public int getUvOffset(int int_1) {
      return (Integer)this.offsetsUv.get(int_1);
   }

   public String toString() {
      String string_1 = "format: " + this.elements.size() + " elements: ";

      for(int int_1 = 0; int_1 < this.elements.size(); ++int_1) {
         string_1 = string_1 + ((VertexFormatElement)this.elements.get(int_1)).toString();
         if (int_1 != this.elements.size() - 1) {
            string_1 = string_1 + " ";
         }
      }

      return string_1;
   }

   private boolean hasPositionElement() {
      int int_1 = 0;

      for(int int_2 = this.elements.size(); int_1 < int_2; ++int_1) {
         VertexFormatElement vertexFormatElement_1 = (VertexFormatElement)this.elements.get(int_1);
         if (vertexFormatElement_1.isPosition()) {
            return true;
         }
      }

      return false;
   }

   public int getVertexSizeInteger() {
      return this.getVertexSize() / 4;
   }

   public int getVertexSize() {
      return this.size;
   }

   public List<VertexFormatElement> getElements() {
      return this.elements;
   }

   public int getElementCount() {
      return this.elements.size();
   }

   public VertexFormatElement getElement(int int_1) {
      return (VertexFormatElement)this.elements.get(int_1);
   }

   public int getElementOffset(int int_1) {
      return (Integer)this.offsets.get(int_1);
   }

   public boolean equals(Object object_1) {
      if (this == object_1) {
         return true;
      } else if (object_1 != null && this.getClass() == object_1.getClass()) {
         VertexFormat vertexFormat_1 = (VertexFormat)object_1;
         if (this.size != vertexFormat_1.size) {
            return false;
         } else {
            return !this.elements.equals(vertexFormat_1.elements) ? false : this.offsets.equals(vertexFormat_1.offsets);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int int_1 = this.elements.hashCode();
      int_1 = 31 * int_1 + this.offsets.hashCode();
      int_1 = 31 * int_1 + this.size;
      return int_1;
   }
}
