package multistylerpc.discord.value.values;

import org.json.JSONObject;

import multistylerpc.discord.value.Value;

public class TextValue extends Value<String>{
    public TextValue(String name, String value) {
        super(name, value);
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject().put(name, value);
    }

    @Override
    public void fromJson(JSONObject element) {
        this.value = element.getString(name);
    }
}
