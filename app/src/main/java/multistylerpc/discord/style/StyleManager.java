package multistylerpc.discord.style;
import java.util.ArrayList;

import com.jagrosh.discordipc.IPCClient;

import multistylerpc.discord.style.styles.DefaultStyle;
public class StyleManager {
    private ArrayList<StyleModule> styleModules = new ArrayList<StyleModule>();
    private StyleModule selectedStyleModule = null;
    private DefaultStyle defaultStyle = new DefaultStyle();
    public StyleManager() {
        styleModules.add(defaultStyle);
        setStyle(defaultStyle);
    };
    public long getSelectedID() throws NullPointerException{
        return selectedStyleModule.getClientID();
    }
    public void onReady(IPCClient client) throws NullPointerException {
        selectedStyleModule.onReady(client);
    }
    public void onUpdate(IPCClient client) throws NullPointerException{
        selectedStyleModule.onUpdate(client);
    }
    public void setStyle(int index) {
        selectedStyleModule = styleModules.get(index);
    }
    public void setStyle(StyleModule style) {
        selectedStyleModule = style;
    }
    public long nextSleepTime() throws NullPointerException {
        return selectedStyleModule.nextSleep();
    }
}
