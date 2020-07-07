package com.elegion.test.behancer.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.ui.projects.ProjectsAdapter;
import com.elegion.test.behancer.ui.projects.ProjectsViewModel;

/**
 * @author Azret Magometov
 */
public class CustomProjectsFactory extends ViewModelProvider.NewInstanceFactory {

    private Storage mStorage;
    private ProjectsAdapter.OnItemClickListener mOnItemClickListener;

    public CustomProjectsFactory(Storage storage, ProjectsAdapter.OnItemClickListener onItemClickListener) {
        mStorage = storage;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == ProjectsViewModel.class) {
            return (T) new ProjectsViewModel(mStorage, mOnItemClickListener);
        }
        return super.create(modelClass);
    }
}
