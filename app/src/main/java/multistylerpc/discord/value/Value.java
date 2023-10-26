package multistylerpc.discord.value;

import com.google.gson.JsonElement;

public abstract class Value<T> {
    public final String name;
    public T value;
    public Value(String name,T value) {
        this.name = name;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public T get() {
        return value;
    }
    public void set(T newValue) {
        if (newValue == value) return;
        T oldValue = get();
        try {
            onChange(oldValue, newValue);
            changeValue(newValue);
            onChanged(oldValue, newValue);
        } catch (Exception e) {
            System.out.println(String.format("[ValueSystem (%s)]: %s (%s) [%s >> %s]", name, e.getClass().getName(),e.getMessage(),oldValue,newValue));
        }
    }

    public abstract JsonElement toJson();
    public abstract void fromJson(JsonElement element);
    
    public void changeValue(T value) {
        this.value = value;
    }

    public void onChange(T oldValue, T newValue) {}
    public void onChanged(T oldValue, T newValue) {}
}
