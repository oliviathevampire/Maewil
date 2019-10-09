package team.abnormals.tut_new;

import com.mojang.bridge.game.GameVersion;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class SharedConstants {
   public static final Level RESOURCE_LEAK_DETECTOR_DISABLED;
   public static boolean isDevelopment;
   public static final char[] INVALID_CHARS_LEVEL_NAME;
   private static GameVersion gameVersion;

   public static boolean isValidChar(char char_1) {
      return char_1 != 167 && char_1 >= ' ' && char_1 != 127;
   }

   public static String stripInvalidChars(String string_1) {
      StringBuilder stringBuilder_1 = new StringBuilder();
      char[] var2 = string_1.toCharArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char char_1 = var2[var4];
         if (isValidChar(char_1)) {
            stringBuilder_1.append(char_1);
         }
      }

      return stringBuilder_1.toString();
   }

   @Environment(EnvType.CLIENT)
   public static String stripSupplementaryChars(String string_1) {
      StringBuilder stringBuilder_1 = new StringBuilder();

      for(int int_1 = 0; int_1 < string_1.length(); int_1 = string_1.offsetByCodePoints(int_1, 1)) {
         int int_2 = string_1.codePointAt(int_1);
         if (!Character.isSupplementaryCodePoint(int_2)) {
            stringBuilder_1.appendCodePoint(int_2);
         } else {
            stringBuilder_1.append('�');
         }
      }

      return stringBuilder_1.toString();
   }

   public static GameVersion getGameVersion() {
      if (gameVersion == null) {
         gameVersion = MinecraftVersion.create();
      }

      return gameVersion;
   }

   static {
      RESOURCE_LEAK_DETECTOR_DISABLED = Level.DISABLED;
      INVALID_CHARS_LEVEL_NAME = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};
      ResourceLeakDetector.setLevel(RESOURCE_LEAK_DETECTOR_DISABLED);
      CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = false;
   }
}
