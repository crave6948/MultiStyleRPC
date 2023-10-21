package multistylerpc.discord.value.values;

import org.json.JSONObject;

import multistylerpc.discord.value.Value;

public class LongValue extends Value<Long>{
    private final long min;
    private final long max;
    public LongValue(String name,long value) {this(name, value,0L,Long.MAX_VALUE);}
    public LongValue(String name,long value,long min, long max) {
        super(name, value);
        this.min = min;
        this.max = max;
    }
    public long getMin() {
        return min;
    }
    public long getMax() {
        return max;
    }
    @Override
    public JSONObject toJson() {
        return new JSONObject().put(name, value);
    }
    @Override
    public void fromJson(JSONObject element) {
        this.value = element.getLong(name);
    }
}
