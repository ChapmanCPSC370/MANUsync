package edu.chapman.manusync;

import android.content.Context;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.chapman.manusync.db.DatabaseHelper;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/8/2015.
 *
 *  This class is used as a 'provider' class. It builds any objects that are necessary for dependency
 *  injection. So for example, we need a context for the MenuItemProvider. In this case we need to provide
 *  dagger a Context so it can build the object for us. the method provideContext() tells dagger that this
 *  is the context we will be using whenever a dependency, like MenuItemProvider, calls for one. Likewise
 *  the method provideDatabaseHelper() used the same exact way, except for other dependencies like the DAO's
 *
 *  the @Provides annotation is how dagger knows that this is a 'provider' method that is used to inject these
 *  dependencies, and the @Singleton garuntees these objects are singletons. I don't believe the singleton is
 *  necessary, however it makes sense with the two different classes we need to provide.
 *
 *  Please jump to the LogInActivity for an explaination/example of how to actually inject a dependency now
 *  that we have our barebones set up.
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
