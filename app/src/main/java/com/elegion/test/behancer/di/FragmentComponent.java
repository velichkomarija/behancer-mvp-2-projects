package com.elegion.test.behancer.di;

import com.elegion.test.behancer.ui.projects.ProjectsFragment;
import com.elegion.test.behancer.ui.projects.userprojects.UserProjectsFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Component;


@FragmentScope
@Component(modules = {AppModule.class, NetworkModule.class})
public interface FragmentComponent {

    void inject(ProjectsFragment injector);
    void inject(UserProjectsFragment injector);
}

