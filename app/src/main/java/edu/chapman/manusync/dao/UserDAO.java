package edu.chapman.manusync.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import edu.chapman.manusync.db.DatabaseHelper;
import edu.chapman.manusync.db.MANUContract;
import edu.chapman.manusync.dto.UserDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/22/15.
 */
public class UserDAO {

    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Context context;

    @Inject
    public UserDAO(Context context, DatabaseHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    /* will assume that all data is verified beforehand */
    public UserDTO createUser(UserDTO userDTO) throws SQLException, Exception {
        open();
        ContentValues values = new ContentValues();

        values.put(MANUContract.Users.COL_USERNAME, userDTO.getUsername());
        values.put(MANUContract.Users.COL_PASSWORD, userDTO.getPassword());
        values.put(MANUContract.Users.COL_FIRST_NAME, userDTO.getFirstName());
        values.put(MANUContract.Users.COL_LAST_NAME, userDTO.getLastName());
        values.put(MANUContract.Users.COL_PRODUCTION_LINE_ID, userDTO.getProductionLineId());

        //Setting creation date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String creationDate = dateFormat.format(Calendar.getInstance().getTime());
        values.put(MANUContract.Users.COL_CREATION_DATE, creationDate);

        long returnCode = database.insert(MANUContract.Users.TABLE_NAME, null, values);

        close();

        if(returnCode == -1) {
            throw new Exception("There was an issue inserting into the database.");
        }

        return new UserDTO((int)returnCode, userDTO.getUsername(), userDTO.getPassword(),
                userDTO.getFirstName(), userDTO.getLastName(), userDTO.getProductionLineId(),
                creationDate);
    }

    /* assumes necessary information was checked beforehand */
    public UserDTO logInUser(UserDTO userDTO) throws Exception {
        open();
        String[] tableColumns = new String[] { "*" };
        String whereClause = MANUContract.Users.COL_USERNAME + " = ? AND "
                + MANUContract.Users.COL_PASSWORD + " = ?";
        String[] whereArgs = new String[] {
                userDTO.getUsername(),
                userDTO.getPassword()
        };

        Cursor cursor = database.query(MANUContract.Users.TABLE_NAME, tableColumns, whereClause, whereArgs, null, null, null);
        if(cursor.moveToFirst()) {
            UserDTO loggedInUser = new UserDTO(cursor.getInt(cursor.getColumnIndex(MANUContract.Users._ID)),
                    cursor.getString(cursor.getColumnIndex(MANUContract.Users.COL_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(MANUContract.Users.COL_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(MANUContract.Users.COL_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndex(MANUContract.Users.COL_LAST_NAME)),
                    cursor.getInt(cursor.getColumnIndex(MANUContract.Users.COL_PRODUCTION_LINE_ID)),
                    cursor.getString(cursor.getColumnIndex(MANUContract.Users.COL_CREATION_DATE)));
            cursor.close();
            close();
            return loggedInUser;
        } else {
            cursor.close();
            close();
            throw new Exception("Incorrect username or password.");
        }
    }
}
