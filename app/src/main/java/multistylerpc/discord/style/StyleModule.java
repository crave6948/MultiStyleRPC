package multistylerpc.discord.style;

import java.util.Random;
import com.jagrosh.discordipc.IPCClient;
import multistylerpc.event.Listenable;

public class StyleModule implements Listenable {
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private final Random RANDOM = new Random();
    public String styleName = "random"+generateRandomString(6);
    private long clientID = -1;
    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
    
    public StyleModule() {
        StyleInfo info = getClass().getAnnotation(StyleInfo.class);
        if (info != null) {
            styleName = info.name();
            clientID = info.clientID();
        } else {
            System.out.println("CustomAnnotation not found on "+this.getClass().getName());
        }
    }
    public long getClientID() {
        return clientID;
    }
    public void onReady(IPCClient client){};
    public void onUpdate(IPCClient client){};
    public long nextSleep(){return 5000L;}

    @Override
    public boolean handleEvents() {
        return true;
    };
}
