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

import multistylerpc.App;
public class TrayClient {
    public void createTray() {
        System.out.println("Creating tray...");
        // Check if the SystemTray is supported on this platform
        if (SystemTray.isSupported()) {
            // Get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Nady.jpeg"));
            // Load an image for your icon (replace "icon.png" with your image file)
            // Image image = Toolkit.getDefaultToolkit().getImage("Nady.jpeg");
            Image image = icon.getImage();

            // Create a popup menu for the system tray icon
            PopupMenu popupMenu = new PopupMenu();
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    App.cDiscordClient.close();
                    System.exit(0);
                }
            });
            popupMenu.add(exitItem);

            // Create a tray icon with your image and popup menu
            TrayIcon trayIcon = new TrayIcon(image, "My System Tray Icon", popupMenu);

            try {
                // Add the tray icon to the SystemTray
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        } else {
            System.err.println("SystemTray is not supported.");
            return;
        }
        System.out.println("Finished Creating tray.");
    }
}
