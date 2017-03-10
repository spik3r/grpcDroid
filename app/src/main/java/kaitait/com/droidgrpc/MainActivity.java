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
import io.reactivex.observers.DisposableObserver;
import kaitait.com.droidgrpc.ViewModel.User;
import kaitait.com.droidgrpc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Integer retryCount = 0;
    private final Integer MAX_RETIRES = 3;
    
    @Inject
    public GRPCClientConnectionManager manager;
    
    @Inject
    public SignInRepositoryImpl repository;
    

    private DisposableObserver observerButtonObserver;
//    private DisposableObserver nameEditTextObserver;
//    private DisposableObserver passwordEditTextObserver;
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
    
        /*nameEditTextObserver = new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                    Log.i("onNext, name", o.toString());
            }
        
            @Override
            public void onError(Throwable e) {
                Log.e("onError", e.getMessage());
            }
        
            @Override
            public void onComplete() {
                Log.i("onComplete", "onComplete");
            }
        };
       
        Observable<String> obs = ObservableHelper.ToObservable(userViewModel.name);
        obs.subscribeWith(nameEditTextObserver);
    
        passwordEditTextObserver = new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                Log.i("onNext, password", o.toString());
            }
        
            @Override
            public void onError(Throwable e) {
                Log.e("onError", e.getMessage());
            }
        
            @Override
            public void onComplete() {
                Log.i("onComplete", "onComplete");
            }
        };
        Observable<String> passwordObserver = ObservableHelper.ToObservable(userViewModel.password);
        passwordObserver.subscribeWith(passwordEditTextObserver);*/
        
    }
    
    private DomainUser createUser()
            //throws ValidationFailedException, InvalidPropertiesFormatException
    {
//        Validator validator = new Validator();
        final DomainUser user = new DomainUser(
                this.userViewModel.name.get(),
                this.userViewModel.password.get());
        
//        List<ConstraintViolation> violations = validator.validate(user);
//        for (ConstraintViolation violation: violations) {
//
//
//            Log.i("violation msg", violation.getMessage());
//            if (violation.getMessage().contains("user"))
//            {
//                Log.i("Validation error", "user");
//                throw new ValidationFailedException("Validation Failed");
//            }
//            if (violation.getMessage().contains("password"))
//            {
//                Log.i("Validation error", "password");
//                throw new InvalidPropertiesFormatException("Validation Failed");
//            }
//
//        }
        return user;
    }
    
    public void signIn() throws InterruptedException, InvalidProtocolBufferException {
    
        DomainUser user = createUser();
//        DomainUser user = null;
//        try {
//            user = createUser();
//        } catch (ValidationFailedException e) {
//            EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
//            nameEditText.setError("Invalid name");
//        }
//        catch (InvalidPropertiesFormatException e)
//        {
//            EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);
//            passwordEditText.setError("Password is too short");
//        }
    
        if (user != null) {
            final DomainRegisteredUser registeredUser = getDomainRegisteredUser(user);
            final String displayString = "Name: " + registeredUser.getUser().getName() +
                                         "\nPassword: " + registeredUser.getUser().getPassword() +
                                         "\nToken: " + registeredUser.getToken();
            userViewModel.response.set(displayString);
        }
    }
    
    private DomainRegisteredUser getDomainRegisteredUser(final DomainUser user)
            throws InvalidProtocolBufferException
    {
        DomainRegisteredUser registeredUser = null;
        if (retryCount < MAX_RETIRES) {
            try {
                registeredUser = repository.signIn(user);
            }
            catch (ExecutionException | InterruptedException e) {
                Log.e("Stub exception", e.getMessage());
                retryCount ++;
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
