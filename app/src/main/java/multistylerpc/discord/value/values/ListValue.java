package multistylerpc.discord.value.values;

import java.util.List;

import org.json.JSONObject;

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
