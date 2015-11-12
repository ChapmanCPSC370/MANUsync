package edu.chapman.manusync.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import edu.chapman.manusync.db.DatabaseHelper;
import edu.chapman.manusync.db.MANUContract;
import edu.chapman.manusync.dto.ProductionLineDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/28/15.
 *
 *  DAO class that grabs data from the ProductionLine table & is used for anything pertaining to
 *  production lines.
 */
public class ProductionLineDAO {

    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Context context;

    @Inject
    public ProductionLineDAO(Context context, DatabaseHelper helper){
        this.context = context;
        this.helper = helper;
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    /* Selects all information from the Production line table, fills the DTO's with the respective
     * data, then returns a list. It is best to think of each DTO as a tuple in the database.
     *
     * Returns null if there is no data found.
     */
    public List<ProductionLineDTO> getAllProductionLines() throws SQLException, Exception {
        open();

        /* selects everything from production line table */
        String[] tableColumns = new String[] { "*" };
        Cursor cursor = database.query(MANUContract.ProductionLine.TABLE_NAME,
                tableColumns, null, null, null, null, null);

        /* fill DTO's with data from database by iterating over the returned cursor */
        List<ProductionLineDTO> tuples = new ArrayList<>();
        if(cursor.getCount() == 0) tuples = null;

        while(cursor.moveToNext()){
            tuples.add(new ProductionLineDTO(cursor.getInt(cursor.getColumnIndex(MANUContract.ProductionLine._ID)),
                    cursor.getString(cursor.getColumnIndex(MANUContract.ProductionLine.COL_PRODUCTION_LINE_ID)),
                    cursor.getInt(cursor.getColumnIndex(MANUContract.ProductionLine.COL_NUM_WORKSTATIONS)),
                    cursor.getString(cursor.getColumnIndex(MANUContract.ProductionLine.COL_PRODUCT_CREATED))));
        }

        cursor.close();
        close();

        return tuples;
    }

}
