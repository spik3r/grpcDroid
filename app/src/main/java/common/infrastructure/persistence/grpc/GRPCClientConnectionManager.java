package common.infrastructure.persistence.grpc;

import java.util.concurrent.TimeUnit;

import common.infrastructure.persistence.ClientConnectionManager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by kai-tait on 27/02/2017.
 */

//TODO: test implementation of what the app will pass the api
//@Module
public class GRPCClientConnectionManager implements ClientConnectionManager {
    
    private ManagedChannel channel;
    
    public GRPCClientConnectionManager() {
                this.channel = ManagedChannelBuilder
                .forAddress("192.168.0.83", 10183)
                .usePlaintext(true)
                .build();
        System.out.println("____Connection created for: 192.168.0.83:10183");
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
