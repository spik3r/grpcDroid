package kaitait.com.droidgrpc.utils;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kaitait.com.droidgrpc.functions.enums.SpecialFunctionReturns;

public class ReadOnlyField<T> extends ObservableField<T> {
    final Observable<T> source;
    final HashMap<OnPropertyChangedCallback, Disposable> subscriptions = new HashMap<>();

    public static <U> ReadOnlyField<U> create(@NonNull Observable<U> source) {
        return new ReadOnlyField<>(source);
    }

    protected ReadOnlyField(@NonNull Observable<T> source) {
        super();
        this.source = source
                .doOnNext(new Consumer<T>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull T t) throws Exception {
                        if (t instanceof SpecialFunctionReturns) {
                            ReadOnlyField.this.handleSpecialFunctionCase((SpecialFunctionReturns) t);
                        } else {
                            ReadOnlyField.super.set(null);
                            ReadOnlyField.super.set(t);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable)
                            throws Exception {
                        System.out.println("Error in source observable");
                    }
                })
                .share();
    }

    private void handleSpecialFunctionCase(SpecialFunctionReturns special_function_return) {
        if (special_function_return == SpecialFunctionReturns.NO_ERROR) {
            ReadOnlyField.super.set(null);
        }
    }

    /**
     * @deprecated Setter of ReadOnlyField does nothing. Merge with the source Observable instead.
     */
    @Deprecated
    @Override
    public void set(T value) {
    }

    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        super.addOnPropertyChangedCallback(callback);
        subscriptions.put(callback, source.subscribe());
    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        super.removeOnPropertyChangedCallback(callback);
        Disposable subscription = subscriptions.remove(callback);
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
