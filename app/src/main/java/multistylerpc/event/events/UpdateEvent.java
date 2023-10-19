package multistylerpc.event.events;

import com.jagrosh.discordipc.IPCClient;

import multistylerpc.event.Event;

public class UpdateEvent extends Event{
    private IPCClient client;
    public UpdateEvent(IPCClient client) {
        this.client = client;
    }
    public IPCClient getClient() {
        return client;
    }
}
