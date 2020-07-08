package com.elegion.test.behancer.di;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.data.api.ApiKeyInterceptor;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.google.gson.Gson;


import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @FragmentScope
    OkHttpClient provideClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new ApiKeyInterceptor());
        if (!BuildConfig.BUILD_TYPE.contains("release")) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }

    @Provides
    @FragmentScope
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @FragmentScope
    Retrofit provideRetrofit(Gson sGson, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                // need for interceptors
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(sGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    @Provides
    @FragmentScope
    BehanceApi provideApiService(Retrofit retrofit) {
        return retrofit.create(BehanceApi.class);
    }
}


