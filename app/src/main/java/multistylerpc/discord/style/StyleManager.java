package multistylerpc.discord.style;
import java.util.ArrayList;
import multistylerpc.App;
import multistylerpc.discord.style.styles.DefaultStyle;
public class StyleManager {
    private ArrayList<StyleModule> styleModules = new ArrayList<StyleModule>();
    private StyleModule selectedStyleModule = null;
    private DefaultStyle defaultStyle = new DefaultStyle();
    public StyleManager() {
        styleModules.add(defaultStyle);
        setStyle(defaultStyle);
    };
    public void setStyle(int index) {
        if (selectedStyleModule != null) App.eventManager.unregisterListener(selectedStyleModule);
        selectedStyleModule = styleModules.get(index);
        App.eventManager.registerListener(selectedStyleModule);
    }
    public void setStyle(StyleModule style) {
        if (selectedStyleModule != null) App.eventManager.unregisterListener(selectedStyleModule);
        selectedStyleModule = style;
        App.eventManager.registerListener(style);
    }
}
