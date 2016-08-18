package com.bolnizar.memro.ui;

import com.bolnizar.memro.BuildConfig;
import com.bolnizar.memro.data.dagger.AppComponent;
import com.bolnizar.memro.data.dagger.AppGraph;
import com.orm.SugarApp;

import android.content.Context;

import timber.log.Timber;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public class BaseApp extends SugarApp {

    private AppGraph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        mGraph = AppComponent.Initializer.init(this);
        mGraph.inject(this);
    }

    public AppGraph graph() {
        return mGraph;
    }

    public static BaseApp get(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

}
