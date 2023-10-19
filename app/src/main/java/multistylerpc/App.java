package multistylerpc;

import java.time.OffsetDateTime;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

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
