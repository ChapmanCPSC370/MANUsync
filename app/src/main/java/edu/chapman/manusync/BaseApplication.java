package edu.chapman.manusync;

import android.app.Application;

import com.parse.Parse;

import edu.chapman.manusync.listener.ConnectivityListener;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/8/2015.
 */
public class BaseApplication extends Application {

    private ConnectivityListener connectivityListener;

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

        connectivityListener = new ConnectivityListener(this);
        connectivityListener.start();
    }

    @Override
    public void onTerminate() {
        connectivityListener.stop();
        super.onTerminate();
    }

    private void downloadUsers() {

    }
}
