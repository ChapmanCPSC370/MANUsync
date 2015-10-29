package edu.chapman.manusync;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.Random;

import edu.chapman.manusync.dao.UserDAO;
import edu.chapman.manusync.db.MANUContract;
import edu.chapman.manusync.dto.UserDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/23/15.
 *
 *  A temporary class used to fill infromation inside the database.
 */
public class tmpDBCreator {

    private UserDAO userDAO;

    public tmpDBCreator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void initPhoneyTables() {
        initUsers();
        initProdLines();
        initParts();
        initIssues();
    }

    private void initUsers() {
        Random random = new Random();
        for( int i = 0; i < 15; ++i ){
            try {
                userDAO.createUser(new UserDTO("niccorder" + i,
                        Hasher.SHA1("Password1"), "Nicholas", "Corder", random.nextInt(5) + 1));
            } catch(Exception e) {}
        }
    }

    private void initProdLines() {
        try {
            userDAO.open();
            SQLiteDatabase database = userDAO.database;
            for (int i = 1; i < 6; ++i) {
                ContentValues values = new ContentValues();
                values.put(MANUContract.ProductionLine.COL_PRODUCTION_LINE_ID, Integer.toString(i));
                values.put(MANUContract.ProductionLine.COL_NUM_WORKSTATIONS, "10");
                values.put(MANUContract.ProductionLine.COL_PRODUCT_CREATED, "Cars");

                database.insert(MANUContract.ProductionLine.TABLE_NAME, null, values);
            }
            database.close();
        } catch(Exception e){}
    }

    private void initParts() {
        try {
            userDAO.open();
            SQLiteDatabase database = userDAO.database;
            Random random = new Random();
            for(int i = 0; i < 50; ++i ){
                ContentValues values = new ContentValues();
                values.put(MANUContract.Part.COL_PART_ID, "part" + i);
                values.put(MANUContract.Part.COL_PRODUCTION_LINE_ID, Integer.toString(random.nextInt(5) + 1));
                values.put(MANUContract.Part.COL_TAKT_TIME, Double.toString(random.nextDouble() * 50) + 1);

                database.insert(MANUContract.Part.TABLE_NAME, null, values);
            }
            database.close();
        } catch (Exception e) {}
    }

    private void initIssues() {
        try {
            userDAO.open();
            SQLiteDatabase database = userDAO.database;
            for(int i = 0; i < 5; ++i) {
                ContentValues values = new ContentValues();
                values.put(MANUContract.Issues.REASON, "Reason no." + i);

                database.insert(MANUContract.Issues.TABLE_NAME, null, values);
            }
            database.close();
        } catch (Exception e){}
    }
}
