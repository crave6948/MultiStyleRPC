package multistylerpc.discord.value.values;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import multistylerpc.discord.value.Value;

public class FloatValue extends Value<Float>{
    private final float min;
    private final float max;
    public FloatValue(String name,float value) {this(name, value,0F,Float.MAX_VALUE);}
    public FloatValue(String name,float value,float min, float max) {
        super(name, value);
        this.min = min;
        this.max = max;
    }
    public float getMin() {
        return min;
    }
    public float getMax() {
        return max;
    }
    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(value);
    }
    @Override
    public void fromJson(JsonElement element) {
        if (element.isJsonPrimitive())
            this.value = element.getAsFloat();
    }
}
