package multistylerpc.discord.value.values;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import multistylerpc.discord.value.Value;

public class BooleanValue extends Value<Boolean>{
    public BooleanValue(String name, Boolean value) {
        super(name, value);
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(value);
    }

    @Override
    public void fromJson(JsonElement element) {
        if (element.isJsonPrimitive())
            this.value = element.getAsBoolean() || element.getAsString().equalsIgnoreCase("true");
    }
}
