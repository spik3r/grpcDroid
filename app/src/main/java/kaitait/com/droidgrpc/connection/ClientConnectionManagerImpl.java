package kaitait.com.droidgrpc.connection;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ClientConnectionManagerImpl implements ClientConnectionManager {

    private ManagedChannel channel;

    public ClientConnectionManagerImpl() {
                channel = ManagedChannelBuilder
                .forAddress("192.168.0.83", 10183)
//                .forAddress("paul-vm.macolighting.com", 10183)
                .usePlaintext(true)
                .build();
    }
    public ManagedChannel getChannel() {
        return channel;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
