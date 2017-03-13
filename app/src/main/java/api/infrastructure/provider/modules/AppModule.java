package api.infrastructure.provider.modules;

import android.content.Context;
import android.view.LayoutInflater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context app_context;

    public AppModule(Context app_context) {
        this.app_context = app_context;
    }

    @Provides
    @Singleton
    Context ProvidesAppContext() {
        return this.app_context;
    }

    @Provides
    @Singleton
    LayoutInflater ProvidesLayoutInflater() {
        return (LayoutInflater) app_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
