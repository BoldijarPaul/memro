package com.bolnizar.memro.data.dagger;

import com.bolnizar.memro.R;
import com.bolnizar.memro.data.dagger.qualifiers.MemeTemplatesVersion;
import com.bolnizar.memro.data.prefs.IntegerPreference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
@Module
public class PrefModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(application.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    @MemeTemplatesVersion
    @Provides
    @Singleton
    IntegerPreference provideMemeTemplatesVersion(SharedPreferences sharedPreferences) {
        return new IntegerPreference(sharedPreferences, "current_version", 0);
    }
}
