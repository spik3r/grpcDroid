package api.infrastructure.provider.modules;

import api.infrastructure.persistence.grpc.SignInRepositoryImpl;
import common.infrastructure.persistence.grpc.GRPCClientConnectionManager;
import dagger.Module;
import dagger.Provides;

@Module
public class GRPCClientConnectionManagerModule {
    
    @Provides
    public GRPCClientConnectionManager provideManager() {
        return new GRPCClientConnectionManager();
    }
    
    @Provides
    public SignInRepositoryImpl provideRepository(GRPCClientConnectionManager manager) {
        return new SignInRepositoryImpl(manager);
    }
}
