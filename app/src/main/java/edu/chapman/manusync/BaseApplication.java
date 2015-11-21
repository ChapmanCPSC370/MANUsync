package edu.chapman.manusync;

import android.app.Application;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import edu.chapman.manusync.db.MANUContract;
import edu.chapman.manusync.listener.ConnectivityListener;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/8/2015.
 */
public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();

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

    /* used to grab all user information when possible */
    public void downloadCloudInformation() {
        ParseQuery<ParseObject> allUsers = ParseQuery.getQuery(MANUContract.Users.TABLE_NAME);
        allUsers.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(objects);
                } else {
                    Log.d(TAG, "Could not save users.");
                }
            }
        });

        ParseQuery<ParseObject> allProductionLines = ParseQuery.getQuery(MANUContract.ProductionLine.TABLE_NAME);
        allProductionLines.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(objects);
                } else {
                    Log.d(TAG, "Could not save production lines.");
                }
            }
        });

        ParseQuery<ParseObject> allParts = ParseQuery.getQuery(MANUContract.Part.TABLE_NAME);
        allParts.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(objects);
                } else {
                    Log.d(TAG, "Could not save parts.");
                }
            }
        });

        ParseQuery<ParseObject> allIssues = ParseQuery.getQuery(MANUContract.Issues.TABLE_NAME);
        allIssues.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(objects);
                } else {
                    Log.d(TAG, "Could not save issues.");
                }
            }
        });
    }
}
