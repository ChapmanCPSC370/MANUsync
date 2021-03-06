package edu.chapman.manusync.db;

import android.provider.BaseColumns;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/21/15.
 *
 *  A contract used for foundations of the SQLite database
 *  reference: http://developer.android.com/training/basics/data-storage/databases.html
 */
public class MANUContract {

    public static final String DATABASE_NAME = "manu_sync.db";

    /* disallows class to be created */
    private MANUContract() {
    }

    public static final class Users implements BaseColumns {

        /* disallows class to be created */
        private Users() {
        }

        public static final String TABLE_NAME = "User";
        public static final String COL_USERNAME = "Username";
        public static final String COL_PASSWORD = "Password";
        public static final String COL_FIRST_NAME = "FirstName";
        public static final String COL_LAST_NAME = "LastName";
        public static final String COL_PRODUCTION_LINE_ID = "ProductionLineID";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COL_USERNAME + " TEXT NOT NULL,"
                + COL_PASSWORD + " TEXT NOT NULL,"
                + COL_FIRST_NAME + " TEXT NOT NULL,"
                + COL_LAST_NAME + " TEXT NOT NULL,"
                + COL_PRODUCTION_LINE_ID + " INTEGER,"
                + "FOREIGN KEY(" + COL_PRODUCTION_LINE_ID + ") REFERENCES " + ProductionLine.TABLE_NAME + "(" + ProductionLine._ID + ")"
                + ");";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String[] COL_ARRAY = {
                COL_USERNAME,
                COL_PASSWORD,
                COL_FIRST_NAME,
                COL_LAST_NAME,
                COL_PRODUCTION_LINE_ID
        };
    }

    public static final class ProductionLine implements BaseColumns {

        /* disallows class to be created */
        private ProductionLine() {
        }

        public static final String TABLE_NAME = "ProductionLine";
        public static final String COL_PRODUCTION_LINE_ID = "ProductionLineID";
        public static final String COL_NUM_WORKSTATIONS = "NumWorkstations";
        public static final String COL_PRODUCT_CREATED = "Product";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COL_PRODUCTION_LINE_ID + " TEXT NOT NULL,"
                + COL_NUM_WORKSTATIONS + " INTEGER NOT NULL,"
                + COL_PRODUCT_CREATED + " TEXT NOT NULL);";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String[] COL_ARRAY = {
                COL_PRODUCTION_LINE_ID,
                COL_NUM_WORKSTATIONS,
                COL_PRODUCT_CREATED
        };
    }

    public static final class Part implements BaseColumns {

        /* disallows class to be created */
        private Part() {
        }

        public static final String TABLE_NAME = "Part";
        public static final String COL_PART_ID = "PartNumber";
        public static final String COL_TAKT_TIME = "TaktTime";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COL_PART_ID + " TEXT NOT NULL,"
                + COL_TAKT_TIME + " REAL);";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String[] COL_ARRAY = {
                COL_PART_ID,
                COL_TAKT_TIME
        };
    }

    public static final class Lot implements BaseColumns {

        /* disallows class to be created */
        private Lot() {}

        public static final String TABLE_NAME = "Lot";
        public static final String COL_LOT_NUMBER = "LotNumber";
        public static final String COL_USER_ID = "UserID";
        public static final String COL_PART_NUMBER_ID = "PartID";
        public static final String COL_WORKSTATION_NUMBER = "WorkstationNumber";
        public static final String COL_TAKT_TIME = "TaktTime";
        public static final String COL_ACTUAL_TIME = "ActualTime";
        public static final String COL_IS_ON_TIME = "OnTime";
        public static final String COL_FINISHED = "Finished";
        public static final String COL_FINISHED_PARTS = "FinishedParts";
        public static final String COL_NUM_PARTS = "NumParts";
        public static final String COL_ISSUE_ID = "IssueID";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COL_LOT_NUMBER + " TEXT NOT NULL,"
                + COL_USER_ID + " INTEGER NOT NULL,"
                + COL_PART_NUMBER_ID + " INTEGER NOT NULL,"
                + COL_ACTUAL_TIME + " REAL NOT NULL,"
                + COL_IS_ON_TIME + " INTEGER NOT NULL,"
                + COL_ISSUE_ID + " INTEGER,"
                + "FOREIGN KEY(" + COL_ISSUE_ID + ") REFERENCES " + Issues.TABLE_NAME + "(" + Issues._ID + "),"
                + "FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + Users.TABLE_NAME + "(" + Users._ID + "),"
                + "FOREIGN KEY(" + COL_PART_NUMBER_ID + ") REFERENCES " + Part.TABLE_NAME + "(" + Part._ID + ")"
                + ");";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String[] COL_ARRAY = {
                COL_LOT_NUMBER,
                COL_USER_ID,
                COL_ACTUAL_TIME,
                COL_IS_ON_TIME,
                COL_ISSUE_ID
        };
    }

    public static final class Issues implements BaseColumns {

        /* disallows class to be created */
        private Issues() {}

        public static final String TABLE_NAME = "Issues";
        public static final String COL_REASON = "Reason";
        public static final String COL_WEIGHT = "Weight";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COL_REASON + " TEXT NOT NULL);";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String[] COL_ARRAY = {
                COL_REASON,
                COL_WEIGHT
        };
    }

    public static final class LotIssues implements BaseColumns {

        /* disallows class to be created */
        private LotIssues() {}

        public static final String TABLE_NAME = "LotIssues";
        public static final String COL_LOT_ID = "LotID";
        public static final String COL_ISSUE_ID = "IssueID";
    }
}