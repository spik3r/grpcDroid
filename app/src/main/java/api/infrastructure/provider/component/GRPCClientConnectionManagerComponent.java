package api.infrastructure.provider.component;

import api.infrastructure.provider.modules.GRPCClientConnectionManagerModule;
import dagger.Component;
import kaitait.com.droidgrpc.MainActivity;

@Component(modules = {GRPCClientConnectionManagerModule.class})
public interface GRPCClientConnectionManagerComponent {
    void injectMainActivity(MainActivity mainActivity);
}
