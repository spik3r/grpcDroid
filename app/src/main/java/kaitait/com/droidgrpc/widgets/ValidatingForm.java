package kaitait.com.droidgrpc.widgets;

import net.sf.oval.ConstraintViolation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import kaitait.com.droidgrpc.functions.FormValidationFunction;
import kaitait.com.droidgrpc.functions.NewViolationCombinationFunction;



public class ValidatingForm
{
    private final Observable<Object> next_click;
    private Map<String, ValidatingField> validating_fields = new HashMap<>();
    private List<Observable<List<ConstraintViolation>>> violation_streams = new ArrayList<>();
    
    public ValidatingForm(Observable<Object> next_click)
    {
        this.next_click = next_click;
    }
    
    public ValidatingField AddValidatingField(
            Object validated_object,
            String input_field_name,
            boolean required)
    {
        ValidatingField validating_field 
                = new ValidatingField(validated_object, input_field_name, required, this.next_click);
        this.validating_fields.put(input_field_name, validating_field);
        this.violation_streams.add(validating_field.GetViolationStream());
        return validating_field;
    }
    
    public ValidatingField GetValidatingField(String field_name)
    {
        return validating_fields.get(field_name);
    }
    
    public Observable<Boolean> GetFormValidityStream()
    {
        return Observable.combineLatest(violation_streams, new NewViolationCombinationFunction())
                .map(new FormValidationFunction());
    }
}
