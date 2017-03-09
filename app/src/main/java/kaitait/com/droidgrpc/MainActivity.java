package kaitait.com.droidgrpc;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;

import javax.inject.Inject;

import api.domain.model.signIn.DomainRegisteredUser;
import api.domain.model.signIn.DomainUser;
import api.infrastructure.persistence.grpc.SignInRepositoryImpl;
import api.infrastructure.provider.component.DaggerGRPCClientConnectionManagerComponent;
import api.infrastructure.provider.component.GRPCClientConnectionManagerComponent;
import common.infrastructure.persistence.grpc.GRPCClientConnectionManager;
import io.reactivex.observers.DisposableObserver;
import kaitait.com.droidgrpc.ViewModel.User;
import kaitait.com.droidgrpc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    
    @Inject
    public GRPCClientConnectionManager manager;

    @Inject
    public SignInRepositoryImpl repository;
//
    
//    private ClientConnectionManager manager = new GRPCClientConnectionManager();
//
    private DisposableObserver observerButtonObserver;
    private final User userViewModel = new User();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userViewModel.response.set("DEFAULT");
        binding.setUser(userViewModel);
        GRPCClientConnectionManagerComponent component =
                DaggerGRPCClientConnectionManagerComponent.create();
        
        component.injectMainActivity(this);

        
        observerButtonObserver = new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                try {
                    signIn();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("____ some error happened: " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("____ completed");
            }
        };
        userViewModel.sendButton.subscribeWith(observerButtonObserver);
    }
    
    public void signIn() throws InterruptedException, InvalidProtocolBufferException {
        final DomainUser user = new DomainUser(
                this.userViewModel.name.get(),
                this.userViewModel.password.get());
//        repository = component.provideRepository();
//        SignInRepository repository = new SignInRepositoryImpl(manager);
    
        System.out.println("____repository" + repository == null);
        Log.i("____repository", String.valueOf(repository == null));
        Log.i("____manager", String.valueOf(manager == null));
        final DomainRegisteredUser registeredUser = repository.signIn(user);
        final String displayString = "Name: " + registeredUser.getUser().getName() +
                                     "\nPassword: " + registeredUser.getUser().getPassword() +
                                     "\nToken: " + registeredUser.getToken();
        userViewModel.response.set(displayString);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        observerButtonObserver.dispose();
        try {
            manager.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
