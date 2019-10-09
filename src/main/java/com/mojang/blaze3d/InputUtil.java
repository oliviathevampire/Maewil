package com.mojang.blaze3d;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.*;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class InputUtil {
   @Nullable
   private static final MethodHandle field_20333;
   private static final int field_20334;
   public static final InputUtil.KeyCode UNKNOWN_KEYCODE;

   public static InputUtil.KeyCode getKeyCode(int int_1, int int_2) {
      return int_1 == -1 ? InputUtil.Type.SCANCODE.createFromCode(int_2) : InputUtil.Type.KEYSYM.createFromCode(int_1);
   }

   public static InputUtil.KeyCode fromName(String string_1) {
      if (InputUtil.KeyCode.NAMES.containsKey(string_1)) {
         return (InputUtil.KeyCode)InputUtil.KeyCode.NAMES.get(string_1);
      } else {
         InputUtil.Type[] var1 = InputUtil.Type.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            InputUtil.Type inputUtil$Type_1 = var1[var3];
            if (string_1.startsWith(inputUtil$Type_1.name)) {
               String string_2 = string_1.substring(inputUtil$Type_1.name.length() + 1);
               return inputUtil$Type_1.createFromCode(Integer.parseInt(string_2));
            }
         }

         throw new IllegalArgumentException("Unknown key name: " + string_1);
      }
   }

   public static boolean isKeyPressed(long long_1, int int_1) {
      return GLFW.glfwGetKey(long_1, int_1) == 1;
   }

   public static void setKeyboardCallbacks(long long_1, GLFWKeyCallbackI gLFWKeyCallbackI_1, GLFWCharModsCallbackI gLFWCharModsCallbackI_1) {
      GLFW.glfwSetKeyCallback(long_1, gLFWKeyCallbackI_1);
      GLFW.glfwSetCharModsCallback(long_1, gLFWCharModsCallbackI_1);
   }

   public static void setMouseCallbacks(long long_1, GLFWCursorPosCallbackI gLFWCursorPosCallbackI_1, GLFWMouseButtonCallbackI gLFWMouseButtonCallbackI_1, GLFWScrollCallbackI gLFWScrollCallbackI_1) {
      GLFW.glfwSetCursorPosCallback(long_1, gLFWCursorPosCallbackI_1);
      GLFW.glfwSetMouseButtonCallback(long_1, gLFWMouseButtonCallbackI_1);
      GLFW.glfwSetScrollCallback(long_1, gLFWScrollCallbackI_1);
   }

   public static void setCursorParameters(long long_1, int int_1, double double_1, double double_2) {
      GLFW.glfwSetCursorPos(long_1, double_1, double_2);
      GLFW.glfwSetInputMode(long_1, 208897, int_1);
   }

   public static boolean method_21735() {
      try {
//         return field_20333 != null && Objects.requireNonNull(field_20333).invokeExact();
         return false;
      } catch (Throwable var1) {
         throw new RuntimeException(var1);
      }
   }

   public static void method_21736(long long_1, boolean boolean_1) {
      if (method_21735()) {
         GLFW.glfwSetInputMode(long_1, field_20334, boolean_1 ? 1 : 0);
      }

   }

   @Nullable
   public static String getKeycodeName(int int_1) {
      return GLFW.glfwGetKeyName(int_1, -1);
   }

   @Nullable
   public static String getScancodeName(int int_1) {
      return GLFW.glfwGetKeyName(-1, int_1);
   }

   static {
      Lookup methodHandles$Lookup_1 = MethodHandles.lookup();
      MethodType methodType_1 = MethodType.methodType(Boolean.TYPE);
      MethodHandle methodHandle_1 = null;
      int int_1 = 0;

      try {
         methodHandle_1 = methodHandles$Lookup_1.findStatic(GLFW.class, "glfwRawMouseMotionSupported", methodType_1);
         MethodHandle methodHandle_2 = methodHandles$Lookup_1.findStaticGetter(GLFW.class, "GLFW_RAW_MOUSE_MOTION", Integer.TYPE);
         int_1 = (int) methodHandle_2.invokeExact();
      } catch (NoSuchFieldException | NoSuchMethodException var5) {
      } catch (Throwable var6) {
         throw new RuntimeException(var6);
      }

      field_20333 = methodHandle_1;
      field_20334 = int_1;
      UNKNOWN_KEYCODE = InputUtil.Type.KEYSYM.createFromCode(-1);
   }

   @Environment(EnvType.CLIENT)
   public static final class KeyCode {
      private final String name;
      private final InputUtil.Type type;
      private final int keyCode;
      private static final Map<String, InputUtil.KeyCode> NAMES = Maps.newHashMap();

      private KeyCode(String string_1, InputUtil.Type inputUtil$Type_1, int int_1) {
         this.name = string_1;
         this.type = inputUtil$Type_1;
         this.keyCode = int_1;
         NAMES.put(string_1, this);
      }

      public InputUtil.Type getCategory() {
         return this.type;
      }

      public int getKeyCode() {
         return this.keyCode;
      }

      public String getName() {
         return this.name;
      }

      public boolean equals(Object object_1) {
         if (this == object_1) {
            return true;
         } else if (object_1 != null && this.getClass() == object_1.getClass()) {
            InputUtil.KeyCode inputUtil$KeyCode_1 = (InputUtil.KeyCode)object_1;
            return this.keyCode == inputUtil$KeyCode_1.keyCode && this.type == inputUtil$KeyCode_1.type;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.type, this.keyCode});
      }

      public String toString() {
         return this.name;
      }

      // $FF: synthetic method
      KeyCode(String string_1, InputUtil.Type inputUtil$Type_1, int int_1, Object inputUtil$1_1) {
         this(string_1, inputUtil$Type_1, int_1);
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum Type {
      KEYSYM("key.keyboard"),
      SCANCODE("scancode"),
      MOUSE("key.mouse");

      private static final String[] mouseButtons;
      private final Int2ObjectMap<InputUtil.KeyCode> map = new Int2ObjectOpenHashMap();
      private final String name;

      private static void mapKey(InputUtil.Type inputUtil$Type_1, String string_1, int int_1) {
         InputUtil.KeyCode inputUtil$KeyCode_1 = new InputUtil.KeyCode(string_1, inputUtil$Type_1, int_1);
         inputUtil$Type_1.map.put(int_1, inputUtil$KeyCode_1);
      }

      private Type(String string_1) {
         this.name = string_1;
      }

      public InputUtil.KeyCode createFromCode(int int_1) {
         if (this.map.containsKey(int_1)) {
            return (InputUtil.KeyCode)this.map.get(int_1);
         } else {
            String string_3;
            if (this == MOUSE) {
               if (int_1 <= 2) {
                  string_3 = "." + mouseButtons[int_1];
               } else {
                  string_3 = "." + (int_1 + 1);
               }
            } else {
               string_3 = "." + int_1;
            }

            InputUtil.KeyCode inputUtil$KeyCode_1 = new InputUtil.KeyCode(this.name + string_3, this, int_1);
            this.map.put(int_1, inputUtil$KeyCode_1);
            return inputUtil$KeyCode_1;
         }
      }

      public String getName() {
         return this.name;
      }

      static {
         mapKey(KEYSYM, "key.keyboard.unknown", -1);
         mapKey(MOUSE, "key.mouse.left", 0);
         mapKey(MOUSE, "key.mouse.right", 1);
         mapKey(MOUSE, "key.mouse.middle", 2);
         mapKey(MOUSE, "key.mouse.4", 3);
         mapKey(MOUSE, "key.mouse.5", 4);
         mapKey(MOUSE, "key.mouse.6", 5);
         mapKey(MOUSE, "key.mouse.7", 6);
         mapKey(MOUSE, "key.mouse.8", 7);
         mapKey(KEYSYM, "key.keyboard.0", 48);
         mapKey(KEYSYM, "key.keyboard.1", 49);
         mapKey(KEYSYM, "key.keyboard.2", 50);
         mapKey(KEYSYM, "key.keyboard.3", 51);
         mapKey(KEYSYM, "key.keyboard.4", 52);
         mapKey(KEYSYM, "key.keyboard.5", 53);
         mapKey(KEYSYM, "key.keyboard.6", 54);
         mapKey(KEYSYM, "key.keyboard.7", 55);
         mapKey(KEYSYM, "key.keyboard.8", 56);
         mapKey(KEYSYM, "key.keyboard.9", 57);
         mapKey(KEYSYM, "key.keyboard.a", 65);
         mapKey(KEYSYM, "key.keyboard.b", 66);
         mapKey(KEYSYM, "key.keyboard.c", 67);
         mapKey(KEYSYM, "key.keyboard.d", 68);
         mapKey(KEYSYM, "key.keyboard.e", 69);
         mapKey(KEYSYM, "key.keyboard.f", 70);
         mapKey(KEYSYM, "key.keyboard.g", 71);
         mapKey(KEYSYM, "key.keyboard.h", 72);
         mapKey(KEYSYM, "key.keyboard.i", 73);
         mapKey(KEYSYM, "key.keyboard.j", 74);
         mapKey(KEYSYM, "key.keyboard.k", 75);
         mapKey(KEYSYM, "key.keyboard.l", 76);
         mapKey(KEYSYM, "key.keyboard.m", 77);
         mapKey(KEYSYM, "key.keyboard.n", 78);
         mapKey(KEYSYM, "key.keyboard.o", 79);
         mapKey(KEYSYM, "key.keyboard.p", 80);
         mapKey(KEYSYM, "key.keyboard.q", 81);
         mapKey(KEYSYM, "key.keyboard.r", 82);
         mapKey(KEYSYM, "key.keyboard.s", 83);
         mapKey(KEYSYM, "key.keyboard.t", 84);
         mapKey(KEYSYM, "key.keyboard.u", 85);
         mapKey(KEYSYM, "key.keyboard.v", 86);
         mapKey(KEYSYM, "key.keyboard.w", 87);
         mapKey(KEYSYM, "key.keyboard.x", 88);
         mapKey(KEYSYM, "key.keyboard.y", 89);
         mapKey(KEYSYM, "key.keyboard.z", 90);
         mapKey(KEYSYM, "key.keyboard.f1", 290);
         mapKey(KEYSYM, "key.keyboard.f2", 291);
         mapKey(KEYSYM, "key.keyboard.f3", 292);
         mapKey(KEYSYM, "key.keyboard.f4", 293);
         mapKey(KEYSYM, "key.keyboard.f5", 294);
         mapKey(KEYSYM, "key.keyboard.f6", 295);
         mapKey(KEYSYM, "key.keyboard.f7", 296);
         mapKey(KEYSYM, "key.keyboard.f8", 297);
         mapKey(KEYSYM, "key.keyboard.f9", 298);
         mapKey(KEYSYM, "key.keyboard.f10", 299);
         mapKey(KEYSYM, "key.keyboard.f11", 300);
         mapKey(KEYSYM, "key.keyboard.f12", 301);
         mapKey(KEYSYM, "key.keyboard.f13", 302);
         mapKey(KEYSYM, "key.keyboard.f14", 303);
         mapKey(KEYSYM, "key.keyboard.f15", 304);
         mapKey(KEYSYM, "key.keyboard.f16", 305);
         mapKey(KEYSYM, "key.keyboard.f17", 306);
         mapKey(KEYSYM, "key.keyboard.f18", 307);
         mapKey(KEYSYM, "key.keyboard.f19", 308);
         mapKey(KEYSYM, "key.keyboard.f20", 309);
         mapKey(KEYSYM, "key.keyboard.f21", 310);
         mapKey(KEYSYM, "key.keyboard.f22", 311);
         mapKey(KEYSYM, "key.keyboard.f23", 312);
         mapKey(KEYSYM, "key.keyboard.f24", 313);
         mapKey(KEYSYM, "key.keyboard.f25", 314);
         mapKey(KEYSYM, "key.keyboard.num.lock", 282);
         mapKey(KEYSYM, "key.keyboard.keypad.0", 320);
         mapKey(KEYSYM, "key.keyboard.keypad.1", 321);
         mapKey(KEYSYM, "key.keyboard.keypad.2", 322);
         mapKey(KEYSYM, "key.keyboard.keypad.3", 323);
         mapKey(KEYSYM, "key.keyboard.keypad.4", 324);
         mapKey(KEYSYM, "key.keyboard.keypad.5", 325);
         mapKey(KEYSYM, "key.keyboard.keypad.6", 326);
         mapKey(KEYSYM, "key.keyboard.keypad.7", 327);
         mapKey(KEYSYM, "key.keyboard.keypad.8", 328);
         mapKey(KEYSYM, "key.keyboard.keypad.9", 329);
         mapKey(KEYSYM, "key.keyboard.keypad.add", 334);
         mapKey(KEYSYM, "key.keyboard.keypad.decimal", 330);
         mapKey(KEYSYM, "key.keyboard.keypad.enter", 335);
         mapKey(KEYSYM, "key.keyboard.keypad.equal", 336);
         mapKey(KEYSYM, "key.keyboard.keypad.multiply", 332);
         mapKey(KEYSYM, "key.keyboard.keypad.divide", 331);
         mapKey(KEYSYM, "key.keyboard.keypad.subtract", 333);
         mapKey(KEYSYM, "key.keyboard.down", 264);
         mapKey(KEYSYM, "key.keyboard.left", 263);
         mapKey(KEYSYM, "key.keyboard.right", 262);
         mapKey(KEYSYM, "key.keyboard.up", 265);
         mapKey(KEYSYM, "key.keyboard.apostrophe", 39);
         mapKey(KEYSYM, "key.keyboard.backslash", 92);
         mapKey(KEYSYM, "key.keyboard.comma", 44);
         mapKey(KEYSYM, "key.keyboard.equal", 61);
         mapKey(KEYSYM, "key.keyboard.grave.accent", 96);
         mapKey(KEYSYM, "key.keyboard.left.bracket", 91);
         mapKey(KEYSYM, "key.keyboard.minus", 45);
         mapKey(KEYSYM, "key.keyboard.period", 46);
         mapKey(KEYSYM, "key.keyboard.right.bracket", 93);
         mapKey(KEYSYM, "key.keyboard.semicolon", 59);
         mapKey(KEYSYM, "key.keyboard.slash", 47);
         mapKey(KEYSYM, "key.keyboard.space", 32);
         mapKey(KEYSYM, "key.keyboard.tab", 258);
         mapKey(KEYSYM, "key.keyboard.left.alt", 342);
         mapKey(KEYSYM, "key.keyboard.left.control", 341);
         mapKey(KEYSYM, "key.keyboard.left.shift", 340);
         mapKey(KEYSYM, "key.keyboard.left.win", 343);
         mapKey(KEYSYM, "key.keyboard.right.alt", 346);
         mapKey(KEYSYM, "key.keyboard.right.control", 345);
         mapKey(KEYSYM, "key.keyboard.right.shift", 344);
         mapKey(KEYSYM, "key.keyboard.right.win", 347);
         mapKey(KEYSYM, "key.keyboard.enter", 257);
         mapKey(KEYSYM, "key.keyboard.escape", 256);
         mapKey(KEYSYM, "key.keyboard.backspace", 259);
         mapKey(KEYSYM, "key.keyboard.delete", 261);
         mapKey(KEYSYM, "key.keyboard.end", 269);
         mapKey(KEYSYM, "key.keyboard.home", 268);
         mapKey(KEYSYM, "key.keyboard.insert", 260);
         mapKey(KEYSYM, "key.keyboard.page.down", 267);
         mapKey(KEYSYM, "key.keyboard.page.up", 266);
         mapKey(KEYSYM, "key.keyboard.caps.lock", 280);
         mapKey(KEYSYM, "key.keyboard.pause", 284);
         mapKey(KEYSYM, "key.keyboard.scroll.lock", 281);
         mapKey(KEYSYM, "key.keyboard.menu", 348);
         mapKey(KEYSYM, "key.keyboard.print.screen", 283);
         mapKey(KEYSYM, "key.keyboard.world.1", 161);
         mapKey(KEYSYM, "key.keyboard.world.2", 162);
         mouseButtons = new String[]{"left", "middle", "right"};
      }
   }
}
