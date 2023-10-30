package multistylerpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import multistylerpc.discord.DiscordClient;
import multistylerpc.discord.file.FileManager;
import multistylerpc.event.EventManager;
import multistylerpc.tray.TrayClient;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static FileManager cFileManager = new FileManager();
    public static EventManager eventManager = new EventManager();
    public static DiscordClient cDiscordClient = new DiscordClient();
    public static TrayClient cTrayClient = new TrayClient();
    public static void main(String[] args) {
        logger.info("Starting App");
        cFileManager.loadAll();
        cDiscordClient.start();
        logger.info("Starting TrayIcons");
        cTrayClient.createTray();
        logger.info("Finished");
    }
}
