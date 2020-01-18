package io.github.vampirestudios.tdg.utils;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.DevEnvUtils;
import coffeecatteam.coffeecatutils.logger.CatLogger;

public class DiscordHandler {

    private CatLogger logger;
    private static final String disbaleArg = "-disableDiscordRP";

    private static final long timeStamp = System.currentTimeMillis();
    public static final DiscordHandler INSTANCE = new DiscordHandler();

    private DiscordRPC rpc;
    private String userId = "NOT SET";

    public static boolean READY = false;

    public void setup() {
        if (!ArgUtils.hasArgument(disbaleArg)) {
            logger = new CatLogger("Maewil-DiscordRichPresence");
            logger.println();
//            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
            rpc = DiscordRPC.INSTANCE;

            DiscordEventHandlers handlers = new DiscordEventHandlers();
            handlers.ready = user -> {
                userId = user.username + "#" + user.discriminator;
                DiscordHandler.READY = true;
                logger.warn("Connected to discord");
                logger.info("Discord rich presence setup for " + userId);
                logger.info("Ready: " + READY);
                logger.println();
            };
            rpc.Discord_Initialize("665912914280054785", handlers, true, "");

            new Thread(() -> {
                logger.warn("Started RPC Callback Handler");
                while (!Thread.currentThread().isInterrupted()) {
                    rpc.Discord_RunCallbacks();
                    if (!READY) {
                        logger.info("Connecting rich presence...");
                        rpc.Discord_UpdateHandlers(handlers);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }, "RPC-Callback-Handler").start();

            updatePresence("Main Menu");
        }
    }

    public void updatePresence(String details) {
        updatePresence(details, "");
    }

    public void updatePresence(String details, String state) {
        updatePresence(details, state, false);
    }

    public void updatePresence(String details, String state, boolean inGame) {
        if (!ArgUtils.hasArgument(disbaleArg)) {
            DiscordRichPresence presence = new DiscordRichPresence();
            presence.details = (DevEnvUtils.isRunningFromDevEnviroment() ? "Developing Maewil - " : "") + details;
            presence.state = state;
            presence.largeImageKey = "default_1024";
            presence.smallImageKey = "profile_1024";
            presence.startTimestamp = timeStamp;
            rpc.Discord_UpdatePresence(presence);
        }
    }

    public void shutdown() {
        if (!ArgUtils.hasArgument(disbaleArg)) {
            logger.warn("" + userId + " is disconnecting!");
            rpc.Discord_ClearPresence();
            logger.info("Cleared rich presence!");
            rpc.Discord_Shutdown();
            logger.info(userId + " hss disconnected!");
        }
    }
}
