package coffeecatteam.theultimatetile.utils;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordHandler {

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

        DiscordRPC.discordUpdatePresence(
                new DiscordRichPresence.Builder("")
                        .setDetails("Main Menu")
                        .setBigImage("ultimatebg", "TUT")
                        .build());
    }
}
