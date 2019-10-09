package team.abnormals.tut_new.game;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.Window;
import com.mojang.blaze3d.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.abnormals.tut_new.WindowProvider;
import team.abnormals.tut_new.engine.*;
import team.abnormals.tut_new.engine.crash.CrashReport;
import team.abnormals.tut_new.engine.crash.CrashReportSection;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.OptionalInt;
import java.util.Queue;
import java.util.function.LongSupplier;

public class TestGame implements WindowEventHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final boolean IS_SYSTEM_MAC = SystemUtil.getOperatingSystem() == SystemUtil.OperatingSystem.OSX;

    public Window window;
    public WindowProvider windowProvider;
    public WindowSettings settings;
    private GlFramebuffer framebuffer;
    private final Queue<Runnable> renderTaskQueue = Queues.newConcurrentLinkedQueue();
    public boolean isRunning;
    public boolean crashed;
    private CrashReport crashReport;
    private static TestGame instance;
    public final File runDirectory;
    private final File assetDirectory;
    private final String gameVersion;
    private final String versionType;

    public TestGame(RunArgs runArgs_1) {
        this.settings = runArgs_1.windowSettings;
        instance = this;
        this.runDirectory = runArgs_1.directories.runDir;
        this.assetDirectory = runArgs_1.directories.assetDir;
//        this.resourcePackDir = runArgs_1.directories.resourcePackDir;
        this.gameVersion = runArgs_1.game.version;
        this.versionType = runArgs_1.game.versionType;
    }

    public static void main(String[] args) {
        new class_4491();
        final TestGame minecraftClient_1 = new TestGame();
        Thread.currentThread().setName("Render thread");
        RenderSystem.initRenderThread();

        try {
            minecraftClient_1.init();
        } catch (Throwable var65) {
            CrashReport crashReport_1 = CrashReport.create(var65, "Initializing game");
            crashReport_1.addElement("Initialization");
            minecraftClient_1.printCrashReport(minecraftClient_1.populateCrashReport(crashReport_1));
            return;
        }

        Thread thread_3;
        if (minecraftClient_1.method_22107()) {
            thread_3 = new Thread("Client thread") {
                public void run() {
                    try {
                        RenderSystem.initClientThread();
                        minecraftClient_1.start();
                    } catch (Throwable var2) {
                        LOGGER.error("Exception in client thread", var2);
                    }

                }
            };
            thread_3.start();

            while(true) {
                minecraftClient_1.method_22108();
            }
        } else {
            thread_3 = null;

            try {
                minecraftClient_1.start();
            } catch (Throwable var64) {
                LOGGER.error("Unhandled game exception", var64);
            }
        }

        try {
            minecraftClient_1.scheduleStop();
        } finally {
//            minecraftClient_1.stop();
        }

    }

    public static TestGame getInstance() {
        return instance;
    }

    public CrashReport populateCrashReport(CrashReport crashReport_1) {
        CrashReportSection crashReportSection_1 = crashReport_1.getSystemDetailsSection();
        crashReportSection_1.add("Launched Version", () -> this.gameVersion);
        crashReportSection_1.add("Backend library", RenderSystem::getBackendDescription);
        crashReportSection_1.add("Backend API", RenderSystem::getApiDescription);
        crashReportSection_1.add("GL Caps", RenderSystem::getCapsString);
        crashReportSection_1.add("Using VBOs", () -> "Yes");
        crashReportSection_1.add("Is Modded", () -> {
            String string_1 = ClientBrandRetriever.getClientModName();
            if (!"vanilla".equals(string_1)) {
                return "Definitely; Client brand changed to '" + string_1 + "'";
            } else {
                return TestGame.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.";
            }
        });
        crashReportSection_1.add("Type", (Object)"Client (map_client.txt)");
        /*crashReportSection_1.add("Resource Packs", () -> {
            StringBuilder stringBuilder_1 = new StringBuilder();
            Iterator var2 = this.options.resourcePacks.iterator();

            while(var2.hasNext()) {
                String string_1 = (String)var2.next();
                if (stringBuilder_1.length() > 0) {
                    stringBuilder_1.append(", ");
                }

                stringBuilder_1.append(string_1);
                if (this.options.incompatibleResourcePacks.contains(string_1)) {
                    stringBuilder_1.append(" (incompatible)");
                }
            }

            return stringBuilder_1.toString();
        });
        crashReportSection_1.add("Current Language", () -> {
            return this.languageManager.getLanguage().toString();
        });*/
        crashReportSection_1.add("CPU", class_4494::method_22089);
        /*if (this.world != null) {
            this.world.addDetailsToCrashReport(crashReport_1);
        }*/

        return crashReport_1;
    }

    public void setCrashReport(CrashReport crashReport_1) {
        this.crashed = true;
        this.crashReport = crashReport_1;
    }

    public void printCrashReport(CrashReport crashReport_1) {
        File file_1 = new File(getInstance().runDirectory, "crash-reports");
        File file_2 = new File(file_1, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
        Bootstrap.println(crashReport_1.asString());
        if (crashReport_1.getFile() != null) {
            Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReport_1.getFile());
            System.exit(-1);
        } else if (crashReport_1.writeToFile(file_2)) {
            Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + file_2.getAbsolutePath());
            System.exit(-1);
        } else {
            Bootstrap.println("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }

    }

    public void start() {
        try {
            boolean boolean_1 = false;

            while(this.isRunning) {
                if (this.crashed/* && this.crashReport != null*/) {
//                    this.printCrashReport(this.crashReport);
                    return;
                }

                try {
                    this.render(!boolean_1);
                } catch (OutOfMemoryError var3) {
                    if (boolean_1) {
                        throw var3;
                    }

//                    this.cleanUpAfterCrash();
//                    this.openScreen(new OutOfMemoryScreen());
                    System.gc();
                    LOGGER.fatal("Out of memory", var3);
                    boolean_1 = true;
                }
            }
        } /*catch (CrashException var4) {
            this.populateCrashReport(var4.getReport());
            this.cleanUpAfterCrash();
            LOGGER.fatal("Reported exception thrown!", var4);
            this.printCrashReport(var4.getReport());
        }*/ catch (Throwable var5) {
//            CrashReport crashReport_1 = this.populateCrashReport(new CrashReport("Unexpected error", var5));
            LOGGER.fatal("Unreported exception thrown!", var5);
//            this.cleanUpAfterCrash();
//            this.printCrashReport(crashReport_1);
        }

    }

    public boolean method_22108() {
        return this.isRunning;
    }

    public void close() {
        try {
//            this.spriteAtlas.clear();
//            this.textRenderer.close();
//            this.fontManager.close();
//            this.gameRenderer.close();
//            this.worldRenderer.close();
//            this.soundManager.close();
//            this.resourcePackContainerManager.close();
//            this.particleManager.clearAtlas();
//            this.statusEffectSpriteManager.close();
//            this.paintingManager.close();
            SystemUtil.shutdownServerWorkerExecutor();
        } finally {
            this.windowProvider.close();
            this.window.close();
        }

    }

    public void scheduleStop() {
        this.isRunning = false;
    }

    private void render(boolean boolean_1) {
        this.window.setPhase("Pre render");
        long long_1 = SystemUtil.getMeasuringTimeNano();
//        this.profiler.startTick();
        if (this.window.method_22093()) {
            this.scheduleStop();
        }

        /*if (this.resourceReloadFuture != null && !(this.overlay instanceof SplashScreen)) {
            CompletableFuture<Void> completableFuture_1 = this.resourceReloadFuture;
            this.resourceReloadFuture = null;
            this.reloadResources().thenRun(() -> {
                completableFuture_1.complete((Object)null);
            });
        }*/

        Runnable runnable_1;
        while((runnable_1 = this.renderTaskQueue.poll()) != null) {
            runnable_1.run();
        }

        /*if (boolean_1) {
            this.renderTickCounter.beginRenderTick(SystemUtil.getMeasuringTimeMs());
            this.profiler.push("scheduledExecutables");
            this.executeTaskQueue();
            this.profiler.pop();
        }*/

        long long_2 = SystemUtil.getMeasuringTimeNano();
        /*this.profiler.push("tick");
        if (boolean_1) {
            for(int int_1 = 0; int_1 < Math.min(10, this.renderTickCounter.ticksThisFrame); ++int_1) {
                this.tick();
            }
        }*/

//        this.mouse.updateMouse();
        this.window.setPhase("Render");
        RenderSystem.pollEvents();
        long long_3 = SystemUtil.getMeasuringTimeNano() - long_2;
//        this.profiler.swap("sound");
//        this.soundManager.updateListenerPosition(this.gameRenderer.getCamera());
//        this.profiler.pop();
//        this.profiler.push("render");
        RenderSystem.pushMatrix();
        RenderSystem.clear(16640, IS_SYSTEM_MAC);
        this.framebuffer.beginWrite(true);
//        this.profiler.push("display");
        RenderSystem.enableTexture();
        /*this.profiler.pop();
        if (!this.skipGameRender) {
            this.profiler.swap("gameRenderer");
            this.gameRenderer.render(this.paused ? this.pausedTickDelta : this.renderTickCounter.tickDelta, long_1, boolean_1);
            this.profiler.swap("toasts");
            this.toastManager.draw();
            this.profiler.pop();
        }

        this.profiler.endTick();
        if (this.options.debugEnabled && this.options.debugProfilerEnabled && !this.options.hudHidden) {
            this.profiler.getController().enable();
            this.drawProfilerResults();
        } else {
            this.profiler.getController().disable();
        }*/

        this.framebuffer.endWrite();
        RenderSystem.popMatrix();
        RenderSystem.pushMatrix();
        this.framebuffer.draw(this.window.getFramebufferWidth(), this.window.getFramebufferHeight());
        RenderSystem.popMatrix();
//        this.profiler.startTick();
        this.updateDisplay(true);
        Thread.yield();
        this.window.setPhase("Post render");
        /*++this.fpsCounter;
        boolean boolean_2 = this.isIntegratedServerRunning() && (this.currentScreen != null && this.currentScreen.isPauseScreen() || this.overlay != null && this.overlay.pausesGame()) && !this.server.isRemote();
        if (this.paused != boolean_2) {
            if (this.paused) {
                this.pausedTickDelta = this.renderTickCounter.tickDelta;
            } else {
                this.renderTickCounter.tickDelta = this.pausedTickDelta;
            }

            this.paused = boolean_2;
        }

        long long_4 = SystemUtil.getMeasuringTimeNano();
        this.metricsData.pushSample(long_4 - this.lastMetricsSampleTime);
        this.lastMetricsSampleTime = long_4;

        while(SystemUtil.getMeasuringTimeMs() >= this.nextDebugInfoUpdateTime + 1000L) {
            currentFps = this.fpsCounter;
            this.fpsDebugString = String.format("%d fps (%d chunk update%s) T: %s%s%s%s", currentFps, ChunkRenderer.chunkUpdateCount, ChunkRenderer.chunkUpdateCount == 1 ? "" : "s", (double)this.options.maxFps == Option.FRAMERATE_LIMIT.getMax() ? "inf" : this.options.maxFps, this.options.enableVsync ? " vsync" : "", this.options.fancyGraphics ? "" : " fast", this.options.cloudRenderMode == CloudRenderMode.OFF ? "" : (this.options.cloudRenderMode == CloudRenderMode.FAST ? " fast-clouds" : " fancy-clouds"));
            ChunkRenderer.chunkUpdateCount = 0;
            this.nextDebugInfoUpdateTime += 1000L;
            this.fpsCounter = 0;
            this.snooper.update();
            if (!this.snooper.isActive()) {
                this.snooper.method_5482();
            }
        }

        this.profiler.endTick();*/
    }

    public void updateDisplay(boolean boolean_1) {
//        this.profiler.push("display_update");
        this.window.setFullscreen(false);
//        this.profiler.pop();
        if (boolean_1/* && this.isFramerateLimited()*/) {
//            this.profiler.push("fpslimit_wait");
            this.window.waitForFramerateLimit();
//            this.profiler.pop();
        }

    }

    public void init() {
        WindowSettings settings = new WindowSettings(854, 480, OptionalInt.of(400), OptionalInt.of(400), false);
        this.windowProvider.createWindow(settings, null, "");

        LOGGER.info("Backend library: {}", RenderSystem.getBackendDescription());
        WindowSettings windowSettings_1 = this.settings;
        /*if (this.options.overrideHeight > 0 && this.options.overrideWidth > 0) {
            windowSettings_1 = new WindowSettings(this.options.overrideWidth, this.options.overrideHeight, windowSettings_1.fullscreenWidth, windowSettings_1.fullscreenHeight, windowSettings_1.fullscreen);
        }*/

        LongSupplier longSupplier_1 = RenderSystem.initBackendSystem();
        if (longSupplier_1 != null) {
            SystemUtil.nanoTimeSupplier = longSupplier_1;
        }

        this.windowProvider = new WindowProvider(this);
        this.window = this.windowProvider.createWindow(windowSettings_1, /*this.options.fullscreenResolution*/null, "Minecraft 19w35a"/* + SharedConstants.getGameVersion().getName()*/);
        this.onWindowFocusChanged(true);

        /*try {
            InputStream inputStream_1 = this.getResourcePackDownloader().getPack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_16x16.png"));
            InputStream inputStream_2 = this.getResourcePackDownloader().getPack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_32x32.png"));
            this.window.setIcon(inputStream_1, inputStream_2);
        } catch (IOException var6) {
            LOGGER.error("Couldn't set icon", var6);
        }

        this.window.setFramerateLimit(this.options.maxFps);*/
        RenderSystem.initRenderer(1, false);
        this.framebuffer = new GlFramebuffer(this.window.getFramebufferWidth(), this.window.getFramebufferHeight(), true, IS_SYSTEM_MAC);
        this.framebuffer.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);

        this.window.setPhase("Startup");
        RenderSystem.enableTexture();
        RenderSystem.shadeModel(7425);
        RenderSystem.clearDepth(1.0D);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(515);
        RenderSystem.enableAlphaTest();
        RenderSystem.alphaFunc(516, 0.1F);
        RenderSystem.cullFace(class_4493.FaceSides.BACK);
        RenderSystem.matrixMode(5889);
        RenderSystem.loadIdentity();
        RenderSystem.matrixMode(5888);
        this.window.setPhase("Post startup");

        RenderSystem.viewport(0, 0, this.window.getFramebufferWidth(), this.window.getFramebufferHeight());


        this.window.setVsync(true);
        this.window.method_21668(true);
        this.window.logOnGlError();
    }

    public boolean forcesUnicodeFont() {
        return false;
    }

    public boolean method_22107() {
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean var1) {

    }

    @Override
    public void onResolutionChanged() {
        int int_1 = this.window.calculateScaleFactor(0, this.forcesUnicodeFont());
        this.window.setScaleFactor((double)int_1);
        /*if (this.currentScreen != null) {
            this.currentScreen.resize(this, this.window.getScaledWidth(), this.window.getScaledHeight());
        }*/

        GlFramebuffer glFramebuffer_1 = this.framebuffer;
        if (glFramebuffer_1 != null) {
            glFramebuffer_1.resize(this.window.getFramebufferWidth(), this.window.getFramebufferHeight(), IS_SYSTEM_MAC);
        }
    }

}