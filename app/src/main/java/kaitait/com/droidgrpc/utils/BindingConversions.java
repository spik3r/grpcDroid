package kaitait.com.droidgrpc.utils;

import android.databinding.BindingConversion;
import android.view.View;

import io.reactivex.subjects.PublishSubject;

/**
 * @author Alex Royds
 */

public class BindingConversions
{
    @BindingConversion
    public static View.OnClickListener ToOnClickListener(final PublishSubject<Object> listener)
    {
        if (listener != null)
        {
            return view -> listener.onNext(new Object());
        } 
        else 
        {
            return null;
        }
    }
}
