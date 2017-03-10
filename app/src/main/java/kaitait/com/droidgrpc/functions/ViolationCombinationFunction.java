package kaitait.com.droidgrpc.functions;

import net.sf.oval.ConstraintViolation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.BiFunction;

/**
 * @author Alex Royds
 */

public class ViolationCombinationFunction implements BiFunction<
        List<ConstraintViolation>,
        List<ConstraintViolation>,
        List<ConstraintViolation>>
{
    
    @Override
    public List<ConstraintViolation> apply(
            List<ConstraintViolation> violations_1,
            List<ConstraintViolation> violations_2) throws Exception
    {
        List<ConstraintViolation> combined_violations = new ArrayList<>();
        combined_violations.addAll(violations_1);
        combined_violations.addAll(violations_2);
        return combined_violations;
    }
}
