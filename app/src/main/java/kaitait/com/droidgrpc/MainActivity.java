package kaitait.com.droidgrpc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.test.generated.LoginServiceGrpc;
import com.test.generated.RegisteredUser;
import com.test.generated.User;

import net.badata.protobuf.converter.Converter;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import kaitait.com.droidgrpc.connection.ClientConnectionManager;
import kaitait.com.droidgrpc.connection.ClientConnectionManagerImpl;
import kaitait.com.droidgrpc.model.DomainRegisteredUser;
import kaitait.com.droidgrpc.model.DomainUser;

public class MainActivity extends AppCompatActivity
{
    private Button sendButton;
    private EditText nameEditText;
    private EditText passwordEditText;
    private ManagedChannel channel;
    private TextView resultText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        resultText = (TextView) findViewById(R.id.grpc_response_text);
        ClientConnectionManager manager = new ClientConnectionManagerImpl();
        channel = manager.getChannel();
        
//        channel = ManagedChannelBuilder.forAddress("192.168.0.83", 10183)
////        channel = ManagedChannelBuilder.forAddress("192.168.0.49", 10183)
//                .usePlaintext(true)
//                .build();
        
        sendButton = (Button) findViewById(R.id.send_button);
        
        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                final User userCredentials = User.newBuilder().setName(nameEditText.getText()
// .toString()).setPassword
//                        (passwordEditText.getText().toString()).build();
                DomainUser user = new DomainUser(
                        nameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                
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
        });
    }
}
