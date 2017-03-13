package common.infrastructure.persistence;

import io.grpc.ManagedChannel;

public interface ClientConnectionManager {
    ManagedChannel getChannel();
    void shutdown() throws InterruptedException;
}
