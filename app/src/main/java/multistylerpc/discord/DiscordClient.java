package multistylerpc.discord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import multistylerpc.discord.style.StyleManager;

public class DiscordClient {
    public IPCClient client = null;
    public StyleManager mStyleManager = new StyleManager();
    private boolean isClosing = false;
    public void start() {
        isClosing = false;
        try {
            newclient();
            connect();
            update();
        } catch (NullPointerException e) {
            client.close();
            client = null;
            System.out.println("No Selected Style");
        } catch (Exception e) {
            e.printStackTrace();
            if (client.getStatus() == PipeStatus.CONNECTED) close();
            client = null;
        }
    }
    public void newclient() throws NullPointerException {
        client = new IPCClient(mStyleManager.getSelectedID());
    }
    public void connect() {
        if (client == null)  return;
        if (client.getStatus() == PipeStatus.CONNECTED) {
            close();
        }
        setListener();
        try {
            client.connect();
        } catch (NoDiscordClientException e) {
            e.printStackTrace();
            close();
        }
    }
    public void setListener() {
        client.setListener(new IPCListener() {
            @Override
            public void onReady(IPCClient client) {
                mStyleManager.onReady(client);
            }
        });
    }
    public void update() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isClosing) {
                        mStyleManager.onUpdate(client);
                        sleep(mStyleManager.nextSleepTime());
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                close();
            }
        };
        t.start();
    }
    public void close() {
        isClosing = true;
        if (client.getStatus() != PipeStatus.CONNECTED) {client = null;return;}
        client.close();
        client = null;
    }
}
