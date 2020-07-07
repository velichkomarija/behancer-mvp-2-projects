package com.elegion.test.behancer.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.databinding.*;
import com.elegion.test.behancer.ui.projects.userprojects.UserProjectsActivity;
import com.elegion.test.behancer.ui.projects.userprojects.UserProjectsFragment;

public class ProfileFragment extends Fragment{

    public static final String PROFILE_KEY = "PROFILE_KEY";

    private ProfileViewModel mProfileViewModel;

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private OnClickListener mOnClickListener = username -> {
        Intent intent = new Intent(getActivity(), UserProjectsActivity.class);
        Bundle args = new Bundle();
        args.putString(UserProjectsFragment.PROFILE_KEY, username);
        intent.putExtra(UserProjectsActivity.USERNAME_KEY, args);
        startActivity(intent);
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        String username = "";
        if (getActivity() != null) {
            username = getArguments().getString(PROFILE_KEY);
        }
        if (context instanceof Storage.StorageOwner) {
            Storage storage = ((Storage.StorageOwner) context).obtainStorage();
            mProfileViewModel = new ProfileViewModel(storage, username);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ProfileBinding binding =  ProfileBinding.inflate(inflater, container, false);
        binding.setVm(mProfileViewModel);
        binding.setOnClickListener(mOnClickListener);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(mProfileViewModel.getUsername());
        }
        mProfileViewModel.onRefreshData();
    }

    @Override
    public void onDetach() {
        mProfileViewModel.dispatchDetach();
        super.onDetach();
    }

    public interface OnClickListener {
        void onClick(String username);
    }
}
