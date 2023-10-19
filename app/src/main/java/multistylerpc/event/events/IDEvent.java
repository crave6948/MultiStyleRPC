package multistylerpc.event.events;

import multistylerpc.event.Event;

public class IDEvent extends Event {
    private long id;
    public IDEvent(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
