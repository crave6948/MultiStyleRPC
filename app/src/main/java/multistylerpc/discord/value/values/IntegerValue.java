package multistylerpc.discord.value.values;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import multistylerpc.discord.value.Value;

public class IntegerValue extends Value<Integer>{
    private final int min;
    private final int max;
    public IntegerValue(String name,int value) {this(name, value,0,Integer.MAX_VALUE);}
    public IntegerValue(String name,int value,int min, int max) {
        super(name, value);
        this.min = min;
        this.max = max;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(value);
    }
    @Override
    public void fromJson(JsonElement element) {
        if (element.isJsonPrimitive())
            this.value = element.getAsInt();
    }
}
