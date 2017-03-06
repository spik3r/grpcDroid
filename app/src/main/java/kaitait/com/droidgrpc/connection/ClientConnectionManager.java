package kaitait.com.droidgrpc.connection;

import io.grpc.ManagedChannel;

public interface ClientConnectionManager {
    public ManagedChannel getChannel();
}
