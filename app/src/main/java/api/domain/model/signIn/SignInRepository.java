package api.domain.model.signIn;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.concurrent.ExecutionException;

/**
 * Created by kai-tait on 15/02/2017.
 */
public interface SignInRepository {
    DomainRegisteredUser signIn(DomainUser domainUser)
            throws InterruptedException, InvalidProtocolBufferException, ExecutionException;
}
