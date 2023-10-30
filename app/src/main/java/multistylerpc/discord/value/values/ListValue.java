package multistylerpc.discord.value.values;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import multistylerpc.discord.value.Value;

public class ListValue extends Value<String>{
    public final List<String> list;
    public ListValue(String name, String value,List<String> list) {
        super(name, value);
        this.list = list;
    }
    public boolean contains(String string) {
        return list.stream().anyMatch(it -> it.equalsIgnoreCase(string));
    }
    @Override
    public void changeValue(String value) {
        for (String element : list) {
            if (element.equalsIgnoreCase(value)) {
                this.value = element;
                break;
            }
        }
    }
    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(value);
    }
    @Override
    public void fromJson(JsonElement element) {
        if (element.isJsonPrimitive()) changeValue(element.getAsString());
    }
}
