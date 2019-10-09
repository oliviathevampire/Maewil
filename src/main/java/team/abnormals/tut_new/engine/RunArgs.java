package team.abnormals.tut_new.engine;

import com.mojang.blaze3d.WindowSettings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.annotation.Nullable;
import java.io.File;

@Environment(EnvType.CLIENT)
public class RunArgs {
   public final WindowSettings windowSettings;
   public final RunArgs.Directories directories;
   public final RunArgs.Game game;
   public final RunArgs.AutoConnect autoConnect;

   public RunArgs(WindowSettings windowSettings_1, RunArgs.Directories runArgs$Directories_1, RunArgs.Game runArgs$Game_1, RunArgs.AutoConnect runArgs$AutoConnect_1) {
      this.windowSettings = windowSettings_1;
      this.directories = runArgs$Directories_1;
      this.game = runArgs$Game_1;
      this.autoConnect = runArgs$AutoConnect_1;
   }

   @Environment(EnvType.CLIENT)
   public static class AutoConnect {
      public final String serverIP;
      public final int serverPort;

      public AutoConnect(String string_1, int int_1) {
         this.serverIP = string_1;
         this.serverPort = int_1;
      }
   }

   @Environment(EnvType.CLIENT)
   public static class Directories {
      public final File runDir;
      public final File resourcePackDir;
      public final File assetDir;
      public final String assetIndex;

      public Directories(File file_1, File file_2, File file_3, @Nullable String string_1) {
         this.runDir = file_1;
         this.resourcePackDir = file_2;
         this.assetDir = file_3;
         this.assetIndex = string_1;
      }

      /*public ResourceIndex getResourceIndex() {
         return (ResourceIndex)(this.assetIndex == null ? new DirectResourceIndex(this.assetDir) : new ResourceIndex(this.assetDir, this.assetIndex));
      }*/
   }

   @Environment(EnvType.CLIENT)
   public static class Game {
      public final boolean demo;
      public final String version;
      public final String versionType;

      public Game(boolean boolean_1, String string_1, String string_2) {
         this.demo = boolean_1;
         this.version = string_1;
         this.versionType = string_2;
      }
   }
}
