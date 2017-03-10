package kaitait.com.droidgrpc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check if value equals contains only unicode characters in the range U+0020 to U+007E inclusive.
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
@net.sf.oval.configuration.annotation.Constraint(checkWith = UnicodeRangeCheck.class)
public @interface UnicodeRange
{
    String message() default "invalid characters";
}
