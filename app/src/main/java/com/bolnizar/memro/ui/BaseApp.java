package com.bolnizar.memro.ui;

import com.bolnizar.memro.data.dagger.AppComponent;
import com.bolnizar.memro.data.dagger.AppGraph;
import com.orm.SugarApp;

import android.content.Context;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
public class BaseApp extends SugarApp {

    private AppGraph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();

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
