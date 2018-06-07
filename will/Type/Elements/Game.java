package will.Type.Elements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

/**
 * <h1>Game Annotation Class</h1>
 * <h2>Class for declare events for game</h2>
 *
 * @author Will
 * @version 1.0.1
 */
public @interface Game {

    boolean isStarted() default false;

}
