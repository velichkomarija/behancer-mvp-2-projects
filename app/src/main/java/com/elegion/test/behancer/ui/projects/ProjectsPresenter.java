package com.elegion.test.behancer.ui.projects;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectsPresenter extends BasePresenter<ProjectsView> {

    private ProjectsView mView;

    @Inject
    BehanceApi mApi;
    @Inject
    Storage mStorage;

    @Inject
    public ProjectsPresenter() {
    }

    void getProjects() {
        mCompositeDisposable.add(
                mApi.getProjects(BuildConfig.API_QUERY)
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertProjects)
                        .onErrorReturn(throwable ->
                                ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> mView.showRefresh())
                        .doFinally(mView::hideRefresh)
                        .subscribe(
                                response -> mView.showProjects(response.getProjects()),
                                throwable -> mView.showError())
        );
    }

    public void getUserProjects(String user) {
        mCompositeDisposable.add(
                mApi.getUserProject(user)
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertProjects)
                        .onErrorReturn(throwable ->
                                ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> mView.showRefresh())
                        .doFinally(mView::hideRefresh)
                        .subscribe(
                                response -> mView.showProjects(response.getProjects()),
                                throwable -> mView.showError())
        );
    }
    public void setView(ProjectsView view) {
        mView = view;
    }

    void openProfileFragment(String username) {
        mView.openDetailFragment(username);
    }
}
