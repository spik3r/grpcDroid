package kaitait.com.droidgrpc;

import android.app.Application;

import api.infrastructure.provider.component.AppComponent;
import api.infrastructure.provider.component.DaggerAppComponent;
import api.infrastructure.provider.component.DaggerUiFieldComponent;
import api.infrastructure.provider.component.UiFieldComponent;
import api.infrastructure.provider.modules.AppModule;
import api.infrastructure.provider.modules.ValidationModule;

public class MainApplication extends Application {
    static AppComponent app_component;
    static UiFieldComponent ui_field_component;

    @Override
    public void onCreate() {
        super.onCreate();
        app_component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        ui_field_component = DaggerUiFieldComponent
                .builder()
                .appComponent(app_component)
                .validationModule(new ValidationModule(this))
                .build();
    }

    public static UiFieldComponent GetUiFieldComponent() {
        return ui_field_component;
    }
}
