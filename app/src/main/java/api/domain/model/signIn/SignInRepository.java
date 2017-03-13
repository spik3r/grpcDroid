package api.domain.model.signIn;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.concurrent.ExecutionException;

public interface SignInRepository {
    DomainRegisteredUser signIn(DomainUser domainUser)
            throws InterruptedException, InvalidProtocolBufferException, ExecutionException;
}
