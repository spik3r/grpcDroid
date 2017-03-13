package api.domain.model.signIn;

import com.test.generated.User;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import common.domain.ValueObject;

@ProtoClass(User.class)
public class DomainUser extends ValueObject {
    public DomainUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @ProtoField
    @NotNull
    @NotBlank
    @Length(min = 3)
    private String name;

    @ProtoField
    @NotNull
    @NotBlank
    @Length(min = 8)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
