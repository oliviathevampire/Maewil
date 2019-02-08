package coffeecatteam.theultimatetile.utils;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.DevEnvUtils;
import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;

public class DiscordHandler {

    private static final long timeStamp = System.currentTimeMillis();
    public static final DiscordHandler INSTANCE = new DiscordHandler();

    private DiscordRPC rpc;
    private String userId = "NOT SET";

    public static boolean READY = false;

    public void setup() {
        if (!ArgUtils.hasArgument(TutEngine.getTutEngine().getArgs(), "-disableDiscordRP")) {
            TutEngine.getTutEngine().getLogger().print();
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
            rpc = DiscordRPC.INSTANCE;

            DiscordEventHandlers handlers = new DiscordEventHandlers();
            handlers.ready = user -> {
                userId = user.username + "#" + user.discriminator;
                DiscordHandler.READY = true;
                TutEngine.getTutEngine().getLogger().print("Connected to discord");
                TutEngine.getTutEngine().getLogger().print("Discord rich presence setup for " + userId);
                TutEngine.getTutEngine().getLogger().print("Ready: " + READY);
                TutEngine.getTutEngine().getLogger().print();
            };
            rpc.Discord_Initialize("502962688733741056", handlers, true, "");

            new Thread(() -> {
                TutEngine.getTutEngine().getLogger().print("Started RPC Callback Handler");
                while (!Thread.currentThread().isInterrupted()) {
                    rpc.Discord_RunCallbacks();
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
        if (!ArgUtils.hasArgument(TutEngine.getTutEngine().getArgs(), "-disableDiscordRP")) {
            DiscordRichPresence presence = new DiscordRichPresence();
            presence.details = (DevEnvUtils.isRunningFromDevEnviroment() ? "Developing TUT - " : "") + details;
            presence.state = state;
            presence.largeImageKey = "ultimatebg";
            if (inGame)
                presence.smallImageKey = getSmallImage(NumberUtils.getRandomInt(5));
            presence.startTimestamp = timeStamp;
            rpc.Discord_UpdatePresence(presence);
        }
    }

    private String getSmallImage(int id) {
        switch (id) {
            default:
            case 0:
                return "cow";
            case 1:
                return "fox";
            case 2:
                return "pig";
            case 3:
                return "player";
            case 4:
                return "sheep";
            case 5:
                return "tree";
        }
    }

    public void shutdown() {
        if (!ArgUtils.hasArgument(TutEngine.getTutEngine().getArgs(), "-disableDiscordRP")) {
            TutEngine.getTutEngine().getLogger().print("" + userId + " is disconnecting!");
            rpc.Discord_ClearPresence();
            TutEngine.getTutEngine().getLogger().print("Cleared rich presence!");
            rpc.Discord_Shutdown();
            TutEngine.getTutEngine().getLogger().print(userId + " hss disconnected!");
        }
    }
}
