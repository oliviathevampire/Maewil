package coffeecatteam.theultimatetile.utils;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.coffeecatutils.NumberUtils;

public class DiscordHandler {

    private static final long timeStamp = System.currentTimeMillis();
    public static final DiscordHandler INSTANCE = new DiscordHandler();

    private DiscordRPC rpc;
    private String userId = "NOT SET";

    private boolean LEVEL_CREATE = false;

    public void setup() {
        Logger.print("");
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        rpc = DiscordRPC.INSTANCE;

        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = user -> {
            userId = user.username + "#" + user.discriminator;
            Logger.print("Connected to discord : " + userId);
            Logger.print("Discord rich presence setup for " + userId);
        };
        rpc.Discord_Initialize("502962688733741056", handlers, true, "");

        new Thread(() -> {
            Logger.print("Started RPC Callback Handler");
            while (!Thread.currentThread().isInterrupted()) {
                rpc.Discord_RunCallbacks();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "RPC-Callback-Handler").start();

        updatePresence((LEVEL_CREATE ? "Level Creator" : "Main Menu"));
    }

    public void updatePresence(String details) {
        updatePresence(details, "");
    }

    public void updatePresence(String details, String state) {
        updatePresence(details, state, false);
    }

    public void updatePresence(String details, String state, boolean inGame) {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.details = details;
        presence.state = state;
        presence.largeImageKey = "ultimatebg" + (LEVEL_CREATE ? "_edit" : "");
        if (inGame)
            presence.smallImageKey = getSmallImage(NumberUtils.getRandomInt(5));
        presence.startTimestamp = timeStamp;
        rpc.Discord_UpdatePresence(presence);
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

    public void LEVEL_CREATE(boolean LEVEL_CREATE) {
        this.LEVEL_CREATE = LEVEL_CREATE;
    }

    public void shutdown() {
        Logger.print("\n" + userId + " is disconnecting!");
        rpc.Discord_ClearPresence();
        Logger.print("Cleared rich presence!");
        rpc.Discord_Shutdown();
        Logger.print(userId + " hss disconnected!");
    }
}
