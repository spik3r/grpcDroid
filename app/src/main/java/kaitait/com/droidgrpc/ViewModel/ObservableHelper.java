package kaitait.com.droidgrpc.ViewModel;

import android.databinding.ObservableField;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Cancellable;

public class ObservableHelper {
    @NonNull
    public static <T> Observable<String> ToObservable(@NonNull final ObservableField<String> field)
    {
        return Observable.create(new ObservableOnSubscribe<String>()
        {
            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception
            {
                final android.databinding.Observable.OnPropertyChangedCallback callback = new android.databinding.Observable.OnPropertyChangedCallback()
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
