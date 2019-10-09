package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.system.Pointer;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Environment(EnvType.CLIENT)
public class Untracker {
   @Nullable
   private static final MethodHandle ALLOCATOR_UNTRACK = (MethodHandle)GLX.make(() -> {
      try {
         Lookup methodHandles$Lookup_1 = MethodHandles.lookup();
         Class<?> class_1 = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
         Method method_1 = class_1.getDeclaredMethod("untrack", Long.TYPE);
         method_1.setAccessible(true);
         Field field_1 = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
         field_1.setAccessible(true);
         Object object_1 = field_1.get((Object)null);
         return class_1.isInstance(object_1) ? methodHandles$Lookup_1.unreflect(method_1) : null;
      } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | ClassNotFoundException var5) {
         throw new RuntimeException(var5);
      }
   });

   public static void untrack(long long_1) {
      if (ALLOCATOR_UNTRACK != null) {
         try {
            ALLOCATOR_UNTRACK.invoke(long_1);
         } catch (Throwable var3) {
            throw new RuntimeException(var3);
         }
      }
   }

   public static void untrack(Pointer pointer_1) {
      untrack(pointer_1.address());
   }
}
