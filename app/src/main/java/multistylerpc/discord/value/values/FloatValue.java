package multistylerpc.discord.value.values;

import org.json.JSONObject;

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
    public JSONObject toJson() {
        return new JSONObject().put(name, value);
    }
    @Override
    public void fromJson(JSONObject element) {
        this.value = element.getFloat(name);
    }
}
