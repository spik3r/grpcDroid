package api.infrastructure.persistence.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.InvalidProtocolBufferException;
import com.test.generated.LoginServiceGrpc;
import com.test.generated.LoginServiceGrpc.LoginServiceFutureStub;
import com.test.generated.RegisteredUser;
import com.test.generated.User;

import net.badata.protobuf.converter.Converter;

import java.util.concurrent.ExecutionException;

import api.domain.model.signIn.DomainRegisteredUser;
import api.domain.model.signIn.DomainUser;
import api.domain.model.signIn.SignInRepository;
import common.infrastructure.persistence.ClientConnectionManager;

/**
 * Created by kai-tait on 15/02/2017.
 */
public class SignInRepositoryImpl implements SignInRepository {
    private ClientConnectionManager manager;
    
    public SignInRepositoryImpl(ClientConnectionManager manager) {
        this.manager = manager;
    }
    
    private LoginServiceFutureStub getStub() {
        return  LoginServiceGrpc
                .newFutureStub(manager.getChannel());
    }
    
    @Override
    public DomainRegisteredUser signIn(DomainUser domainUser) throws
            InterruptedException, InvalidProtocolBufferException, ExecutionException
    {
        LoginServiceFutureStub stub = getStub();
        User request = Converter.create().toProtobuf(User.class, domainUser);
        ListenableFuture<RegisteredUser> response = stub.signIn(request);
        RegisteredUser registeredUser = null;
        registeredUser = response.get();
    
        return Converter.create().toDomain(DomainRegisteredUser.class,registeredUser);
    }
}
