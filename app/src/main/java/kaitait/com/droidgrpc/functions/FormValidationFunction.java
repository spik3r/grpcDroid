package kaitait.com.droidgrpc.functions;

import net.sf.oval.ConstraintViolation;

import java.util.List;

import io.reactivex.functions.Function;

public class FormValidationFunction implements Function<List<ConstraintViolation>, Boolean> {
    @Override
    public Boolean apply(List<ConstraintViolation> violations) throws Exception {
        return violations.size() == 0;
    }
}
