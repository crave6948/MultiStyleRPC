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
import multistylerpc.event.events.ReadyEvent;
import multistylerpc.event.events.RepeatEvent;
import multistylerpc.event.events.UpdateEvent;

@StyleInfo(name ="Default") 
public class DefaultStyle extends StyleModule{
    private int count = 0;
    private LongValue clientID = new LongValue("ClientID", 1161336492774412399L);
    private IntegerValue RepeatEvery = new IntegerValue("Repeat Every", 10000, 0, 50000);
    private TextValue state = new TextValue("State","Hello world!");

    @EventTarget
    public void onClient(IDEvent event) {
        event.setId(clientID.get());
    }
    @EventTarget
    public void onReady(ReadyEvent event) {
        IPCClient client = event.getClient();
        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setState("West of House")
                // .setDetails("Frustration level: Over 9000")
                // .setStartTimestamp(OffsetDateTime.now())
                .setLargeImage("cat", "Discord Canary")
                .setSmallImage("cat", "Discord PTB");
        RichPresence presence = builder.build();
        client.sendRichPresence(presence);
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        IPCClient client = event.getClient();
        count++;
        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setState(state.get() + " || count -> " + count)
                .setDetails("RepeatEvery -> " + RepeatEvery.get())
                .setStartTimestamp(OffsetDateTime.now())
                .setLargeImage("cat", "Style - "+this.styleName);
        RichPresence presence = builder.build();
        client.sendRichPresence(presence);
    }

    @EventTarget
    public void nextSleep(RepeatEvent event) {
        event.setRepeatEvery(RepeatEvery.get().longValue());
    }
    
}
