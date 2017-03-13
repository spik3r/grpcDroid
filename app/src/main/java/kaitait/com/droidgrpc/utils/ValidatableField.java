package kaitait.com.droidgrpc.utils;

import android.databinding.ObservableField;

public class ValidatableField<T> extends ObservableField<T>
{
    @Override
    public String toString()
    {
        return this.get() == null? "" : this.get().toString();
    }
}
