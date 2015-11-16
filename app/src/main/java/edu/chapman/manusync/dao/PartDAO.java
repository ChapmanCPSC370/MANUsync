package edu.chapman.manusync.dao;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.db.MANUContract;
import edu.chapman.manusync.dto.PartDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/16/15.
 */
public class PartDAO {
    private static final String TAG = PartDAO.class.getSimpleName();

    public PartDAO() {}

    public List<PartDTO> getAllParts() throws ParseException{
        ParseQuery<ParseObject> query = ParseQuery.getQuery(MANUContract.Part.TABLE_NAME);
        query.orderByDescending(MANUContract.Part.COL_PART_ID);

        if(!PasserSingleton.getInstance().isConnected())
            query.fromLocalDatastore();

        List<ParseObject> foundParts = query.find();
        List<PartDTO> allParts = new ArrayList<>();
        for(ParseObject object : foundParts){
            allParts.add(new PartDTO(object.getObjectId(),
                    object.getString(MANUContract.Part.COL_PART_ID),
                    object.getDouble(MANUContract.Part.COL_TAKT_TIME)));
        }
        return allParts;
    }
}
