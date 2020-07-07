package com.elegion.test.behancer.ui.projects;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.ui.projects.commonprojectsui.BaseProjectFragment;
import com.elegion.test.behancer.ui.profile.ProfileActivity;
import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.utils.CustomProjectsFactory;

import org.jetbrains.annotations.NotNull;

public class ProjectsFragment extends BaseProjectFragment {


    private ProjectsAdapter.OnItemClickListener mOnItemClickListener = username -> {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        Bundle args = new Bundle();
        args.putString(ProfileFragment.PROFILE_KEY, username);
        intent.putExtra(ProfileActivity.USERNAME_KEY, args);
        startActivity(intent);
    };

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            Storage storage = ((Storage.StorageOwner) context).obtainStorage();
            CustomProjectsFactory factory = new CustomProjectsFactory(storage, mOnItemClickListener);
            mBaseViewModel = ViewModelProviders.of(this, factory).get(ProjectsViewModel.class);
        }
    }

    @Override
    public Fragment newInstance(Bundle args) {
        return new ProjectsFragment();
    }
}
