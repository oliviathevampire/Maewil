package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VertexFormats {
   public static final VertexFormatElement POSITION_ELEMENT;
   public static final VertexFormatElement COLOR_ELEMENT;
   public static final VertexFormatElement UV_ELEMENT;
   public static final VertexFormatElement LMAP_ELEMENT;
   public static final VertexFormatElement NORMAL_ELEMENT;
   public static final VertexFormatElement PADDING_ELEMENT;
   public static final VertexFormat POSITION_COLOR_UV_LMAP;
   public static final VertexFormat POSITION_COLOR_UV_NORMAL;
   public static final VertexFormat POSITION_UV_NORMAL_2;
   public static final VertexFormat POSITION_UV_COLOR_LMAP;
   public static final VertexFormat POSITION;
   public static final VertexFormat POSITION_COLOR;
   public static final VertexFormat POSITION_UV;
   public static final VertexFormat POSITION_NORMAL;
   public static final VertexFormat POSITION_UV_COLOR;
   public static final VertexFormat POSITION_UV_NORMAL;
   public static final VertexFormat POSITION_UV_LMAP_COLOR;
   public static final VertexFormat POSITION_UV_COLOR_NORMAL;

   static {
      POSITION_ELEMENT = new VertexFormatElement(0, VertexFormatElement.Format.FLOAT, VertexFormatElement.Type.POSITION, 3);
      COLOR_ELEMENT = new VertexFormatElement(0, VertexFormatElement.Format.UBYTE, VertexFormatElement.Type.COLOR, 4);
      UV_ELEMENT = new VertexFormatElement(0, VertexFormatElement.Format.FLOAT, VertexFormatElement.Type.UV, 2);
      LMAP_ELEMENT = new VertexFormatElement(1, VertexFormatElement.Format.SHORT, VertexFormatElement.Type.UV, 2);
      NORMAL_ELEMENT = new VertexFormatElement(0, VertexFormatElement.Format.BYTE, VertexFormatElement.Type.NORMAL, 3);
      PADDING_ELEMENT = new VertexFormatElement(0, VertexFormatElement.Format.BYTE, VertexFormatElement.Type.PADDING, 1);
      POSITION_COLOR_UV_LMAP = (new VertexFormat()).add(POSITION_ELEMENT).add(COLOR_ELEMENT).add(UV_ELEMENT).add(LMAP_ELEMENT);
      POSITION_COLOR_UV_NORMAL = (new VertexFormat()).add(POSITION_ELEMENT).add(COLOR_ELEMENT).add(UV_ELEMENT).add(NORMAL_ELEMENT).add(PADDING_ELEMENT);
      POSITION_UV_NORMAL_2 = (new VertexFormat()).add(POSITION_ELEMENT).add(UV_ELEMENT).add(NORMAL_ELEMENT).add(PADDING_ELEMENT);
      POSITION_UV_COLOR_LMAP = (new VertexFormat()).add(POSITION_ELEMENT).add(UV_ELEMENT).add(COLOR_ELEMENT).add(LMAP_ELEMENT);
      POSITION = (new VertexFormat()).add(POSITION_ELEMENT);
      POSITION_COLOR = (new VertexFormat()).add(POSITION_ELEMENT).add(COLOR_ELEMENT);
      POSITION_UV = (new VertexFormat()).add(POSITION_ELEMENT).add(UV_ELEMENT);
      POSITION_NORMAL = (new VertexFormat()).add(POSITION_ELEMENT).add(NORMAL_ELEMENT).add(PADDING_ELEMENT);
      POSITION_UV_COLOR = (new VertexFormat()).add(POSITION_ELEMENT).add(UV_ELEMENT).add(COLOR_ELEMENT);
      POSITION_UV_NORMAL = (new VertexFormat()).add(POSITION_ELEMENT).add(UV_ELEMENT).add(NORMAL_ELEMENT).add(PADDING_ELEMENT);
      POSITION_UV_LMAP_COLOR = (new VertexFormat()).add(POSITION_ELEMENT).add(UV_ELEMENT).add(LMAP_ELEMENT).add(COLOR_ELEMENT);
      POSITION_UV_COLOR_NORMAL = (new VertexFormat()).add(POSITION_ELEMENT).add(UV_ELEMENT).add(COLOR_ELEMENT).add(NORMAL_ELEMENT).add(PADDING_ELEMENT);
   }
}
