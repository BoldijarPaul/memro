package com.bolnizar.memro.data.dagger;

import com.bolnizar.memro.ui.BaseApp;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
@Module
public class AppModule {
    private final BaseApp mBaseApp;

    public AppModule(BaseApp baseApp) {
        mBaseApp = baseApp;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mBaseApp;
    }
}
