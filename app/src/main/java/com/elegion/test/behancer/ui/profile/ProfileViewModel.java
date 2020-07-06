package com.elegion.test.behancer.ui.profile;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel {

    private ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private ObservableBoolean mIsErrorVisible = new ObservableBoolean(false);
    private ObservableField<User> mUser = new ObservableField<>();

    private Disposable mDisposable;
    private Storage mStorage;
    private String mUsername;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = this::onRefreshData;

    public ProfileViewModel(Storage storage, String username) {
        mStorage = storage;
        mUsername = username;
    }

    public String getUsername() {
        return mUsername;
    }

    public void onRefreshData() {
        mDisposable = ApiUtils.getApiService().getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.set(true))
                .doFinally(() -> mIsLoading.set(false))
                .subscribe(
                        response -> {
                            mUser.set(response.getUser());
                            mIsErrorVisible.set(false);
                        },
                        throwable -> mIsErrorVisible.set(true));
    }

    public void dispatchDetach() {
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public ObservableField<User> getUser(){
        return mUser;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public ObservableBoolean getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }
}