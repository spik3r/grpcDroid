package kaitait.com.droidgrpc;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.jakewharton.rxbinding2.view.RxView;
import com.test.generated.LoginServiceGrpc;
import com.test.generated.RegisteredUser;
import com.test.generated.User;

import net.badata.protobuf.converter.Converter;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import kaitait.com.droidgrpc.connection.ClientConnectionManager;
import kaitait.com.droidgrpc.connection.ClientConnectionManagerImpl;
import kaitait.com.droidgrpc.databinding.ActivityMainBinding;
import kaitait.com.droidgrpc.model.DomainRegisteredUser;
import kaitait.com.droidgrpc.model.DomainUser;

public class MainActivity extends AppCompatActivity
{
    ActivityMainBinding binding;
    
    private Button sendButton;
    private EditText nameEditText;
    private EditText passwordEditText;
    private ManagedChannel channel;
    private TextView resultText;
    private Observable<Object> clicks;
    
    private Observable<Object> name;
    
    
    private DisposableObserver observerButtonObserver;
    private DisposableObserver nameFieldObserver;
    private Data data = new Data();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setData(data);
        
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        resultText = (TextView) findViewById(R.id.grpc_response_text);
        ClientConnectionManager manager = new ClientConnectionManagerImpl();
        channel = manager.getChannel();
        sendButton = (Button) findViewById(R.id.send_button);
        
        clicks = RxView.clicks(sendButton);
        observerButtonObserver = new DisposableObserver()
        {
            @Override
            public void onNext(Object o)
            {
                System.out.println("____ disposable observerButtonObserver rxButton clicked");
                sendGRPC();
            }
    
            @Override
            public void onError(Throwable e)
            {
                System.out.println("____ some error happened: " + e);
            }
    
            @Override
            public void onComplete()
            {
                System.out.println("____ completed");
            }
        };
    
        clicks.subscribeWith(observerButtonObserver);
        
    }
    
    private void sendGRPC() {
        DomainUser user = new DomainUser(data.getName(), data.getPassword());
    
        User userCredentials = Converter.create().toProtobuf(User.class, user);
        LoginServiceGrpc.LoginServiceFutureStub
                asyncStub = LoginServiceGrpc.newFutureStub(channel)
                .withDeadlineAfter(5, TimeUnit.SECONDS);
    
        Futures.addCallback(
                asyncStub.signIn(userCredentials),
                new FutureCallback<RegisteredUser>()
                {
                    @Override
                    public void onSuccess(final RegisteredUser result)
                    {
                        runOnUiThread(new Runnable()
                                      {
                                          @Override
                                          public void run()
                                          {
                                              DomainRegisteredUser
                                                      domainRegisteredUser = Converter
                                                      .create().toDomain(
                                                              DomainRegisteredUser.class,
                                                              result);
                                              final String displayString = "Name: " +
                                                                           domainRegisteredUser.getUser()
                                                                                   .getName() +
                                                                           "\nPassword: " +
                                                                           domainRegisteredUser.getUser()
                                                                                   .getPassword()
                                                                           + "\nToken: " +
                                                                           domainRegisteredUser
                                                                                   .getToken();
                                              resultText.setText(displayString);
                                          }
                                      }
                        );
                    }
                
                    @Override
                    public void onFailure(final Throwable t)
                    {
                        runOnUiThread(new Runnable()
                                      {
                                          @Override
                                          public void run()
                                          {
                                              resultText.setText(t.getMessage());
                                          }
                                      }
                        );
                    }
                });
        
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        observerButtonObserver.dispose();
    }
}
