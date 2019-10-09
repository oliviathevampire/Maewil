package com.mojang.blaze3d;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface WindowEventHandler {
   void onWindowFocusChanged(boolean var1);

   void updateDisplay(boolean var1);

   void onResolutionChanged();
}
