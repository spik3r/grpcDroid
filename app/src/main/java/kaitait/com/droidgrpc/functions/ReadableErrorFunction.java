package kaitait.com.droidgrpc.functions;

import net.sf.oval.ConstraintViolation;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import kaitait.com.droidgrpc.MainApplication;
import kaitait.com.droidgrpc.functions.enums.SpecialFunctionReturns;
import kaitait.com.droidgrpc.utils.ValidationErrorParser;

public class ReadableErrorFunction implements Function<List<ConstraintViolation>, Object> {
    @Inject
    ValidationErrorParser validation_error_parser;

    public ReadableErrorFunction() {
        MainApplication.GetUiFieldComponent().Inject(this);
    }

    @Override
    public Object apply(List<ConstraintViolation> violations) throws Exception {
        if (violations.size() == 0) {
            return SpecialFunctionReturns.NO_ERROR;
        } else {
            return validation_error_parser.parseOvalError(violations.get(0));
        }
    }
}
