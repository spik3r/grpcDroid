package kaitait.com.droidgrpc;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.protobuf.InvalidProtocolBufferException;
import com.jakewharton.rxbinding2.view.RxView;

import api.domain.model.signIn.DomainRegisteredUser;
import api.domain.model.signIn.DomainUser;
import api.domain.model.signIn.SignInRepository;
import api.infrastructure.persistence.grpc.SignInRepositoryImpl;
import common.infrastructure.persistence.ClientConnectionManager;
import common.infrastructure.persistence.grpc.GRPCClientConnectionManager;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import kaitait.com.droidgrpc.ViewModel.User;
import kaitait.com.droidgrpc.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final ClientConnectionManager manager = new GRPCClientConnectionManager();
    private Button sendButton;
    private Observable<Object> clicks;
    private DisposableObserver observerButtonObserver;
    private final User userViewModel = new User();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userViewModel.response.set("DEFAULT");
        binding.setUser(userViewModel);
        sendButton = (Button) findViewById(R.id.send_button);
        clicks = RxView.clicks(sendButton);
        
        observerButtonObserver = new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                try {
                    sendGRPC();
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
    
        clicks.subscribeWith(observerButtonObserver);
    }
    
    private void sendGRPC() throws InterruptedException, InvalidProtocolBufferException {
        final DomainUser user = new DomainUser(this.userViewModel.name.get(), this.userViewModel
                .password.get());
        SignInRepository repository = new SignInRepositoryImpl(manager);
        
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
