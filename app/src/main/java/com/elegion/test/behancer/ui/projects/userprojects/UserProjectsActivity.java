package com.elegion.test.behancer.ui.projects.userprojects;

import android.support.v4.app.Fragment;

import com.elegion.test.behancer.common.SingleFragmentActivity;

import static com.elegion.test.behancer.ui.profile.ProfileActivity.USERNAME_KEY;

public class UserProjectsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        if (getIntent() != null) {
            return UserProjectsFragment.newInstance(getIntent().getBundleExtra(USERNAME_KEY));
        }
        throw new IllegalStateException("getIntent cannot be null");
    }
}
