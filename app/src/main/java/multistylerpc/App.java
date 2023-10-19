package multistylerpc;

import multistylerpc.discord.DiscordClient;
import multistylerpc.event.EventManager;
import multistylerpc.tray.TrayClient;

public class App {
    public static EventManager eventManager = new EventManager();
    public static DiscordClient cDiscordClient = new DiscordClient();
    public static TrayClient cTrayClient = new TrayClient();
    public static void main(String[] args) {
        System.out.println("Starting App");
        cDiscordClient.start();
        System.out.println("Starting TrayIcons");
        cTrayClient.createTray();
        System.out.println("Finished");
    }
}
