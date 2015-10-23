package edu.chapman.manusync;

import android.content.Context;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.chapman.manusync.db.DatabaseHelper;

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

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper(){
        Log.d("app module", "Creating db-helper");
        return new DatabaseHelper(application.getApplicationContext()); }
}
