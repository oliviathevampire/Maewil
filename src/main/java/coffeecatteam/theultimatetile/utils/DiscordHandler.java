package coffeecatteam.theultimatetile.utils;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordHandler {

    private static final long timeStamp = System.currentTimeMillis();
    private static DiscordHandler instance = new DiscordHandler();

    public static DiscordHandler getInstance() {
        return instance;
    }

    public void setup() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down DiscordHook.");
            DiscordRPC.discordShutdown();
        }));

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
        }).build();
        DiscordRPC.discordInitialize("502962688733741056", handlers, true);
        DiscordRPC.discordRunCallbacks();

        updatePresence("Main Menu");
    }

    public void updatePresence(String details) {
        updatePresence(details, "");
    }

    public void updatePresence(String details, String state) {
        updatePresence(details, state, false);
    }

    public void updatePresence(String details, String state, boolean inGame) {
        DiscordRichPresence rich = new DiscordRichPresence();
        rich.details = details;
        rich.state = state;
        rich.largeImageKey = "ultimatebg";
        if (inGame)
            rich.smallImageKey = getSmallImage(Utils.getRandomInt(5));
        rich.startTimestamp = timeStamp;
        DiscordRPC.discordUpdatePresence(rich);
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
}
