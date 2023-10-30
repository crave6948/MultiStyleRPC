package multistylerpc.discord.style;
import java.util.ArrayList;
import multistylerpc.App;
import multistylerpc.discord.style.styles.DefaultStyle;
import multistylerpc.discord.style.styles.TypedStyle;
public class StyleManager {
    private ArrayList<StyleModule> styleModules = new ArrayList<>();
    public StyleModule selectedStyleModule = null;
    private DefaultStyle defaultStyle = new DefaultStyle();
    private TypedStyle typedStyle = new TypedStyle();
    public StyleManager() {
        styleModules.add(defaultStyle);
        styleModules.add(typedStyle);
        setStyle(typedStyle);
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

    public StyleModule getModule(String name) {
        for (StyleModule m : styleModules) {
            if (m.styleName.equals(name)) {
                return m;
            }
        }
        return null;
        // List<StyleModule> a = styleModules.stream().filter(it -> it.styleName == name).collect(Collectors.toList());
        // return a.isEmpty() ? null : a.get(0);
    }
    public ArrayList<StyleModule> getModules() {
        return styleModules;
    }
}
