package com.elegion.test.behancer.ui.projects;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.utils.ApiUtils;
import com.elegion.test.behancer.data.Storage;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {

    private final Storage mStorage;

    public ProjectsPresenter( Storage storage) {
        mStorage = storage;
    }

    void getProjects() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertProjects)
                        .onErrorReturn(throwable ->
                                ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                response -> getViewState().showProjects(response.getProjects()),
                                throwable -> getViewState().showError())
        );
    }

    public void getUserProjects(String user) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getUserProject(user)
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertProjects)
                        .onErrorReturn(throwable ->
                                ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                response -> getViewState().showProjects(response.getProjects()),
                                throwable -> getViewState().showError())
        );
    }

    void openProfileFragment(String username) {
        getViewState().openDetailFragment(username);
    }
}
