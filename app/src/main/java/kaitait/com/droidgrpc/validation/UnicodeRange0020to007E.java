package kaitait.com.droidgrpc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@net.sf.oval.configuration.annotation.Constraint(checkWith = UnicodeRange0020to007ECheck.class)
public @interface UnicodeRange0020to007E
{
    String message() default "invalid characters";
}
