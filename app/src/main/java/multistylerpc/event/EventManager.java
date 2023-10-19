package multistylerpc.event;
 
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private Map<Class<? extends Event>, List<EventHook>> registry = new HashMap<>();

    /**
     * Register listener
     *
     * @param listener to register
     */
    public void registerListener(Listenable listener) {
        for (java.lang.reflect.Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventTarget.class) && method.getParameterCount() == 1) {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                Class<? extends Event> eventClass = getEventClass(method);
                EventTarget eventTarget = method.getAnnotation(EventTarget.class);

                List<EventHook> invokableEventTargets = registry.getOrDefault(eventClass, new ArrayList<>());
                invokableEventTargets.add(new EventHook(listener, method, eventTarget));
                registry.put(eventClass, invokableEventTargets);
            }
        }
    }
    private Class<? extends Event> getEventClass(Method method) {
    try {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 1 && Event.class.isAssignableFrom(parameterTypes[0])) {
            @SuppressWarnings("unchecked")
            Class<? extends Event> eventClass = (Class<? extends Event>) parameterTypes[0];
            return eventClass;
        }
    } catch (ClassCastException e) {
        e.printStackTrace();
    }
    return null;
}

    /**
     * Unregister listener
     *
     * @param listenable for unregister
     */
    public void unregisterListener(Listenable listenable) {
        for (Map.Entry<Class<? extends Event>, List<EventHook>> entry : registry.entrySet()) {
            entry.getValue().removeIf(eventHook -> eventHook.getEventClass() == listenable);
        }
    }

    /**
     * Call event to listeners
     *
     * @param event to call
     */
    public void callEvent(Event event) {
        List<EventHook> targets = registry.get(event.getClass());
        if (targets == null) {
            return;
        }

        for (EventHook invokableEventTarget : targets) {
            try {
                if (!invokableEventTarget.getEventClass().handleEvents() && !invokableEventTarget.isIgnoreCondition()) {
                    continue;
                }

                invokableEventTarget.getMethod().invoke(invokableEventTarget.getEventClass(), event);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}

