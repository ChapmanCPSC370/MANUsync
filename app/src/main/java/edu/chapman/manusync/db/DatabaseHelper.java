package edu.chapman.manusync.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import javax.inject.Inject;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/21/15.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "manu_sync.db";

    @Inject
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Creating Database");
        db.execSQL(MANUContract.ProductionLine.CREATE_TABLE);
        db.execSQL(MANUContract.Users.CREATE_TABLE);
        db.execSQL(MANUContract.Part.CREATE_TABLE);
        db.execSQL(MANUContract.Issues.CREATE_TABLE);
        db.execSQL(MANUContract.Lot.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
