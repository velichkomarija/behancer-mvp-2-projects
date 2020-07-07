package com.elegion.test.behancer.ui.projects.userprojects;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.data.model.project.RichProject;
import com.elegion.test.behancer.ui.projects.commonprojectsui.BaseViewModel;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Single;

public class UserProjectsViewModel extends BaseViewModel {

    private String mUsername;

    public UserProjectsViewModel(Storage storage, String userName) {
        super(storage, null);
        mUsername = userName;
        Future<LiveData<PagedList<RichProject>>> future = Executors.newSingleThreadExecutor().submit(() -> storage.getProjectsPaged(userName));
        try {
            mProjects = future.get();
        } catch (Exception e) {
            mProjects = new MutableLiveData<>();
        }
        updateProjects();
    }

    @Override
    protected Single<ProjectResponse> updateProjectsQuery() {
        return ApiUtils.getApiService().getUserProject(mUsername);
    }
}
