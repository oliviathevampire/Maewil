package team.abnormals.tut_new;

import com.mojang.blaze3d.Monitor;
import com.mojang.blaze3d.MonitorTracker;
import com.mojang.blaze3d.Window;
import com.mojang.blaze3d.WindowSettings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.abnormals.tut_new.game.TestGame;

@Environment(EnvType.CLIENT)
public final class WindowProvider implements AutoCloseable {
   private final TestGame client;
   private final MonitorTracker monitorTracker;

   public WindowProvider(TestGame minecraftClient_1) {
      this.client = minecraftClient_1;
      this.monitorTracker = new MonitorTracker(Monitor::new);
   }

   public Window createWindow(WindowSettings windowSettings_1, String string_1, String string_2) {
      return new Window(this.client, this.monitorTracker, windowSettings_1, string_1, string_2);
   }

   public void close() {
      this.monitorTracker.stop();
   }
}
