package common.infrastructure.persistence.grpc;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import common.infrastructure.persistence.ClientConnectionManager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GRPCClientConnectionManager implements ClientConnectionManager {

    private ManagedChannel channel;

    public GRPCClientConnectionManager() {
        this.channel = ManagedChannelBuilder
                .forAddress("192.168.0.83", 10183)
                .usePlaintext(true)
                .build();
        Log.i("Connection created", "for: 192.168.0.83:10183");
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
