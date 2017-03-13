package kaitait.com.droidgrpc.utils;

import io.reactivex.observers.DisposableObserver;

public abstract class DisposableUIObserver<T> extends DisposableObserver<T>
{
    @Override
    public void onError(Throwable e)
    {
        //TODO
    }
    
    @Override
    public void onComplete()
    {
        //ignored
    }
}
