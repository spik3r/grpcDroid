package kaitait.com.droidgrpc;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        new GrpcTask().execute();
    }

    private class GrpcTask extends AsyncTask<Void, Void, String>
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
            try {
                channel = ManagedChannelBuilder.forAddress("0.0.0.0", 8888)
                        .usePlaintext(true)
                        .build();
                DomainUser user = new DomainUser(message, "asdfghjkl");
                LoginServiceGrpc.LoginServiceBlockingStub stub = LoginServiceGrpc
                        .newBlockingStub(channel);
                User userCredentials = Converter.create().toProtobuf(User.class, user);
                System.out.println("____userCredentials: " + userCredentials);
                RegisteredUser registeredGrpcUser = stub.signIn(userCredentials);
                String userToken = registeredGrpcUser.getToken();
                System.out.println("____token: " + userToken);
                DomainRegisteredUser domainRegisteredUser = Converter.create().toDomain(DomainRegisteredUser.class, registeredGrpcUser);
                System.out.println("_____" + domainRegisteredUser.toString());
//                User user = HelloRequest.newBuilder().setName(this.message).build();
//                DomainRegisteredUser domainRegisteredUser = stub.signIn(message);
                return "badjdfj";

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
                channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            resultText.setText(result);
            sendButton.setEnabled(true);
        }
    }
}
