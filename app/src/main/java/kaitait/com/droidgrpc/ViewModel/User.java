package kaitait.com.droidgrpc.ViewModel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

/**
 * Created by kai-tait on 7/03/2017.
 */

public class User extends BaseObservable {
    public ObservableField<String> name;
    public ObservableField<String> password;
    public ObservableField<String> response;
    
    public User() {
        this.name = new ObservableField<>();
        this.password = new ObservableField<>();
        this.response = new ObservableField<>();
    }
}
