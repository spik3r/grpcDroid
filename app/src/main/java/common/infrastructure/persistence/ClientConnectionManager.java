package common.infrastructure.persistence;

import io.grpc.ManagedChannel;

/**
 * Created by kai-tait on 27/02/2017.
 */
public interface ClientConnectionManager {
    ManagedChannel getChannel();
    void shutdown() throws InterruptedException;
}
