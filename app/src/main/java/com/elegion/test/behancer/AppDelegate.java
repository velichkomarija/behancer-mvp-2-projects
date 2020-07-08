package com.elegion.test.behancer;

import android.app.Application;
import com.elegion.test.behancer.di.AppModule;
import com.elegion.test.behancer.di.DaggerFragmentComponent;
import com.elegion.test.behancer.di.FragmentComponent;
import com.elegion.test.behancer.di.NetworkModule;

public class AppDelegate extends Application {

    private static FragmentComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerFragmentComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule()).build();
    }

    public static FragmentComponent getAppComponent() {
        return sAppComponent;
    }
}
