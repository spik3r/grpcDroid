package kaitait.com.droidgrpc.utils;

import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

public class FieldUtils
{
    @NonNull
    public static <T> ObservableField<T> ToField(@NonNull Observable<T> observable)
    {
        return ReadOnlyField.create(observable);
    }
    
    @NonNull
    public static <T> Observable<String> ToObservable(@NonNull final ObservableField<String> field)
    {
        return Observable.create(new ObservableOnSubscribe<String>()
        {
            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception
            {
                final OnPropertyChangedCallback callback = new OnPropertyChangedCallback()
                {
                    @Override
                    public void onPropertyChanged(
                            android.databinding.Observable databinding_observable,
                            int property_id)
                    {
                        if (databinding_observable == field)
                        {
                            e.onNext(field.get() == null? "" : field.get());
                        }
                    }
                };
                field.addOnPropertyChangedCallback(callback);
                e.setCancellable(new Cancellable()
                {
                    @Override
                    public void cancel() throws Exception
                    {
                        field.removeOnPropertyChangedCallback(callback);
                    }
                });
                
            }
        });
    }
}
