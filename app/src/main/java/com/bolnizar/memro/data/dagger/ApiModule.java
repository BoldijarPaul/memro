package com.bolnizar.memro.data.dagger;

import com.bolnizar.memro.data.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
@Module
public class ApiModule {

    private static final String ENDPOINT = "https://raw.githubusercontent.com/BoldijarPaul/memro/master/";

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    ApiService provideRoService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
