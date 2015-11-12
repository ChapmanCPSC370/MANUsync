package edu.chapman.manusync;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/8/2015.
 */
public class BaseApplication extends Application {


    /*
     * Starts Dagger-2's dependency injection on application start.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        MANUComponent.Instance.init(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "rsl6FR3p81PmNQQPAr4Bkg4pAWpmDvCWepAmBCWk", "qXwwN6aDtU0QUwNcEuVrhWSFqwrfaZ6syGIQLQFu");
    }
}
