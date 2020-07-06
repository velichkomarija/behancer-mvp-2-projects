package com.elegion.test.behancer.common;


import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter {

    protected final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    void disposeAll() {
            mCompositeDisposable.dispose();
    }
}
