package kaitait.com.droidgrpc.utils;

import android.databinding.BindingConversion;
import android.view.View;

import io.reactivex.subjects.PublishSubject;

public class BindingConversions {
    @BindingConversion
    public static View.OnClickListener toOnClickListener(final PublishSubject<Object> listener) {
        if (listener != null) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onNext(new Object());
                }
            };
        } else {
            return null;
        }
    }
}
