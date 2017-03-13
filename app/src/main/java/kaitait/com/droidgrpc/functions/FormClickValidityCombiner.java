package kaitait.com.droidgrpc.functions;

import io.reactivex.functions.BiFunction;

public class FormClickValidityCombiner implements BiFunction<Object, Boolean, Boolean> {
    @Override
    public Boolean apply(Object input_object, Boolean input_boolean) throws Exception {
        return input_boolean;
    }
}
