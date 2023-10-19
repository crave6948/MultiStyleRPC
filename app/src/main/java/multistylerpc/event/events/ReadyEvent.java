package multistylerpc.event.events;

import com.jagrosh.discordipc.IPCClient;

import multistylerpc.event.Event;

public class ReadyEvent extends Event{
    private IPCClient client;
    public ReadyEvent(IPCClient client) {
        this.client = client;
    }
    public IPCClient getClient() {
        return client;
    }
}
