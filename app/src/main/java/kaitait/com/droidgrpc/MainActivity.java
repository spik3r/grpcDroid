package kaitait.com.droidgrpc;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import api.domain.model.signIn.DomainRegisteredUser;
import api.domain.model.signIn.DomainUser;
import api.infrastructure.persistence.grpc.SignInRepositoryImpl;
import api.infrastructure.provider.component.DaggerGRPCClientConnectionManagerComponent;
import api.infrastructure.provider.component.GRPCClientConnectionManagerComponent;
import common.infrastructure.persistence.grpc.GRPCClientConnectionManager;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import kaitait.com.droidgrpc.ViewModel.User;
import kaitait.com.droidgrpc.databinding.ActivityMainBinding;
import kaitait.com.droidgrpc.utils.DisposableUIObserver;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Integer retryCount = 0;
    private final Integer MAX_RETIRES = 3;

    @Inject
    public GRPCClientConnectionManager manager;

    @Inject
    public SignInRepositoryImpl repository;

    private DisposableObserver observerButtonObserver;
    private User userViewModel = new User();

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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InvalidProtocolBufferException e) {
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
        SetObserverForLoginClick();
    }

    public void RegisterObserverForDisposal(Observable observable, DisposableObserver observer) {
        CompositeDisposable composite_disposable = new CompositeDisposable();
        composite_disposable.add((Disposable) observable.subscribeWith(observer));
    }

    private void SetObserverForLoginClick() {
        this.RegisterObserverForDisposal(
                this.userViewModel.login_click_validity,
                new DisposableUIObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean login_clicked_form_valid) {
                        if (login_clicked_form_valid) {
                            System.out.println("login clicked on valid form");
                            try {
                                signIn();
                            } catch (InterruptedException | InvalidProtocolBufferException e) {
                                Log.e("Validation Exception", e.getMessage());
                            }
                        }
                    }
                });
    }

    private DomainUser createUser() {
        final DomainUser user = new DomainUser(
                this.userViewModel.name.get(),
                this.userViewModel.password.get());
        return user;
    }

    public void signIn() throws InterruptedException, InvalidProtocolBufferException {

        DomainUser user = createUser();

        if (user != null) {
            final DomainRegisteredUser registeredUser = getDomainRegisteredUser(user);
            final String displayString = "Name: " + registeredUser.getUser().getName() +
                    "\nPassword: " + registeredUser.getUser().getPassword() +
                    "\nToken: " + registeredUser.getToken();
            userViewModel.response.set(displayString);
        }
    }

    private DomainRegisteredUser getDomainRegisteredUser(final DomainUser user)
            throws InvalidProtocolBufferException {
        DomainRegisteredUser registeredUser = null;
        if (retryCount < MAX_RETIRES) {
            try {
                registeredUser = repository.signIn(user);
            } catch (ExecutionException | InterruptedException e) {
                Log.e("Stub exception", e.getMessage());
                retryCount++;
                getDomainRegisteredUser(user);
            }
        }
        return registeredUser;
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
