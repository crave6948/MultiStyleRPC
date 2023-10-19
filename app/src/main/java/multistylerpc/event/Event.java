package multistylerpc.event;

public class Event {
    private boolean cancel = false;
    public boolean isCancelled() {
        return cancel;
    }
    public void cancelEvent() {
        cancel = true;
    }
    // Event implementation in Java
}
