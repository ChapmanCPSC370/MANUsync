package edu.chapman.manusync.db;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/21/15.
 */

@Singleton
public class DatabaseManager {
    private static final String TAG = DatabaseManager.class.getSimpleName();

    private final Context context;
    private DatabaseHelper helper;

    @Inject
    public DatabaseManager(Context context, DatabaseHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    public void close() {
        helper.close();
    }
}
