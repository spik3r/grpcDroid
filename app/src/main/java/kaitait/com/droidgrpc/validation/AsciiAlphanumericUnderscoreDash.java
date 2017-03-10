package kaitait.com.droidgrpc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check if value equals contains only ASCII alphanumeric characters, the underscore U+005F and the 
 * dash U+002D.
 *
 * <br>
 * <br>
 * <b>Note:</b> This constraint is also satisfied when the value to validate is null, therefore you 
 * might also need to specified @NotNull
 *
 * @author Alex Royds
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@net.sf.oval.configuration.annotation.Constraint(checkWith = AsciiAlphanumericUnderscoreDashCheck.class)
public @interface AsciiAlphanumericUnderscoreDash
{
    String message() default "must be alphanumeric characters, underscores or dashes only";
}
