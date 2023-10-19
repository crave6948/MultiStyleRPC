package multistylerpc.event;
import java.lang.reflect.Method;
public class EventHook {
    private final Listenable eventClass;
    private final Method method;
    private final boolean isIgnoreCondition;

    EventHook(Listenable eventClass, Method method, EventTarget eventTarget) {
        this.eventClass = eventClass;
        this.method = method;
        this.isIgnoreCondition = eventTarget.ignoreCondition();
    }

    public Listenable getEventClass() {
        return eventClass;
    }

    public Method getMethod() {
        return method;
    }

    public boolean isIgnoreCondition() {
        return isIgnoreCondition;
    }
}