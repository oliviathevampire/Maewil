/*
package team.abnormals.tut_new.engine;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.GlfwUtil;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.SmoothUtil;
import net.minecraft.util.math.MathHelper;
import team.abnormals.tut_new.game.TestGame;

@Environment(EnvType.CLIENT)
public class Mouse {
   private final TestGame client;
   private boolean leftButtonClicked;
   private boolean middleButtonClicked;
   private boolean rightButtonClicked;
   private double x;
   private double y;
   private int controlLeftTicks;
   private int activeButton = -1;
   private boolean hasResolutionChanged = true;
   private int field_1796;
   private double glfwTime;
   private final SmoothUtil cursorXSmoother = new SmoothUtil();
   private final SmoothUtil cursorYSmoother = new SmoothUtil();
   private double cursorDeltaX;
   private double cursorDeltaY;
   private double eventDeltaWheel;
   private double field_1785 = Double.MIN_VALUE;
   private boolean isCursorLocked;

   public Mouse(TestGame minecraftClient_1) {
      this.client = minecraftClient_1;
   }

   private void onMouseButton(long long_1, int int_1, int int_2, int int_3) {
      if (long_1 == this.client.window.getHandle()) {
         boolean boolean_1 = int_2 == 1;
         if (TestGame.IS_SYSTEM_MAC && int_1 == 0) {
            if (boolean_1) {
               if ((int_3 & 2) == 2) {
                  int_1 = 1;
                  ++this.controlLeftTicks;
               }
            } else if (this.controlLeftTicks > 0) {
               int_1 = 1;
               --this.controlLeftTicks;
            }
         }

         if (boolean_1) {
            this.activeButton = int_1;
            this.glfwTime = GlfwUtil.getTime();
         } else if (this.activeButton != -1) {
            this.activeButton = -1;
         }

         boolean[] booleans_1 = new boolean[]{false};
         if (this.client.overlay == null) {
            if (this.client.currentScreen == null) {
               if (!this.isCursorLocked && boolean_1) {
                  this.lockCursor();
               }
            } else {
               double double_1 = this.x * (double)this.client.window.getScaledWidth() / (double)this.client.window.getWidth();
               double double_2 = this.y * (double)this.client.window.getScaledHeight() / (double)this.client.window.getHeight();
               if (boolean_1) {
                  Screen.wrapScreenError(() -> {
                     booleans_1[0] = this.client.currentScreen.mouseClicked(double_1, double_2, int_1);
                  }, "mouseClicked event handler", this.client.currentScreen.getClass().getCanonicalName());
               } else {
                  Screen.wrapScreenError(() -> {
                     booleans_1[0] = this.client.currentScreen.mouseReleased(double_1, double_2, int_1);
                  }, "mouseReleased event handler", this.client.currentScreen.getClass().getCanonicalName());
               }
            }
         }

         if (!booleans_1[0] && (this.client.currentScreen == null || this.client.currentScreen.passEvents) && this.client.overlay == null) {
            if (int_1 == 0) {
               this.leftButtonClicked = boolean_1;
            } else if (int_1 == 2) {
               this.middleButtonClicked = boolean_1;
            } else if (int_1 == 1) {
               this.rightButtonClicked = boolean_1;
            }

            KeyBinding.setKeyPressed(InputUtil.Type.MOUSE.createFromCode(int_1), boolean_1);
            if (boolean_1) {
               if (this.client.player.isSpectator() && int_1 == 2) {
                  this.client.inGameHud.getSpectatorWidget().method_1983();
               } else {
                  KeyBinding.onKeyPressed(InputUtil.Type.MOUSE.createFromCode(int_1));
               }
            }
         }

      }
   }

   private void onMouseScroll(long long_1, double double_1, double double_2) {
      if (long_1 == MinecraftClient.getInstance().window.getHandle()) {
         double double_3 = (this.client.options.discreteMouseScroll ? Math.signum(double_2) : double_2) * this.client.options.mouseWheelSensitivity;
         if (this.client.overlay == null) {
            if (this.client.currentScreen != null) {
               double double_4 = this.x * (double)this.client.window.getScaledWidth() / (double)this.client.window.getWidth();
               double double_5 = this.y * (double)this.client.window.getScaledHeight() / (double)this.client.window.getHeight();
               this.client.currentScreen.mouseScrolled(double_4, double_5, double_3);
            } else if (this.client.player != null) {
               if (this.eventDeltaWheel != 0.0D && Math.signum(double_3) != Math.signum(this.eventDeltaWheel)) {
                  this.eventDeltaWheel = 0.0D;
               }

               this.eventDeltaWheel += double_3;
               float float_1 = (float)((int)this.eventDeltaWheel);
               if (float_1 == 0.0F) {
                  return;
               }

               this.eventDeltaWheel -= (double)float_1;
               if (this.client.player.isSpectator()) {
                  if (this.client.inGameHud.getSpectatorWidget().method_1980()) {
                     this.client.inGameHud.getSpectatorWidget().method_1976((double)(-float_1));
                  } else {
                     float float_2 = MathHelper.clamp(this.client.player.abilities.getFlySpeed() + float_1 * 0.005F, 0.0F, 0.2F);
                     this.client.player.abilities.setFlySpeed(float_2);
                  }
               } else {
                  this.client.player.inventory.scrollInHotbar((double)float_1);
               }
            }
         }
      }

   }

   public void setup(long long_1) {
      InputUtil.setMouseCallbacks(long_1, this::onCursorPos, this::onMouseButton, this::onMouseScroll);
   }

   private void onCursorPos(long long_1, double double_1, double double_2) {
      if (long_1 == MinecraftClient.getInstance().window.getHandle()) {
         if (this.hasResolutionChanged) {
            this.x = double_1;
            this.y = double_2;
            this.hasResolutionChanged = false;
         }

         Element element_1 = this.client.currentScreen;
         if (element_1 != null && this.client.overlay == null) {
            double double_3 = double_1 * (double)this.client.window.getScaledWidth() / (double)this.client.window.getWidth();
            double double_4 = double_2 * (double)this.client.window.getScaledHeight() / (double)this.client.window.getHeight();
            Screen.wrapScreenError(() -> {
               element_1.mouseMoved(double_3, double_4);
            }, "mouseMoved event handler", element_1.getClass().getCanonicalName());
            if (this.activeButton != -1 && this.glfwTime > 0.0D) {
               double double_5 = (double_1 - this.x) * (double)this.client.window.getScaledWidth() / (double)this.client.window.getWidth();
               double double_6 = (double_2 - this.y) * (double)this.client.window.getScaledHeight() / (double)this.client.window.getHeight();
               Screen.wrapScreenError(() -> {
                  element_1.mouseDragged(double_3, double_4, this.activeButton, double_5, double_6);
               }, "mouseDragged event handler", element_1.getClass().getCanonicalName());
            }
         }

         this.client.getProfiler().push("mouse");
         if (this.isCursorLocked() && this.client.isWindowFocused()) {
            this.cursorDeltaX += double_1 - this.x;
            this.cursorDeltaY += double_2 - this.y;
         }

         this.updateMouse();
         this.x = double_1;
         this.y = double_2;
         this.client.getProfiler().pop();
      }
   }

   public void updateMouse() {
      double double_1 = GlfwUtil.getTime();
      double double_2 = double_1 - this.field_1785;
      this.field_1785 = double_1;
      if (this.isCursorLocked() && this.client.isWindowFocused()) {
         double double_3 = this.client.options.mouseSensitivity * 0.6000000238418579D + 0.20000000298023224D;
         double double_4 = double_3 * double_3 * double_3 * 8.0D;
         double double_9;
         double double_10;
         if (this.client.options.smoothCameraEnabled) {
            double double_5 = this.cursorXSmoother.smooth(this.cursorDeltaX * double_4, double_2 * double_4);
            double double_6 = this.cursorYSmoother.smooth(this.cursorDeltaY * double_4, double_2 * double_4);
            double_9 = double_5;
            double_10 = double_6;
         } else {
            this.cursorXSmoother.clear();
            this.cursorYSmoother.clear();
            double_9 = this.cursorDeltaX * double_4;
            double_10 = this.cursorDeltaY * double_4;
         }

         this.cursorDeltaX = 0.0D;
         this.cursorDeltaY = 0.0D;
         int int_1 = 1;
         if (this.client.options.invertYMouse) {
            int_1 = -1;
         }

         this.client.getTutorialManager().onUpdateMouse(double_9, double_10);
         if (this.client.player != null) {
            this.client.player.changeLookDirection(double_9, double_10 * (double)int_1);
         }

      } else {
         this.cursorDeltaX = 0.0D;
         this.cursorDeltaY = 0.0D;
      }
   }

   public boolean wasLeftButtonClicked() {
      return this.leftButtonClicked;
   }

   public boolean wasRightButtonClicked() {
      return this.rightButtonClicked;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public void onResolutionChanged() {
      this.hasResolutionChanged = true;
   }

   public boolean isCursorLocked() {
      return this.isCursorLocked;
   }

   public void lockCursor() {
      if (this.client.isWindowFocused()) {
         if (!this.isCursorLocked) {
            if (!MinecraftClient.IS_SYSTEM_MAC) {
               KeyBinding.updatePressedStates();
            }

            this.isCursorLocked = true;
            this.x = (double)(this.client.window.getWidth() / 2);
            this.y = (double)(this.client.window.getHeight() / 2);
            InputUtil.setCursorParameters(this.client.window.getHandle(), 212995, this.x, this.y);
            this.client.openScreen((Screen)null);
            this.client.attackCooldown = 10000;
            this.hasResolutionChanged = true;
         }
      }
   }

   public void unlockCursor() {
      if (this.isCursorLocked) {
         this.isCursorLocked = false;
         this.x = (double)(this.client.window.getWidth() / 2);
         this.y = (double)(this.client.window.getHeight() / 2);
         InputUtil.setCursorParameters(this.client.window.getHandle(), 212993, this.x, this.y);
      }
   }
}
*/
