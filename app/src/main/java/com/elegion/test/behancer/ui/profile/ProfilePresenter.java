package com.elegion.test.behancer.ui.profile;

import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.ui.projects.ProjectsView;
import com.elegion.test.behancer.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter<ProfileView> {

    private ProfileView mView;

    @Inject
    Storage mStorage;

    @Inject
    BehanceApi mApi;

    @Inject
    ProfilePresenter() {
    }

    void getProfile(String username) {
        mCompositeDisposable.add(mApi.getUserInfo(username)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(mStorage::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(username) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mView.showRefresh())
                .doFinally(mView::hideRefresh)
                .subscribe(
                        response -> mView.showProfile(response.getUser()),
                        throwable -> mView.showError()));
    }

    public void setView(ProfileView view) {
        mView = view;
    }
}
