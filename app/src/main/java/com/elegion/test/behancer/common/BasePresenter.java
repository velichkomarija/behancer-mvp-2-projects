package com.elegion.test.behancer.common;


import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V extends BaseView> extends MvpPresenter<V> {

    protected final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    void disposeAll() {
            mCompositeDisposable.dispose();
    }
}
