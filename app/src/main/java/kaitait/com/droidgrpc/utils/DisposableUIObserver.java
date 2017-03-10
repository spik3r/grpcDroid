package kaitait.com.droidgrpc.utils;

import io.reactivex.observers.DisposableObserver;

/**
 * Extension of {@link DisposableObserver} that ignores completion and logs errors
 * @author Alex Royds
 */

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
