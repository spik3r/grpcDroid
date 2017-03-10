package kaitait.com.droidgrpc.functions;

import net.sf.oval.ConstraintViolation;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import kaitait.com.droidgrpc.ConductorApplication;
import kaitait.com.droidgrpc.functions.enums.SpecialFunctionReturns;
import kaitait.com.droidgrpc.utils.ValidationErrorParser;

/**
 * @author Alex Royds
 */

public class ReadableErrorFunction implements Function<List<ConstraintViolation>, Object>
{
    @Inject
    ValidationErrorParser validation_error_parser;
    
    public ReadableErrorFunction()
    {
        ConductorApplication.GetUiFieldComponent().Inject(this);
    }
    
    @Override
    public Object apply(List<ConstraintViolation> violations) throws Exception
    {
        if (violations.size() == 0)
        {
            return SpecialFunctionReturns.NO_ERROR;
        }
        else
        {
            return validation_error_parser.ParseOvalError(violations.get(0));
        }
    }
}
