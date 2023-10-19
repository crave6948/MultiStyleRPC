package multistylerpc.discord.style;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
@Retention(RetentionPolicy.RUNTIME)
public @interface StyleInfo {
    String name();
    long clientID() default 1161336492774412399L;
}
