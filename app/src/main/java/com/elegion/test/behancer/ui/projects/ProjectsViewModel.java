package com.elegion.test.behancer.ui.projects;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.data.model.project.RichProject;
import com.elegion.test.behancer.ui.projects.commonprojectsui.BaseViewModel;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.Single;


public class ProjectsViewModel extends BaseViewModel {

    public ProjectsViewModel(Storage storage, ProjectsAdapter.OnItemClickListener onItemClickListener) {
        super(storage, onItemClickListener);
        Future<LiveData<PagedList<RichProject>>> future = Executors.newSingleThreadExecutor().submit((Callable<LiveData<PagedList<RichProject>>>) storage::getProjectsPaged);
        try {
            mProjects = future.get();
        } catch (Exception e) {
            mProjects = new MutableLiveData<>();
        }
        updateProjects();
    }

    @Override
    protected Single<ProjectResponse> updateProjectsQuery() {
        return ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY);
    }
}
