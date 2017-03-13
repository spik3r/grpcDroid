package kaitait.com.droidgrpc.functions;

import net.sf.oval.ConstraintViolation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;


public class NewViolationCombinationFunction implements Function<Object[], List<ConstraintViolation>>
{
    @Override
    public List<ConstraintViolation> apply(Object[] lists) throws Exception
    {
        List<ConstraintViolation> combined_violations = new ArrayList<>();
        for (Object violation_list : lists)
        {
            if (violation_list instanceof List<?>)
            {
                combined_violations.addAll((List<ConstraintViolation>) violation_list);
            }
        }
        return combined_violations;
    }
}
