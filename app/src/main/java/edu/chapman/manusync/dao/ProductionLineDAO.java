package edu.chapman.manusync.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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

    public ProductionLineDAO(){}

    /* Selects all information from the Production line table, fills the DTO's with the respective
     * data, then returns a list. It is best to think of each DTO as a tuple in the database.
     *
     * Returns null if there is no data found.
     */
    public List<ProductionLineDTO> getAllProductionLines() throws ParseException {

        ParseQuery<ParseObject> query = new ParseQuery<>(MANUContract.ProductionLine.TABLE_NAME);
        query.orderByDescending(MANUContract.ProductionLine.COL_PRODUCTION_LINE_ID);

        List<ParseObject> parseObjects = query.find();
        List<ProductionLineDTO> allProductionLines = new ArrayList<>();
        for(ParseObject productionLine : parseObjects) {
            allProductionLines.add(new ProductionLineDTO(productionLine.getObjectId(),
                    productionLine.getString(MANUContract.ProductionLine.COL_PRODUCTION_LINE_ID),
                    productionLine.getInt(MANUContract.ProductionLine.COL_NUM_WORKSTATIONS),
                    productionLine.getString(MANUContract.ProductionLine.COL_PRODUCT_CREATED)));
        }

        return allProductionLines;
    }

    public ProductionLineDTO getProductionLine(String parseId) throws ParseException {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(MANUContract.ProductionLine.TABLE_NAME);
        ParseObject productionLine = query.get(parseId);
        return new ProductionLineDTO(productionLine.getObjectId(),
                productionLine.getString(MANUContract.ProductionLine.COL_PRODUCTION_LINE_ID),
                productionLine.getInt(MANUContract.ProductionLine.COL_NUM_WORKSTATIONS),
                productionLine.getString(MANUContract.ProductionLine.COL_PRODUCT_CREATED));
    }
}
