package api.infrastructure.provider.modules;

import android.content.Context;

import net.sf.oval.Validator;

import api.infrastructure.provider.component.ActivityScope;
import dagger.Module;
import dagger.Provides;
import kaitait.com.droidgrpc.utils.ValidationErrorParser;


@Module
public class ValidationModule
{
    private final Context app_context;
    
    public ValidationModule(Context app_context)
    {
        this.app_context = app_context;
    }
    
    @Provides
    @ActivityScope
    Validator ProvidesValidator()
    {
        return new Validator();
    }
    
    @Provides
    @ActivityScope
    ValidationErrorParser ProvidesValidationErrorParser()
    {
        return new ValidationErrorParser(app_context);
    }
}
