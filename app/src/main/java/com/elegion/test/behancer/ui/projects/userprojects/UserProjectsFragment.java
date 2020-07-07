package com.elegion.test.behancer.ui.projects.userprojects;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.ui.projects.commonprojectsui.BaseProjectFragment;
import com.elegion.test.behancer.utils.CustomUserProjectsFactory;

public class UserProjectsFragment extends BaseProjectFragment {

    public static final String PROFILE_KEY = "PROFILE_KEY";


    @Override
    public Fragment newInstance(Bundle args) {
        UserProjectsFragment userProjectsFragment = new UserProjectsFragment();
        userProjectsFragment.setArguments(args);
        return userProjectsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getArguments() != null) {
            String username = getArguments().getString(PROFILE_KEY);

            if (context instanceof Storage.StorageOwner) {
                Storage storage = ((Storage.StorageOwner) context).obtainStorage();
                CustomUserProjectsFactory factory = new CustomUserProjectsFactory(storage, username);
                mBaseViewModel = ViewModelProviders.of(this, factory).get(UserProjectsViewModel.class);
            }
        }
    }
}
