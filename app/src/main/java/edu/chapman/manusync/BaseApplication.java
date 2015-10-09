package edu.chapman.manusync;

import android.app.Application;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/8/2015.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MANUComponent.Instance.init(this);
    }
}
