package multistylerpc.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(DiscordClient.class);
    public IPCClient client = null;
    public StyleManager mStyleManager = new StyleManager();
    private boolean isClosing = false;
    public void start() {
        isClosing = false;
        try {
            IDEvent eIdEvent = new IDEvent(-1);
            App.eventManager.callEvent(eIdEvent);
            client = new IPCClient(eIdEvent.getId());
            connect();
            update();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (client.getStatus() == PipeStatus.CONNECTED) close();
            client = null;
        }
    }
    private void connect() throws NoDiscordClientException{
        if (client == null)  return;
        client.setListener(new IPCListener() {
            @Override
            public void onReady(IPCClient client) {
                App.eventManager.callEvent(new ReadyEvent(client));
            }
        });
        try {
            client.connect();
        } catch (NoDiscordClientException e) {
            throw e;
        }
    }
    private void update() {
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
                    logger.error(e.getMessage(), e);
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
