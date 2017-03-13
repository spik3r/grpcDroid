package kaitait.com.droidgrpc.functions;

import android.databinding.ObservableField;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.util.List;

import io.reactivex.functions.Function;

public class FieldValidationFunction implements Function<String, List<ConstraintViolation>> {
    private final Validator validator;
    private Object validated_object;
    private String field_name;
    private ObservableField<String> field_to_validate;

    public FieldValidationFunction(
            Validator validator,
            Object validated_object,
            String field_name,
            ObservableField<String> field_to_validate) {
        this.validator = validator;
        this.validated_object = validated_object;
        this.field_name = field_name;
        this.field_to_validate = field_to_validate;
    }

    @Override
    public List<ConstraintViolation> apply(String string) throws NoSuchFieldException {
        return validator.validateFieldValue(
                validated_object,
                validated_object.getClass().getField(field_name),
                field_to_validate);
    }
}
