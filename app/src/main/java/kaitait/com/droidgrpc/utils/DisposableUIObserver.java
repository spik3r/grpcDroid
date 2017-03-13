package kaitait.com.droidgrpc.utils;

import android.util.Log;

import io.reactivex.observers.DisposableObserver;

public abstract class DisposableUIObserver<T> extends DisposableObserver<T> {
    @Override
    public void onError(Throwable e) {
        Log.e("DisposableUIObserver", e.getMessage());
    }

    @Override
    public void onComplete() {
        //ignored
    }
}
