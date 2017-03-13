package kaitait.com.droidgrpc.ViewModel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import net.badata.protobuf.converter.annotation.ProtoField;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import kaitait.com.droidgrpc.functions.FormClickValidityCombiner;
import kaitait.com.droidgrpc.widgets.ValidatingField;
import kaitait.com.droidgrpc.widgets.ValidatingForm;

public class User extends BaseObservable {
    @ProtoField
    @NotNull
    @NotBlank
    @Length(min = 3)
    public ObservableField<String> name;
    public ObservableField<Object> nameError;
    
    @ProtoField
    @NotNull
    @NotBlank
    @Length(min = 8)
    public ObservableField<String> password;
    public ObservableField<Object> passwordError;
    
    public ObservableField<String> response;
    public PublishSubject<Object> sendButton;
    
    private ValidatingForm login_form;
    public Observable<Boolean> login_click_validity;
    
    public User() {
        this.name = new ObservableField<>();
        this.nameError = new ObservableField<>();
        this.password = new ObservableField<>();
        this.response = new ObservableField<>();
        this.sendButton = PublishSubject.create();
    
        InitialiseClickObservables();
        CreateValidationForm();
        AddFieldsRequiringValidation();
        InitialiseLoginClick();
    }
    
    private void InitialiseClickObservables()
    {
        this.sendButton = PublishSubject.create();
    }
    
    private void CreateValidationForm()
    {
        this.login_form = new ValidatingForm(this.sendButton);
    }
    
    
    private void AddFieldsRequiringValidation()
    {
        ValidatingField username_widget
                = this.login_form.AddValidatingField(this, "name", true);
        ValidatingField password_widget
                = this.login_form.AddValidatingField(this, "password", true);
        SetObservableFields(username_widget, password_widget);
    }
    
    private void SetObservableFields(
            ValidatingField username_widget,
            ValidatingField password_widget)
    {
        this.name = username_widget.GetValidatableField();
        this.password = password_widget.GetValidatableField();
        this.nameError = username_widget.GetErrorObservableField();
        this.passwordError = password_widget.GetErrorObservableField();
    }
    
    private void InitialiseLoginClick()
    {
        this.login_click_validity = this.sendButton.withLatestFrom(
                login_form.GetFormValidityStream(),
                new FormClickValidityCombiner());
    }
}
