package com.bolnizar.memro.data.dagger;

import com.bolnizar.memro.ui.BaseApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by BoldijarPaul on 18/08/16.
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent extends AppGraph {
    final class Initializer {
        private Initializer() {
        } // No instances

        public static AppGraph init(BaseApp app) {
            return DaggerAppComponent.builder()
                    .appModule(new AppModule(app))
                    .build();
        }
    }
}