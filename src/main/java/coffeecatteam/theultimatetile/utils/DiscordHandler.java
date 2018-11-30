package coffeecatteam.theultimatetile.utils;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.coffeecatutils.NumberUtils;

public class DiscordHandler {

    private static final long timeStamp = System.currentTimeMillis();
    public static final DiscordHandler INSTANCE = new DiscordHandler();

    private DiscordRPC lib;
    private boolean LEVEL_CREATE = false;

    public void setup() {
        lib = DiscordRPC.INSTANCE;
        String appID = "502962688733741056";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = user -> Logger.print("Connected to discord : " + user.username + "#" + user.discriminator);
        lib.Discord_Initialize(appID, handlers, true, "");

        Thread t = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    lib.Discord_Shutdown();
                    break;
                }
            }
        }, "RPC-Callback-Handler");
        t.start();

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
        lib.Discord_UpdatePresence(presence);
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

    public boolean LEVEL_CREATE() {
        return LEVEL_CREATE;
    }

    public void LEVEL_CREATE(boolean LEVEL_CREATE) {
        this.LEVEL_CREATE = LEVEL_CREATE;
    }
}
