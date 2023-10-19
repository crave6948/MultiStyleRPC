package multistylerpc.discord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import multistylerpc.App;
import multistylerpc.discord.style.StyleManager;
import multistylerpc.event.events.IDEvent;
import multistylerpc.event.events.ReadyEvent;
import multistylerpc.event.events.RepeatEvent;
import multistylerpc.event.events.UpdateEvent;

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
        IDEvent eIdEvent = new IDEvent(-1);
        App.eventManager.callEvent(eIdEvent);
        client = new IPCClient(eIdEvent.getId());
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
                App.eventManager.callEvent(new ReadyEvent(client));
            }
        });
    }
    public void update() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isClosing) {
                        App.eventManager.callEvent(new UpdateEvent(client));
                        RepeatEvent repeatEvent = new RepeatEvent(5000L);
                        App.eventManager.callEvent(repeatEvent);
                        if (repeatEvent.isCancelled()) break;
                        sleep(repeatEvent.getRepeatEvery());
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
