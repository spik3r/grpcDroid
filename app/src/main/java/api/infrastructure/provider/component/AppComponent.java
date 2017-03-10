package api.infrastructure.provider.component;

import android.content.Context;
import javax.inject.Singleton;

import api.infrastructure.provider.modules.AppModule;
import dagger.Component;
import kaitait.com.droidgrpc.utils.ValidationErrorParser;

/**
 * @author Alex Royds
 */

@Singleton
@Component (modules = {AppModule.class})
public interface AppComponent
{
    Context ProvidesAppContext();
    
    void Inject(ValidationErrorParser validation_error_parser);
}
