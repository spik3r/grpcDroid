package kaitait.com.droidgrpc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@net.sf.oval.configuration.annotation.Constraint(checkWith = AsciiAlphanumericUnderscoreDashCheck.class)
public @interface AsciiAlphanumericUnderscoreDash
{
    String message() default "must be alphanumeric characters, underscores or dashes only";
}
