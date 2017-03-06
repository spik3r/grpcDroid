package kaitait.com.droidgrpc;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.test.generated.LoginServiceGrpc;
import com.test.generated.RegisteredUser;
import com.test.generated.User;

import net.badata.protobuf.converter.Converter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import kaitait.com.droidgrpc.model.DomainRegisteredUser;
import kaitait.com.droidgrpc.model.DomainUser;

public class MainActivity extends AppCompatActivity
{
    private Button sendButton;
    private EditText hostEdit;
    private EditText portEdit;
    private EditText messageEdit;
    private TextView resultText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendButton = (Button) findViewById(R.id.send_button);
        hostEdit = (EditText) findViewById(R.id.host_edit_text);
        portEdit = (EditText) findViewById(R.id.port_edit_text);
        messageEdit = (EditText) findViewById(R.id.message_edit_text);
        resultText = (TextView) findViewById(R.id.grpc_response_text);
        resultText.setMovementMethod(new ScrollingMovementMethod());
    }

    public void sendMessage(View view) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(hostEdit.getWindowToken(), 0);
        sendButton.setEnabled(false);
//        new GrpcTask().execute();
        System.out.println(nonBlockingServerRequest());
    }

    private String nonBlockingServerRequest()
    {
        System.out.println("____ nonBlockingServerRequest");
        final String[] messages = {"Message: "};
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("192.168.0.83", 10183)
         ManagedChannel channel = ManagedChannelBuilder.forAddress("paul-vm.macolighting.com", 10183)
                .usePlaintext(true)
                .build();
        DomainUser user = new DomainUser("message test", "asdfghjkl");
//        User userCredentials = Converter.create().toProtobuf(User.class, user);

        LoginServiceGrpc.LoginServiceFutureStub
                asyncStub = LoginServiceGrpc.newFutureStub(channel)
                .withDeadlineAfter(5, TimeUnit.SECONDS);

//        ListenableFuture<RegisteredUser>
//                userCredentials = asyncStub.signIn(User.newBuilder().setName("Bob").setPassword
//                ("asdfghjkl").build());
        User userB = User.newBuilder().setName("BOB").setPassword("asdfghjkl").build();
//            LoginServiceGrpc.LoginServiceStub asyncStub = LoginServiceGrpc.newStub(channel);


//            final CountDownLatch finishLatch = new CountDownLatch(1);
//            StreamObserver<RegisteredUser> responseObserver = new StreamObserver<RegisteredUser>() {
//                @Override
//                public void onNext(RegisteredUser registeredUser) {
//                    System.out.println("___________onNext");
//                    String name = registeredUser.getUser().getName();
//                    String password = registeredUser.getUser().getPassword();
//                    String token = registeredUser.getToken();
//                    message[0] += "Name: " + name + "\nPassword: " + password + "\nToken: " + token;
//                }
//
//                @Override
//                public void onError(Throwable t) {
//                    System.out.println("___________onError");
//                    System.out.println(t.getMessage());
//                    System.out.println(t.getCause());
//                    message[0] += t;
////                    finishLatch.countDown();
//                    try {
//                        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                }
//
//                @Override
//                public void onCompleted() {
//                    System.out.println("___________onCompleted");
//                    message[0] += "Finished RecordRoute";
////                    finishLatch.countDown();
//                    try {
//                        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                }
//            };
//            ListenableFuture<RegisteredUser> a = asyncStub.signIn(userCredentials);

        Futures.addCallback(
                asyncStub.signIn(userB),
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
                                              resultText.setText(result.getToken());
                                          }
                                      }
                        );
                        System.out.println("____success");
                        messages[0] += result.getUser().getName();
                        System.out.println(result.getUser().getName());
                        System.out.println(result.getUser().getPassword());
                        System.out.println(result.getToken());
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
                        System.out.println("____error");
                        System.out.println(t.getCause());
                        System.out.println(t.getMessage());
                    }
                });

//            asyncStub.signIn(userCredentials, responseObserver);
        return messages[0];
    }

    private class GrpcTask extends AsyncTask<Void, Void, String>
//    private class GrpcTask extends AsyncTask<Void, Void, String>
    {
        private String host;
        private String message;
        private int port;
        private ManagedChannel channel;

        @Override
        protected void onPreExecute() {
            host = hostEdit.getText().toString();
            message = messageEdit.getText().toString();
            String portStr = portEdit.getText().toString();
            port = TextUtils.isEmpty(portStr) ? 0 : Integer.valueOf(portStr);
            resultText.setText("");
        }

        @Override
        protected String doInBackground(Void... nothing) {
//            return nonBlockingServerRequest();
            return blockingServerRequest();
        }





        @NonNull
        private String blockingServerRequest()
        {
            try {
                channel = ManagedChannelBuilder.forAddress("192.168.0.83", 10183)
//                channel = ManagedChannelBuilder.forAddress("paul-vm.macolighting.com", 10183)
                        .usePlaintext(true)
                        .build();
                DomainUser user = new DomainUser(message.toString(), "asdfghjkl");
                LoginServiceGrpc.LoginServiceBlockingStub stub = LoginServiceGrpc
                        .newBlockingStub(channel)
                        .withDeadlineAfter(5, TimeUnit.SECONDS);
                User userCredentials = Converter.create().toProtobuf(User.class, user);
                System.out.println("____userCredentials: " + userCredentials);
                RegisteredUser registeredGrpcUser = stub.signIn(userCredentials);
                String userToken = registeredGrpcUser.getToken();
                System.out.println("____token: " + userToken);
                DomainRegisteredUser
                        domainRegisteredUser = Converter.create().toDomain(DomainRegisteredUser.class, registeredGrpcUser);
                System.out.println("_____" + domainRegisteredUser.toString());
                return "token: " +userToken + "\nuser: " + domainRegisteredUser.getUser().getName
                        ()+ "\npassword: " + domainRegisteredUser.getUser().getPassword();

            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                return "Failed... : " + System.lineSeparator() + sw;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            resultText.setText(result);
            sendButton.setEnabled(true);
        }
    }
}
