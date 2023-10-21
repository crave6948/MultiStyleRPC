package multistylerpc.discord.value.values;

import java.util.Arrays;

import org.json.JSONObject;

import multistylerpc.discord.value.Value;

public class ListValue extends Value<String>{
    private final String[] list;
    public ListValue(String name, String value,String[] list) {
        super(name, value);
        this.list = list;
    }
    public boolean contains(String string) {
        return Arrays.stream(list).anyMatch(it -> it.equalsIgnoreCase(string));
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
    public JSONObject toJson() {
        return new JSONObject().put(name, value);
    }
    @Override
    public void fromJson(JSONObject element) {
        try {
            String jValue = element.getString(name);
            for (String string : list) {
                if (string.equalsIgnoreCase(jValue)) {
                    this.value = string;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
