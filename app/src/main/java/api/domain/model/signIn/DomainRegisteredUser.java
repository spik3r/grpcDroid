package api.domain.model.signIn;

import com.test.generated.RegisteredUser;
import com.test.generated.User;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;

/**
 * Created by kai-tait on 23/02/2017.
 */
@ProtoClass(RegisteredUser.class)
public class DomainRegisteredUser
{
    public DomainRegisteredUser() {}
    
    public DomainRegisteredUser(RegisteredUser user, String token) {
        
        this.user = user.getUser();
        this.token = token;
    }
    
    public DomainRegisteredUser(String name, String password, String token) {
        this.token = token;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    
    @ProtoField
    private User user;
    
    @ProtoField
    private String token;
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    @Override
    public String toString() {
        return "name: " + user.getName() + " \n" +
               "password: " + user.getPassword() + " \n" +
               "token: " + token;
    }
}
