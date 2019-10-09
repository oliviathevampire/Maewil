package team.abnormals.tut_new.engine;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.resource.ClientResourcePackContainer;
import net.minecraft.client.tutorial.TutorialStep;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.VideoMode;
import net.minecraft.datafixers.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resource.ResourcePackContainerManager;
import net.minecraft.server.network.packet.ClientSettingsC2SPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Arm;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.TagHelper;
import net.minecraft.world.Difficulty;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class GameOptions {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new Gson();
   private static final Type STRING_LIST_TYPE = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{String.class};
      }

      public Type getRawType() {
         return List.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };
   public static final Splitter COLON_SPLITTER = Splitter.on(':');
   public double mouseSensitivity = 0.5D;
   public int viewDistance = -1;
   public int maxFps = 120;
   public CloudRenderMode cloudRenderMode;
   public boolean fancyGraphics;
   public AoOption ao;
   public List<String> resourcePacks;
   public List<String> incompatibleResourcePacks;
   public ChatVisibility chatVisibility;
   public double chatOpacity;
   public double textBackgroundOpacity;
   @Nullable
   public String fullscreenResolution;
   public boolean hideServerAddress;
   public boolean advancedItemTooltips;
   public boolean pauseOnLostFocus;
   private final Set<PlayerModelPart> enabledPlayerModelParts;
   public Arm mainArm;
   public int overrideWidth;
   public int overrideHeight;
   public boolean heldItemTooltips;
   public double chatScale;
   public double chatWidth;
   public double chatHeightUnfocused;
   public double chatHeightFocused;
   public int mipmapLevels;
   private final Map<SoundCategory, Float> soundVolumeLevels;
   public boolean useNativeTransport;
   public AttackIndicator attackIndicator;
   public TutorialStep tutorialStep;
   public int biomeBlendRadius;
   public double mouseWheelSensitivity;
   public boolean field_20308;
   public int glDebugVerbosity;
   public boolean autoJump;
   public boolean autoSuggestions;
   public boolean chatColors;
   public boolean chatLinks;
   public boolean chatLinksPrompt;
   public boolean enableVsync;
   public boolean entityShadows;
   public boolean forceUnicodeFont;
   public boolean invertYMouse;
   public boolean discreteMouseScroll;
   public boolean realmsNotifications;
   public boolean reducedDebugInfo;
   public boolean snooperEnabled;
   public boolean showSubtitles;
   public boolean backgroundForChatOnly;
   public boolean touchscreen;
   public boolean fullscreen;
   public boolean bobView;
   public final KeyBinding keyForward;
   public final KeyBinding keyLeft;
   public final KeyBinding keyBack;
   public final KeyBinding keyRight;
   public final KeyBinding keyJump;
   public final KeyBinding keySneak;
   public final KeyBinding keySprint;
   public final KeyBinding keyInventory;
   public final KeyBinding keySwapHands;
   public final KeyBinding keyDrop;
   public final KeyBinding keyUse;
   public final KeyBinding keyAttack;
   public final KeyBinding keyPickItem;
   public final KeyBinding keyChat;
   public final KeyBinding keyPlayerList;
   public final KeyBinding keyCommand;
   public final KeyBinding keyScreenshot;
   public final KeyBinding keyTogglePerspective;
   public final KeyBinding keySmoothCamera;
   public final KeyBinding keyFullscreen;
   public final KeyBinding keySpectatorOutlines;
   public final KeyBinding keyAdvancements;
   public final KeyBinding[] keysHotbar;
   public final KeyBinding keySaveToolbarActivator;
   public final KeyBinding keyLoadToolbarActivator;
   public final KeyBinding[] keysAll;
   protected MinecraftClient client;
   private final File optionsFile;
   public Difficulty difficulty;
   public boolean hudHidden;
   public int perspective;
   public boolean debugEnabled;
   public boolean debugProfilerEnabled;
   public boolean debugTpsEnabled;
   public String lastServer;
   public boolean smoothCameraEnabled;
   public double fov;
   public double gamma;
   public int guiScale;
   public ParticlesOption particles;
   public NarratorOption narrator;
   public String language;

   public GameOptions(MinecraftClient minecraftClient_1, File file_1) {
      this.cloudRenderMode = CloudRenderMode.FANCY;
      this.fancyGraphics = true;
      this.ao = AoOption.MAX;
      this.resourcePacks = Lists.newArrayList();
      this.incompatibleResourcePacks = Lists.newArrayList();
      this.chatVisibility = ChatVisibility.FULL;
      this.chatOpacity = 1.0D;
      this.textBackgroundOpacity = 0.5D;
      this.pauseOnLostFocus = true;
      this.enabledPlayerModelParts = Sets.newHashSet(PlayerModelPart.values());
      this.mainArm = Arm.RIGHT;
      this.heldItemTooltips = true;
      this.chatScale = 1.0D;
      this.chatWidth = 1.0D;
      this.chatHeightUnfocused = 0.44366195797920227D;
      this.chatHeightFocused = 1.0D;
      this.mipmapLevels = 4;
      this.soundVolumeLevels = Maps.newEnumMap(SoundCategory.class);
      this.useNativeTransport = true;
      this.attackIndicator = AttackIndicator.CROSSHAIR;
      this.tutorialStep = TutorialStep.MOVEMENT;
      this.biomeBlendRadius = 2;
      this.mouseWheelSensitivity = 1.0D;
      this.field_20308 = true;
      this.glDebugVerbosity = 1;
      this.autoJump = true;
      this.autoSuggestions = true;
      this.chatColors = true;
      this.chatLinks = true;
      this.chatLinksPrompt = true;
      this.enableVsync = true;
      this.entityShadows = true;
      this.realmsNotifications = true;
      this.snooperEnabled = true;
      this.backgroundForChatOnly = true;
      this.bobView = true;
      this.keyForward = new KeyBinding("key.forward", 87, "key.categories.movement");
      this.keyLeft = new KeyBinding("key.left", 65, "key.categories.movement");
      this.keyBack = new KeyBinding("key.back", 83, "key.categories.movement");
      this.keyRight = new KeyBinding("key.right", 68, "key.categories.movement");
      this.keyJump = new KeyBinding("key.jump", 32, "key.categories.movement");
      this.keySneak = new KeyBinding("key.sneak", 340, "key.categories.movement");
      this.keySprint = new KeyBinding("key.sprint", 341, "key.categories.movement");
      this.keyInventory = new KeyBinding("key.inventory", 69, "key.categories.inventory");
      this.keySwapHands = new KeyBinding("key.swapHands", 70, "key.categories.inventory");
      this.keyDrop = new KeyBinding("key.drop", 81, "key.categories.inventory");
      this.keyUse = new KeyBinding("key.use", InputUtil.Type.MOUSE, 1, "key.categories.gameplay");
      this.keyAttack = new KeyBinding("key.attack", InputUtil.Type.MOUSE, 0, "key.categories.gameplay");
      this.keyPickItem = new KeyBinding("key.pickItem", InputUtil.Type.MOUSE, 2, "key.categories.gameplay");
      this.keyChat = new KeyBinding("key.chat", 84, "key.categories.multiplayer");
      this.keyPlayerList = new KeyBinding("key.playerlist", 258, "key.categories.multiplayer");
      this.keyCommand = new KeyBinding("key.command", 47, "key.categories.multiplayer");
      this.keyScreenshot = new KeyBinding("key.screenshot", 291, "key.categories.misc");
      this.keyTogglePerspective = new KeyBinding("key.togglePerspective", 294, "key.categories.misc");
      this.keySmoothCamera = new KeyBinding("key.smoothCamera", InputUtil.UNKNOWN_KEYCODE.getKeyCode(), "key.categories.misc");
      this.keyFullscreen = new KeyBinding("key.fullscreen", 300, "key.categories.misc");
      this.keySpectatorOutlines = new KeyBinding("key.spectatorOutlines", InputUtil.UNKNOWN_KEYCODE.getKeyCode(), "key.categories.misc");
      this.keyAdvancements = new KeyBinding("key.advancements", 76, "key.categories.misc");
      this.keysHotbar = new KeyBinding[]{new KeyBinding("key.hotbar.1", 49, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 50, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 51, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 52, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 53, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 54, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 55, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 56, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 57, "key.categories.inventory")};
      this.keySaveToolbarActivator = new KeyBinding("key.saveToolbarActivator", 67, "key.categories.creative");
      this.keyLoadToolbarActivator = new KeyBinding("key.loadToolbarActivator", 88, "key.categories.creative");
      this.keysAll = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[]{this.keyAttack, this.keyUse, this.keyForward, this.keyLeft, this.keyBack, this.keyRight, this.keyJump, this.keySneak, this.keySprint, this.keyDrop, this.keyInventory, this.keyChat, this.keyPlayerList, this.keyPickItem, this.keyCommand, this.keyScreenshot, this.keyTogglePerspective, this.keySmoothCamera, this.keyFullscreen, this.keySpectatorOutlines, this.keySwapHands, this.keySaveToolbarActivator, this.keyLoadToolbarActivator, this.keyAdvancements}, this.keysHotbar);
      this.difficulty = Difficulty.NORMAL;
      this.lastServer = "";
      this.fov = 70.0D;
      this.particles = ParticlesOption.ALL;
      this.narrator = NarratorOption.OFF;
      this.language = "en_us";
      this.client = minecraftClient_1;
      this.optionsFile = new File(file_1, "options.txt");
      if (minecraftClient_1.is64Bit() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
         Option.RENDER_DISTANCE.setMax(32.0F);
      } else {
         Option.RENDER_DISTANCE.setMax(16.0F);
      }

      this.viewDistance = minecraftClient_1.is64Bit() ? 12 : 8;
      this.load();
   }

   public float getTextBackgroundOpacity(float float_1) {
      return this.backgroundForChatOnly ? float_1 : (float)this.textBackgroundOpacity;
   }

   public int getTextBackgroundColor(float float_1) {
      return (int)(this.getTextBackgroundOpacity(float_1) * 255.0F) << 24 & -16777216;
   }

   public int getTextBackgroundColor(int int_1) {
      return this.backgroundForChatOnly ? int_1 : (int)(this.textBackgroundOpacity * 255.0D) << 24 & -16777216;
   }

   public void setKeyCode(KeyBinding keyBinding_1, InputUtil.KeyCode inputUtil$KeyCode_1) {
      keyBinding_1.setKeyCode(inputUtil$KeyCode_1);
      this.write();
   }

   public void load() {
      try {
         if (!this.optionsFile.exists()) {
            return;
         }

         this.soundVolumeLevels.clear();
         List<String> list_1 = IOUtils.readLines(new FileInputStream(this.optionsFile));
         CompoundTag compoundTag_1 = new CompoundTag();
         Iterator var3 = list_1.iterator();

         String string_2;
         while(var3.hasNext()) {
            string_2 = (String)var3.next();

            try {
               Iterator<String> iterator_1 = COLON_SPLITTER.omitEmptyStrings().limit(2).split(string_2).iterator();
               compoundTag_1.putString((String)iterator_1.next(), (String)iterator_1.next());
            } catch (Exception var10) {
               LOGGER.warn("Skipping bad option: {}", string_2);
            }
         }

         compoundTag_1 = this.method_1626(compoundTag_1);
         var3 = compoundTag_1.getKeys().iterator();

         while(var3.hasNext()) {
            string_2 = (String)var3.next();
            String string_3 = compoundTag_1.getString(string_2);

            try {
               if ("autoJump".equals(string_2)) {
                  Option.AUTO_JUMP.set(this, string_3);
               }

               if ("autoSuggestions".equals(string_2)) {
                  Option.AUTO_SUGGESTIONS.set(this, string_3);
               }

               if ("chatColors".equals(string_2)) {
                  Option.CHAT_COLOR.set(this, string_3);
               }

               if ("chatLinks".equals(string_2)) {
                  Option.CHAT_LINKS.set(this, string_3);
               }

               if ("chatLinksPrompt".equals(string_2)) {
                  Option.CHAT_LINKS_PROMPT.set(this, string_3);
               }

               if ("enableVsync".equals(string_2)) {
                  Option.VSYNC.set(this, string_3);
               }

               if ("entityShadows".equals(string_2)) {
                  Option.ENTITY_SHADOWS.set(this, string_3);
               }

               if ("forceUnicodeFont".equals(string_2)) {
                  Option.FORCE_UNICODE_FONT.set(this, string_3);
               }

               if ("discrete_mouse_scroll".equals(string_2)) {
                  Option.DISCRETE_MOUSE_SCROLL.set(this, string_3);
               }

               if ("invertYMouse".equals(string_2)) {
                  Option.INVERT_MOUSE.set(this, string_3);
               }

               if ("realmsNotifications".equals(string_2)) {
                  Option.REALMS_NOTIFICATIONS.set(this, string_3);
               }

               if ("reducedDebugInfo".equals(string_2)) {
                  Option.REDUCED_DEBUG_INFO.set(this, string_3);
               }

               if ("showSubtitles".equals(string_2)) {
                  Option.SUBTITLES.set(this, string_3);
               }

               if ("snooperEnabled".equals(string_2)) {
                  Option.SNOOPER.set(this, string_3);
               }

               if ("touchscreen".equals(string_2)) {
                  Option.TOUCHSCREEN.set(this, string_3);
               }

               if ("fullscreen".equals(string_2)) {
                  Option.FULLSCREEN.set(this, string_3);
               }

               if ("bobView".equals(string_2)) {
                  Option.VIEW_BOBBING.set(this, string_3);
               }

               if ("mouseSensitivity".equals(string_2)) {
                  this.mouseSensitivity = (double)parseFloat(string_3);
               }

               if ("fov".equals(string_2)) {
                  this.fov = (double)(parseFloat(string_3) * 40.0F + 70.0F);
               }

               if ("gamma".equals(string_2)) {
                  this.gamma = (double)parseFloat(string_3);
               }

               if ("renderDistance".equals(string_2)) {
                  this.viewDistance = Integer.parseInt(string_3);
               }

               if ("guiScale".equals(string_2)) {
                  this.guiScale = Integer.parseInt(string_3);
               }

               if ("particles".equals(string_2)) {
                  this.particles = ParticlesOption.byId(Integer.parseInt(string_3));
               }

               if ("maxFps".equals(string_2)) {
                  this.maxFps = Integer.parseInt(string_3);
                  if (this.client.window != null) {
                     this.client.window.setFramerateLimit(this.maxFps);
                  }
               }

               if ("difficulty".equals(string_2)) {
                  this.difficulty = Difficulty.byOrdinal(Integer.parseInt(string_3));
               }

               if ("fancyGraphics".equals(string_2)) {
                  this.fancyGraphics = "true".equals(string_3);
               }

               if ("tutorialStep".equals(string_2)) {
                  this.tutorialStep = TutorialStep.byName(string_3);
               }

               if ("ao".equals(string_2)) {
                  if ("true".equals(string_3)) {
                     this.ao = AoOption.MAX;
                  } else if ("false".equals(string_3)) {
                     this.ao = AoOption.OFF;
                  } else {
                     this.ao = AoOption.getOption(Integer.parseInt(string_3));
                  }
               }

               if ("renderClouds".equals(string_2)) {
                  if ("true".equals(string_3)) {
                     this.cloudRenderMode = CloudRenderMode.FANCY;
                  } else if ("false".equals(string_3)) {
                     this.cloudRenderMode = CloudRenderMode.OFF;
                  } else if ("fast".equals(string_3)) {
                     this.cloudRenderMode = CloudRenderMode.FAST;
                  }
               }

               if ("attackIndicator".equals(string_2)) {
                  this.attackIndicator = AttackIndicator.byId(Integer.parseInt(string_3));
               }

               if ("resourcePacks".equals(string_2)) {
                  this.resourcePacks = (List)JsonHelper.deserialize(GSON, string_3, STRING_LIST_TYPE);
                  if (this.resourcePacks == null) {
                     this.resourcePacks = Lists.newArrayList();
                  }
               }

               if ("incompatibleResourcePacks".equals(string_2)) {
                  this.incompatibleResourcePacks = (List)JsonHelper.deserialize(GSON, string_3, STRING_LIST_TYPE);
                  if (this.incompatibleResourcePacks == null) {
                     this.incompatibleResourcePacks = Lists.newArrayList();
                  }
               }

               if ("lastServer".equals(string_2)) {
                  this.lastServer = string_3;
               }

               if ("lang".equals(string_2)) {
                  this.language = string_3;
               }

               if ("chatVisibility".equals(string_2)) {
                  this.chatVisibility = ChatVisibility.byId(Integer.parseInt(string_3));
               }

               if ("chatOpacity".equals(string_2)) {
                  this.chatOpacity = (double)parseFloat(string_3);
               }

               if ("textBackgroundOpacity".equals(string_2)) {
                  this.textBackgroundOpacity = (double)parseFloat(string_3);
               }

               if ("backgroundForChatOnly".equals(string_2)) {
                  this.backgroundForChatOnly = "true".equals(string_3);
               }

               if ("fullscreenResolution".equals(string_2)) {
                  this.fullscreenResolution = string_3;
               }

               if ("hideServerAddress".equals(string_2)) {
                  this.hideServerAddress = "true".equals(string_3);
               }

               if ("advancedItemTooltips".equals(string_2)) {
                  this.advancedItemTooltips = "true".equals(string_3);
               }

               if ("pauseOnLostFocus".equals(string_2)) {
                  this.pauseOnLostFocus = "true".equals(string_3);
               }

               if ("overrideHeight".equals(string_2)) {
                  this.overrideHeight = Integer.parseInt(string_3);
               }

               if ("overrideWidth".equals(string_2)) {
                  this.overrideWidth = Integer.parseInt(string_3);
               }

               if ("heldItemTooltips".equals(string_2)) {
                  this.heldItemTooltips = "true".equals(string_3);
               }

               if ("chatHeightFocused".equals(string_2)) {
                  this.chatHeightFocused = (double)parseFloat(string_3);
               }

               if ("chatHeightUnfocused".equals(string_2)) {
                  this.chatHeightUnfocused = (double)parseFloat(string_3);
               }

               if ("chatScale".equals(string_2)) {
                  this.chatScale = (double)parseFloat(string_3);
               }

               if ("chatWidth".equals(string_2)) {
                  this.chatWidth = (double)parseFloat(string_3);
               }

               if ("mipmapLevels".equals(string_2)) {
                  this.mipmapLevels = Integer.parseInt(string_3);
               }

               if ("useNativeTransport".equals(string_2)) {
                  this.useNativeTransport = "true".equals(string_3);
               }

               if ("mainHand".equals(string_2)) {
                  this.mainArm = "left".equals(string_3) ? Arm.LEFT : Arm.RIGHT;
               }

               if ("narrator".equals(string_2)) {
                  this.narrator = NarratorOption.byId(Integer.parseInt(string_3));
               }

               if ("biomeBlendRadius".equals(string_2)) {
                  this.biomeBlendRadius = Integer.parseInt(string_3);
               }

               if ("mouseWheelSensitivity".equals(string_2)) {
                  this.mouseWheelSensitivity = (double)parseFloat(string_3);
               }

               if ("rawMouseInput".equals(string_2)) {
                  this.field_20308 = "true".equals(string_3);
               }

               if ("glDebugVerbosity".equals(string_2)) {
                  this.glDebugVerbosity = Integer.parseInt(string_3);
               }

               KeyBinding[] var6 = this.keysAll;
               int var7 = var6.length;

               int var8;
               for(var8 = 0; var8 < var7; ++var8) {
                  KeyBinding keyBinding_1 = var6[var8];
                  if (string_2.equals("key_" + keyBinding_1.getId())) {
                     keyBinding_1.setKeyCode(InputUtil.fromName(string_3));
                  }
               }

               SoundCategory[] var14 = SoundCategory.values();
               var7 = var14.length;

               for(var8 = 0; var8 < var7; ++var8) {
                  SoundCategory soundCategory_1 = var14[var8];
                  if (string_2.equals("soundCategory_" + soundCategory_1.getName())) {
                     this.soundVolumeLevels.put(soundCategory_1, parseFloat(string_3));
                  }
               }

               PlayerModelPart[] var15 = PlayerModelPart.values();
               var7 = var15.length;

               for(var8 = 0; var8 < var7; ++var8) {
                  PlayerModelPart playerModelPart_1 = var15[var8];
                  if (string_2.equals("modelPart_" + playerModelPart_1.getName())) {
                     this.setPlayerModelPart(playerModelPart_1, "true".equals(string_3));
                  }
               }
            } catch (Exception var11) {
               LOGGER.warn("Skipping bad option: {}:{}", string_2, string_3);
            }
         }

         KeyBinding.updateKeysByCode();
      } catch (Exception var12) {
         LOGGER.error("Failed to load options", var12);
      }

   }

   private CompoundTag method_1626(CompoundTag compoundTag_1) {
      int int_1 = 0;

      try {
         int_1 = Integer.parseInt(compoundTag_1.getString("version"));
      } catch (RuntimeException var4) {
      }

      return TagHelper.update(this.client.getDataFixer(), DataFixTypes.OPTIONS, compoundTag_1, int_1);
   }

   private static float parseFloat(String string_1) {
      if ("true".equals(string_1)) {
         return 1.0F;
      } else {
         return "false".equals(string_1) ? 0.0F : Float.parseFloat(string_1);
      }
   }

   public void write() {
      try {
         PrintWriter printWriter_1 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFile), StandardCharsets.UTF_8));
         Throwable var2 = null;

         try {
            printWriter_1.println("version:" + SharedConstants.getGameVersion().getWorldVersion());
            printWriter_1.println("autoJump:" + Option.AUTO_JUMP.get(this));
            printWriter_1.println("autoSuggestions:" + Option.AUTO_SUGGESTIONS.get(this));
            printWriter_1.println("chatColors:" + Option.CHAT_COLOR.get(this));
            printWriter_1.println("chatLinks:" + Option.CHAT_LINKS.get(this));
            printWriter_1.println("chatLinksPrompt:" + Option.CHAT_LINKS_PROMPT.get(this));
            printWriter_1.println("enableVsync:" + Option.VSYNC.get(this));
            printWriter_1.println("entityShadows:" + Option.ENTITY_SHADOWS.get(this));
            printWriter_1.println("forceUnicodeFont:" + Option.FORCE_UNICODE_FONT.get(this));
            printWriter_1.println("discrete_mouse_scroll:" + Option.DISCRETE_MOUSE_SCROLL.get(this));
            printWriter_1.println("invertYMouse:" + Option.INVERT_MOUSE.get(this));
            printWriter_1.println("realmsNotifications:" + Option.REALMS_NOTIFICATIONS.get(this));
            printWriter_1.println("reducedDebugInfo:" + Option.REDUCED_DEBUG_INFO.get(this));
            printWriter_1.println("snooperEnabled:" + Option.SNOOPER.get(this));
            printWriter_1.println("showSubtitles:" + Option.SUBTITLES.get(this));
            printWriter_1.println("touchscreen:" + Option.TOUCHSCREEN.get(this));
            printWriter_1.println("fullscreen:" + Option.FULLSCREEN.get(this));
            printWriter_1.println("bobView:" + Option.VIEW_BOBBING.get(this));
            printWriter_1.println("mouseSensitivity:" + this.mouseSensitivity);
            printWriter_1.println("fov:" + (this.fov - 70.0D) / 40.0D);
            printWriter_1.println("gamma:" + this.gamma);
            printWriter_1.println("renderDistance:" + this.viewDistance);
            printWriter_1.println("guiScale:" + this.guiScale);
            printWriter_1.println("particles:" + this.particles.getId());
            printWriter_1.println("maxFps:" + this.maxFps);
            printWriter_1.println("difficulty:" + this.difficulty.getId());
            printWriter_1.println("fancyGraphics:" + this.fancyGraphics);
            printWriter_1.println("ao:" + this.ao.getValue());
            printWriter_1.println("biomeBlendRadius:" + this.biomeBlendRadius);
            switch(this.cloudRenderMode) {
            case FANCY:
               printWriter_1.println("renderClouds:true");
               break;
            case FAST:
               printWriter_1.println("renderClouds:fast");
               break;
            case OFF:
               printWriter_1.println("renderClouds:false");
            }

            printWriter_1.println("resourcePacks:" + GSON.toJson(this.resourcePacks));
            printWriter_1.println("incompatibleResourcePacks:" + GSON.toJson(this.incompatibleResourcePacks));
            printWriter_1.println("lastServer:" + this.lastServer);
            printWriter_1.println("lang:" + this.language);
            printWriter_1.println("chatVisibility:" + this.chatVisibility.getId());
            printWriter_1.println("chatOpacity:" + this.chatOpacity);
            printWriter_1.println("textBackgroundOpacity:" + this.textBackgroundOpacity);
            printWriter_1.println("backgroundForChatOnly:" + this.backgroundForChatOnly);
            if (this.client.window.getVideoMode().isPresent()) {
               printWriter_1.println("fullscreenResolution:" + ((VideoMode)this.client.window.getVideoMode().get()).asString());
            }

            printWriter_1.println("hideServerAddress:" + this.hideServerAddress);
            printWriter_1.println("advancedItemTooltips:" + this.advancedItemTooltips);
            printWriter_1.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
            printWriter_1.println("overrideWidth:" + this.overrideWidth);
            printWriter_1.println("overrideHeight:" + this.overrideHeight);
            printWriter_1.println("heldItemTooltips:" + this.heldItemTooltips);
            printWriter_1.println("chatHeightFocused:" + this.chatHeightFocused);
            printWriter_1.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
            printWriter_1.println("chatScale:" + this.chatScale);
            printWriter_1.println("chatWidth:" + this.chatWidth);
            printWriter_1.println("mipmapLevels:" + this.mipmapLevels);
            printWriter_1.println("useNativeTransport:" + this.useNativeTransport);
            printWriter_1.println("mainHand:" + (this.mainArm == Arm.LEFT ? "left" : "right"));
            printWriter_1.println("attackIndicator:" + this.attackIndicator.getId());
            printWriter_1.println("narrator:" + this.narrator.getId());
            printWriter_1.println("tutorialStep:" + this.tutorialStep.getName());
            printWriter_1.println("mouseWheelSensitivity:" + this.mouseWheelSensitivity);
            printWriter_1.println("rawMouseInput:" + Option.RAW_MOUSE_INPUT.get(this));
            printWriter_1.println("glDebugVerbosity:" + this.glDebugVerbosity);
            KeyBinding[] var3 = this.keysAll;
            int var4 = var3.length;

            int var5;
            for(var5 = 0; var5 < var4; ++var5) {
               KeyBinding keyBinding_1 = var3[var5];
               printWriter_1.println("key_" + keyBinding_1.getId() + ":" + keyBinding_1.getName());
            }

            SoundCategory[] var18 = SoundCategory.values();
            var4 = var18.length;

            for(var5 = 0; var5 < var4; ++var5) {
               SoundCategory soundCategory_1 = var18[var5];
               printWriter_1.println("soundCategory_" + soundCategory_1.getName() + ":" + this.getSoundVolume(soundCategory_1));
            }

            PlayerModelPart[] var19 = PlayerModelPart.values();
            var4 = var19.length;

            for(var5 = 0; var5 < var4; ++var5) {
               PlayerModelPart playerModelPart_1 = var19[var5];
               printWriter_1.println("modelPart_" + playerModelPart_1.getName() + ":" + this.enabledPlayerModelParts.contains(playerModelPart_1));
            }
         } catch (Throwable var15) {
            var2 = var15;
            throw var15;
         } finally {
            if (printWriter_1 != null) {
               if (var2 != null) {
                  try {
                     printWriter_1.close();
                  } catch (Throwable var14) {
                     var2.addSuppressed(var14);
                  }
               } else {
                  printWriter_1.close();
               }
            }

         }
      } catch (Exception var17) {
         LOGGER.error("Failed to save options", var17);
      }

      this.onPlayerModelPartChange();
   }

   public float getSoundVolume(SoundCategory soundCategory_1) {
      return this.soundVolumeLevels.containsKey(soundCategory_1) ? (Float)this.soundVolumeLevels.get(soundCategory_1) : 1.0F;
   }

   public void setSoundVolume(SoundCategory soundCategory_1, float float_1) {
      this.soundVolumeLevels.put(soundCategory_1, float_1);
      this.client.getSoundManager().updateSoundVolume(soundCategory_1, float_1);
   }

   public void onPlayerModelPartChange() {
      if (this.client.player != null) {
         int int_1 = 0;

         PlayerModelPart playerModelPart_1;
         for(Iterator var2 = this.enabledPlayerModelParts.iterator(); var2.hasNext(); int_1 |= playerModelPart_1.getBitFlag()) {
            playerModelPart_1 = (PlayerModelPart)var2.next();
         }

         this.client.player.networkHandler.sendPacket(new ClientSettingsC2SPacket(this.language, this.viewDistance, this.chatVisibility, this.chatColors, int_1, this.mainArm));
      }

   }

   public Set<PlayerModelPart> getEnabledPlayerModelParts() {
      return ImmutableSet.copyOf(this.enabledPlayerModelParts);
   }

   public void setPlayerModelPart(PlayerModelPart playerModelPart_1, boolean boolean_1) {
      if (boolean_1) {
         this.enabledPlayerModelParts.add(playerModelPart_1);
      } else {
         this.enabledPlayerModelParts.remove(playerModelPart_1);
      }

      this.onPlayerModelPartChange();
   }

   public void togglePlayerModelPart(PlayerModelPart playerModelPart_1) {
      if (this.getEnabledPlayerModelParts().contains(playerModelPart_1)) {
         this.enabledPlayerModelParts.remove(playerModelPart_1);
      } else {
         this.enabledPlayerModelParts.add(playerModelPart_1);
      }

      this.onPlayerModelPartChange();
   }

   public CloudRenderMode getCloudRenderMode() {
      return this.viewDistance >= 4 ? this.cloudRenderMode : CloudRenderMode.OFF;
   }

   public boolean shouldUseNativeTransport() {
      return this.useNativeTransport;
   }

   public void addResourcePackContainersToManager(ResourcePackContainerManager<ClientResourcePackContainer> resourcePackContainerManager_1) {
      resourcePackContainerManager_1.callCreators();
      Set<ClientResourcePackContainer> set_1 = Sets.newLinkedHashSet();
      Iterator iterator_1 = this.resourcePacks.iterator();

      while(true) {
         while(iterator_1.hasNext()) {
            String string_1 = (String)iterator_1.next();
            ClientResourcePackContainer clientResourcePackContainer_1 = (ClientResourcePackContainer)resourcePackContainerManager_1.getContainer(string_1);
            if (clientResourcePackContainer_1 == null && !string_1.startsWith("file/")) {
               clientResourcePackContainer_1 = (ClientResourcePackContainer)resourcePackContainerManager_1.getContainer("file/" + string_1);
            }

            if (clientResourcePackContainer_1 == null) {
               LOGGER.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", string_1);
               iterator_1.remove();
            } else if (!clientResourcePackContainer_1.getCompatibility().isCompatible() && !this.incompatibleResourcePacks.contains(string_1)) {
               LOGGER.warn("Removed resource pack {} from options because it is no longer compatible", string_1);
               iterator_1.remove();
            } else if (clientResourcePackContainer_1.getCompatibility().isCompatible() && this.incompatibleResourcePacks.contains(string_1)) {
               LOGGER.info("Removed resource pack {} from incompatibility list because it's now compatible", string_1);
               this.incompatibleResourcePacks.remove(string_1);
            } else {
               set_1.add(clientResourcePackContainer_1);
            }
         }

         resourcePackContainerManager_1.setEnabled(set_1);
         return;
      }
   }
}
