package com.elegion.test.behancer.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.ui.projects.userprojects.UserProjectsViewModel;


public class CustomUserProjectsFactory extends ViewModelProvider.NewInstanceFactory {

    private Storage mStorage;
    private String mUsername;

    public CustomUserProjectsFactory(Storage storage, String username) {
        mStorage = storage;
        mUsername = username;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == UserProjectsViewModel.class) {
            return (T) new UserProjectsViewModel(mStorage, mUsername);
        }
        return super.create(modelClass);
    }
}