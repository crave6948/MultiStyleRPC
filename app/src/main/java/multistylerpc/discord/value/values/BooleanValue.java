package multistylerpc.discord.value.values;

import org.json.JSONObject;

import multistylerpc.discord.value.Value;

public class BooleanValue extends Value<Boolean>{
    public BooleanValue(String name, Boolean value) {
        super(name, value);
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject().put(name, value);
    }

    @Override
    public void fromJson(JSONObject element) {
        this.value = element.getBoolean(name);
    }
}
