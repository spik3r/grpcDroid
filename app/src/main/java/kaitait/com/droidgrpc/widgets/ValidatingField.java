package kaitait.com.droidgrpc.widgets;

import android.content.Context;
import android.databinding.ObservableField;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import kaitait.com.droidgrpc.MainApplication;
import kaitait.com.droidgrpc.functions.ClickToFieldValueFunction;
import kaitait.com.droidgrpc.functions.FieldValidationFunction;
import kaitait.com.droidgrpc.functions.ReadableErrorFunction;
import kaitait.com.droidgrpc.functions.StringTrimmingFunction;
import kaitait.com.droidgrpc.utils.ObservableConverter;
import kaitait.com.droidgrpc.utils.ValidatableField;

public class ValidatingField {
    @Inject
    Validator validator;
    @Inject
    Context app_context;

    private final Object validated_object;
    private String input_field_name;

    private ValidatableField<String> validatable_field;
    private Observable<String> click_value_stream;
    private Observable<String> value_stream;
    private Observable<String> debounced_value_stream;
    private Observable<List<ConstraintViolation>> violation_stream;
    private ObservableField<Object> error_observable_field;
    private boolean required;
    private final Observable<Object> next_click;

    ValidatingField(
            Object validated_object,
            final String input_field_name,
            boolean required,
            Observable<Object> next_click) {

        MainApplication.GetUiFieldComponent().Inject(this);
        this.validated_object = validated_object;
        this.input_field_name = input_field_name;
        this.required = required;
        this.next_click = next_click;
    }

    public ObservableField<String> GetValidatableField() {
        if (this.validatable_field == null) {
            this.validatable_field = new ValidatableField<>();
        }
        return this.validatable_field;
    }

    private Observable<String> GetClickTriggeredValueStream() {
        if (this.click_value_stream == null) {
            this.click_value_stream = this.next_click
                    .map(new ClickToFieldValueFunction(GetValidatableField()));
        }
        return this.click_value_stream;
    }

    private Observable<String> GetValueStream() {
        if (this.value_stream == null) {
            this.value_stream = ObservableConverter.toObservable(GetValidatableField())
                    .distinctUntilChanged()
                    .map(new StringTrimmingFunction());
        }
        return this.value_stream;
    }

    private Observable<String> GetDebouncedValueStream() {
        if (this.debounced_value_stream == null) {
            this.debounced_value_stream = this.GetValueStream()
                    .debounce(500, TimeUnit.MILLISECONDS);
            if (this.required) {
                this.debounced_value_stream = this.debounced_value_stream
                        .mergeWith(this.GetClickTriggeredValueStream());
            }
        }
        return this.debounced_value_stream;
    }

    public Observable<List<ConstraintViolation>> GetViolationStream() {
        if (this.violation_stream == null) {
            this.violation_stream = this.GetDebouncedValueStream()
                    .map(new FieldValidationFunction(
                            this.validator,
                            this.validated_object,
                            input_field_name,
                            GetValidatableField()));
        }
        return this.violation_stream;
    }

    public ObservableField<Object> GetErrorObservableField() {
        if (this.error_observable_field == null) {
            Observable<Object> readable_error = this.GetViolationStream()
                    .map(new ReadableErrorFunction());
            this.error_observable_field = ObservableConverter.toField(readable_error);
        }
        return this.error_observable_field;
    }
}
