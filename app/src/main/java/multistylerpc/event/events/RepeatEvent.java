package multistylerpc.event.events;

import multistylerpc.event.Event;

public class RepeatEvent extends Event{
    private long repeatEvery;
    public RepeatEvent(long repeatEvery) {
        this.repeatEvery = repeatEvery;
    }
    public long getRepeatEvery() {
        return repeatEvery;
    }
    public void setRepeatEvery(long repeatEvery) {
        this.repeatEvery = repeatEvery;
    }
}
