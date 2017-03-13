package api.infrastructure.provider.component;

import api.infrastructure.provider.modules.ValidationModule;
import dagger.Component;
import kaitait.com.droidgrpc.functions.ReadableErrorFunction;
import kaitait.com.droidgrpc.widgets.ValidatingField;


@ActivityScope
@Component(dependencies = {AppComponent.class},
        modules = {ValidationModule.class})
public interface UiFieldComponent
{
    void Inject(ValidatingField validating_field);
    void Inject(ReadableErrorFunction readable_error_function);
}
