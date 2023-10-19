package multistylerpc.discord.style.styles;

import java.time.OffsetDateTime;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.RichPresence;
import multistylerpc.discord.style.StyleInfo;
import multistylerpc.discord.style.StyleModule;

@StyleInfo(name ="Default", clientID = 1161336492774412399L) 
public class DefaultStyle extends StyleModule{
    private int count = 0;
    private long RepeatEvery = 5000L;
    @Override
    public void onReady(IPCClient client) {
        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setState("West of House")
                // .setDetails("Frustration level: Over 9000")
                // .setStartTimestamp(OffsetDateTime.now())
                .setLargeImage("cat", "Discord Canary")
                .setSmallImage("cat", "Discord PTB");
        RichPresence presence = builder.build();
        client.sendRichPresence(presence);
    }

    @Override
    public void onUpdate(IPCClient client) {
        count++;
        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setState("count -> " + count)
                .setDetails("RepeatEvery -> " + RepeatEvery)
                .setStartTimestamp(OffsetDateTime.now())
                .setLargeImage("cat", "Style - "+this.styleName);
        RichPresence presence = builder.build();
        client.sendRichPresence(presence);
    }

    @Override
    public long nextSleep() {
        return RepeatEvery;
    }
    
}
