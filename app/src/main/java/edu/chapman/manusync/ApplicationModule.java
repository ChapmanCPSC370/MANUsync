package edu.chapman.manusync;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/8/2015.
 */

@Module
public class ApplicationModule {
    private final BaseApplication application;

    public ApplicationModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application.getApplicationContext();
    }
}
