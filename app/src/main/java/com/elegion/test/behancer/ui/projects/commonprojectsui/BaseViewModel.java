package com.elegion.test.behancer.ui.projects.commonprojectsui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.data.model.project.RichProject;
import com.elegion.test.behancer.ui.projects.ProjectsAdapter;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseViewModel extends ViewModel {

    private Storage mStorage;
    private Disposable mDisposable;
    public LiveData<PagedList<RichProject>> mProjects;

    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = this::updateProjects;
    private ProjectsAdapter.OnItemClickListener mOnItemClickListener;


    public BaseViewModel(Storage storage, ProjectsAdapter.OnItemClickListener onItemClickListener) {
        mStorage = storage;
        mOnItemClickListener = onItemClickListener;
    }

    public void updateProjects() {
        mDisposable = updateProjectsQuery()
                .map(ProjectResponse::getProjects)
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .doOnSuccess(response -> mIsErrorVisible.postValue(false))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            Future<Void> future = Executors.newSingleThreadExecutor().submit(() -> {
                                mStorage.insertProjects(response);
                                return null;
                            });
                            future.get();
                        },
                        throwable -> {
                            boolean value = mProjects.getValue() == null || mProjects.getValue().size() == 0;
                            mIsErrorVisible.postValue(value);
                        });
    }

    protected abstract Single<ProjectResponse> updateProjectsQuery();

    @Override
    public void onCleared() {
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public LiveData<PagedList<RichProject>> getProjects() {
        return mProjects;
    }

    public Storage getStorage() {
        return mStorage;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    protected void setIsLoading(boolean value) {
        this.mIsLoading.postValue(value);
    }

    public MutableLiveData<Boolean> getIsErrorVisible() {
        return mIsErrorVisible;
    }

    protected void setIsErrorVisible(boolean value) {
        this.mIsErrorVisible.postValue(value);
    }

    protected void setDisposable(Disposable mDisposable) {
        this.mDisposable = mDisposable;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public ProjectsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

}
