package multistylerpc.discord.style;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import multistylerpc.App;
import multistylerpc.discord.value.Value;
import multistylerpc.event.Listenable;

public class StyleModule implements Listenable {
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private final Random RANDOM = new Random();
    public String styleName = "random"+generateRandomString(6);
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
        } else {
            System.out.println("CustomAnnotation not found on "+this.getClass().getName());
        }
    }
    public List<Value<?>> getValues() {
        List<Value<?>> result = new ArrayList<>();
        Field[] declaredFields = getClass().getDeclaredFields();

        for (Field valueField : declaredFields) {
            valueField.setAccessible(true);
            try {
                Object fieldValue = valueField.get(this);
                if (fieldValue instanceof Value<?>) {
                    result.add((Value<?>) fieldValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Handle the exception according to your application's logic
            }
        }

        return result;
    }
    public Value<?> getValue(String valueName) {
        for (Value<?> value : getValues()) {
            if (value.getName().equalsIgnoreCase(valueName)) {
                return value;
            }
        }
        return null;
    }
    @Override
    public boolean handleEvents() {
        if (App.cDiscordClient.mStyleManager.selectedStyleModule == null) return false;
        return App.cDiscordClient.mStyleManager.selectedStyleModule.styleName.equals(this.styleName);
    };
}
