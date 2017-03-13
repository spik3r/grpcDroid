package kaitait.com.droidgrpc.validation;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.OValException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UnicodeRange0020to007ECheck extends 
        AbstractAnnotationCheck<UnicodeRange0020to007E>
{
    private static Pattern alphanumeric_pattern = Pattern.compile("^[ -~]*$");
    
    @Override
    public boolean isSatisfied(
            Object validated_object,
            Object value_to_validate,
            OValContext context,
            Validator validator) throws OValException
    {
        if (value_to_validate == null) return true;
        
        Matcher matcher = alphanumeric_pattern.matcher(value_to_validate.toString());
        return matcher.matches();
    }
}
