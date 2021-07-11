package academy.learn.programming.annotations;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
public @interface ServiceManQualifier {

    ServiceManQualifier.TYPE value();

    public enum TYPE {
        POLICE, SOLDIER;
    }
}
