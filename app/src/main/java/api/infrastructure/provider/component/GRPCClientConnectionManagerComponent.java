package api.infrastructure.provider.component;

import api.infrastructure.provider.modules.GRPCClientConnectionManagerModule;
import dagger.Component;
import kaitait.com.droidgrpc.MainActivity;

/**
 * Created by kai-tait on 8/03/2017.
 */

@Component(modules = {GRPCClientConnectionManagerModule.class})
public interface GRPCClientConnectionManagerComponent {
//    GRPCClientConnectionManager provideManager();
//    SignInRepositoryImpl provideRepository();
    
    void injectMainActivity(MainActivity mainActivity);
}
