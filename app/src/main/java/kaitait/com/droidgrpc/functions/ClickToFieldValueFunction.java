package kaitait.com.droidgrpc.functions;

import android.databinding.ObservableField;

import io.reactivex.functions.Function;

public class ClickToFieldValueFunction implements Function<Object, String> {
    private final ObservableField<String> observable_field;

    public ClickToFieldValueFunction(ObservableField<String> observable_field) {
        this.observable_field = observable_field;
    }

    @Override
    public String apply(Object object) throws Exception {
        return observable_field.get() == null ? "" : observable_field.get();
    }
}
