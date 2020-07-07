package com.elegion.test.behancer.ui.projects.commonprojectsui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.test.behancer.databinding.ProjectsBinding;

public abstract class BaseProjectFragment extends Fragment {

   public BaseViewModel mBaseViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ProjectsBinding binding = ProjectsBinding.inflate(inflater, container, false);
        binding.setBvm(mBaseViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    public abstract Fragment newInstance(Bundle args);
}
