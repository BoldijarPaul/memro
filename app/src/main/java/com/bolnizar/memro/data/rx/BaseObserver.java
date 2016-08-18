package com.bolnizar.memro.data.rx;

import rx.Observer;
import timber.log.Timber;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e, "BaseObserverError");
    }

    @Override
    public void onNext(T t) {

    }
}
