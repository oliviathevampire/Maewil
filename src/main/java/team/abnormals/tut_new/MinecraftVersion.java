package team.abnormals.tut_new;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.bridge.game.GameVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.abnormals.tut_new.engine.JsonHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

public class MinecraftVersion implements GameVersion {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String id;
   private final String name;
   private final boolean stable;
   private final int worldVersion;
   private final int protocolVersion;
   private final int packVersion;
   private final Date buildTime;
   private final String releaseTarget;

   public MinecraftVersion() {
      this.id = UUID.randomUUID().toString().replaceAll("-", "");
      this.name = "19w35a";
      this.stable = false;
      this.worldVersion = 2201;
      this.protocolVersion = 551;
      this.packVersion = 4;
      this.buildTime = new Date();
      this.releaseTarget = "1.15";
   }

   protected MinecraftVersion(JsonObject jsonObject_1) {
      this.id = JsonHelper.getString(jsonObject_1, "id");
      this.name = JsonHelper.getString(jsonObject_1, "name");
      this.releaseTarget = JsonHelper.getString(jsonObject_1, "release_target");
      this.stable = JsonHelper.getBoolean(jsonObject_1, "stable");
      this.worldVersion = JsonHelper.getInt(jsonObject_1, "world_version");
      this.protocolVersion = JsonHelper.getInt(jsonObject_1, "protocol_version");
      this.packVersion = JsonHelper.getInt(jsonObject_1, "pack_version");
      this.buildTime = Date.from(ZonedDateTime.parse(JsonHelper.getString(jsonObject_1, "build_time")).toInstant());
   }

   public static GameVersion create() {
      try {
         InputStream inputStream_1 = MinecraftVersion.class.getResourceAsStream("/version.json");
         Throwable var1 = null;

         MinecraftVersion var2;
         try {
            if (inputStream_1 != null) {
               InputStreamReader inputStreamReader_1 = new InputStreamReader(inputStream_1);
               Throwable var3 = null;

               try {
                  Object var4;
                  try {
                     var4 = new MinecraftVersion(JsonHelper.deserialize((Reader)inputStreamReader_1));
                     return (GameVersion)var4;
                  } catch (Throwable var30) {
                     var4 = var30;
                     var3 = var30;
                     throw var30;
                  }
               } finally {
                  if (inputStreamReader_1 != null) {
                     if (var3 != null) {
                        try {
                           inputStreamReader_1.close();
                        } catch (Throwable var29) {
                           var3.addSuppressed(var29);
                        }
                     } else {
                        inputStreamReader_1.close();
                     }
                  }

               }
            }

            LOGGER.warn("Missing version information!");
            var2 = new MinecraftVersion();
         } catch (Throwable var32) {
            var1 = var32;
            throw var32;
         } finally {
            if (inputStream_1 != null) {
               if (var1 != null) {
                  try {
                     inputStream_1.close();
                  } catch (Throwable var28) {
                     var1.addSuppressed(var28);
                  }
               } else {
                  inputStream_1.close();
               }
            }

         }

         return var2;
      } catch (JsonParseException | IOException var34) {
         throw new IllegalStateException("Game version information is corrupt", var34);
      }
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getReleaseTarget() {
      return this.releaseTarget;
   }

   public int getWorldVersion() {
      return this.worldVersion;
   }

   public int getProtocolVersion() {
      return this.protocolVersion;
   }

   public int getPackVersion() {
      return this.packVersion;
   }

   public Date getBuildTime() {
      return this.buildTime;
   }

   public boolean isStable() {
      return this.stable;
   }
}
