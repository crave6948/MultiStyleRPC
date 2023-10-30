package multistylerpc.tray;
import java.awt.SystemTray;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.TrayIcon;
import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import multistylerpc.App;
import multistylerpc.tray.menu.TrayMenuManager;
public class TrayClient {
    private static final Logger logger = LoggerFactory.getLogger(TrayClient.class);
    public TrayMenuManager mTrayMenuManager = new TrayMenuManager();
    public void createTray() {
        logger.info("Creating tray...");
        // Check if the SystemTray is supported on this platform
        if (SystemTray.isSupported()) {
            // Get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Nady.jpeg"));
            Image image = icon.getImage();
            // Create a popup menu for the system tray icon
            PopupMenu popupMenu = new PopupMenu();
            if (App.cDiscordClient.mStyleManager.selectedStyleModule != null) {
                mTrayMenuManager.createMenu(App.cDiscordClient.mStyleManager.selectedStyleModule);
                mTrayMenuManager.allMenus().forEach(it -> {popupMenu.add(it);});
            }
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    App.cDiscordClient.close();
                    System.exit(0);
                    logger.info("Closing App by Tray Icon");
                }
            });
            popupMenu.addSeparator();
            popupMenu.add(exitItem);

            // Create a tray icon with your image and popup menu
            TrayIcon trayIcon = new TrayIcon(image, "My System Tray Icon", popupMenu);

            try {
                // Add the tray icon to the SystemTray
                tray.add(trayIcon);
            } catch (AWTException e) {
                logger.error("TrayIcon could not be added.");
            }
        } else {
            logger.error("SystemTray is not supported.");
            return;
        }
        logger.info("Finished Creating tray.");
    }
}
