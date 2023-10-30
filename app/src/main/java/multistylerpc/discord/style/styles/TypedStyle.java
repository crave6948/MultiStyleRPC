package multistylerpc.discord.style.styles;

import java.time.OffsetDateTime;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.RichPresence;

import multistylerpc.discord.style.StyleInfo;
import multistylerpc.discord.style.StyleModule;
import multistylerpc.discord.value.values.IntegerValue;
import multistylerpc.discord.value.values.LongValue;
import multistylerpc.discord.value.values.TextValue;
import multistylerpc.event.EventTarget;
import multistylerpc.event.events.IDEvent;
import multistylerpc.event.events.RepeatEvent;
import multistylerpc.event.events.UpdateEvent;
@StyleInfo(name ="Typed") 
public class TypedStyle extends StyleModule{
    private LongValue clientID = new LongValue("ClientID", 1161336492774412399L);
    private IntegerValue RepeatEvery = new IntegerValue("Repeat Every", 3000, 0, 50000);
    private TextValue detailsValue = new TextValue("Details","Hello world!");
    private TextValue stateValue = new TextValue("State","Good Afternoon world!");
    private TextValue prefixValue = new TextValue("Prefix","-_-");
    private int currentPercent = 0;
    private boolean up = true;
    private OffsetDateTime time = OffsetDateTime.now();
    @EventTarget
    public void onClient(IDEvent event) {
        event.setId(clientID.get());
        time = OffsetDateTime.now();
    }
    private String insert(String originalString, int insertIndex) {
        if (insertIndex >= 0 && insertIndex <= originalString.length()) {
            return originalString.substring(0, insertIndex) + prefixValue.get() + originalString.substring(insertIndex);
        } else {
            System.out.println("Invalid insert position");
        }
        return "Invalid insert position";
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        currentPercent += up ? 10 : -10;
        if (currentPercent >= 100) {
            up = false;
        } else if (currentPercent <= 0) {
            up = true;
        }
        IPCClient client = event.getClient();
        RichPresence.Builder builder = new RichPresence.Builder();
        int stateindex = Math.round((currentPercent / 100f) * stateValue.get().length());
        int detailsindex = Math.round((currentPercent / 100f) * detailsValue.get().length());
        builder.setState(insert(stateValue.get(), stateindex))
               .setDetails(insert(detailsValue.get(), detailsindex))
               .setStartTimestamp(time)
               .setLargeImage("cat", "Style - " + this.styleName);
        RichPresence presence = builder.build();
        client.sendRichPresence(presence);
    }

    @EventTarget
    public void nextSleep(RepeatEvent event) {
        event.setRepeatEvery(RepeatEvery.get().longValue());
    }
}
