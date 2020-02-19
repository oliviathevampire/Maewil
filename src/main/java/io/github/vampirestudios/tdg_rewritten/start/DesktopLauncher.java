package io.github.vampirestudios.tdg_rewritten.start;

import coffeecatteam.coffeecatutils.DevEnvUtils;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
   public static void main (String[] arg) {
      String[] icons;
      /* Set natives path */
      if (!DevEnvUtils.isRunningFromDevEnviroment()) {
         icons = new String[]{"data/icons/32.png", "data/icons/64.png"};
      } else {
         icons = new String[]{"../data/icons/32.png", "../data/icons/64.png"};
      }
      Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
      config.setTitle("Maewil");
      config.setWindowedMode(854, 480);
      config.setWindowIcon(icons);
      config.setResizable(true);
      new Lwjgl3Application(new MaewilLauncher(), config);
   }
}