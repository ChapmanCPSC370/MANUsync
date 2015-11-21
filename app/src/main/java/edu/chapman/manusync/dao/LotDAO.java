package edu.chapman.manusync.dao;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.db.MANUContract;
import edu.chapman.manusync.dto.CompletedLotDTO;
import edu.chapman.manusync.dto.LotDTO;
import edu.chapman.manusync.dto.UserDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/21/15.
 */
public class LotDAO {
    private static final String TAG = LotDAO.class.getSimpleName();

    private UserDTO user;

    public LotDAO(UserDTO user) {
        this.user = user;
    }

    /* for now this requires internet connection */
    public LotDTO addNewLot(LotDTO lotDTO) throws ParseException {
        ParseObject newLot = new ParseObject(MANUContract.Lot.TABLE_NAME);
        newLot.put(MANUContract.Lot.COL_LOT_NUMBER, lotDTO.getLotNumber());
        newLot.put(MANUContract.Lot.COL_USER_ID, user.getParseId());
        newLot.put(MANUContract.Lot.COL_PART_NUMBER_ID, lotDTO.getPart().getParseId());
        newLot.put(MANUContract.Lot.COL_WORKSTATION_NUMBER, lotDTO.getWorkstationNumberString());
        newLot.put(MANUContract.Lot.COL_NUM_PARTS, lotDTO.getQuantity());
        newLot.put(MANUContract.Lot.COL_ACTUAL_TIME, -1.0);
        newLot.put(MANUContract.Lot.COL_TAKT_TIME, lotDTO.getTotalTaktTime());
        newLot.put(MANUContract.Lot.COL_IS_ON_TIME, false);
        newLot.put(MANUContract.Lot.COL_FINISHED, false);

        newLot.save();
        lotDTO.setParseID(newLot.getObjectId());

        return lotDTO;
    }

    public CompletedLotDTO finishLot(CompletedLotDTO completedLot) throws ParseException {
        ParseQuery<ParseObject> lotQuery = ParseQuery.getQuery(MANUContract.Lot.TABLE_NAME);

        if(!PasserSingleton.getInstance().isConnected())
            lotQuery.fromLocalDatastore();
        ParseObject lot = lotQuery.get(completedLot.getParseID());

        lot.put(MANUContract.Lot.COL_ACTUAL_TIME, completedLot.getTotalTimeSeconds());
        lot.put(MANUContract.Lot.COL_FINISHED, completedLot.getIsFinished());
        if(completedLot.getTotalTimeSeconds() <= completedLot.getTotalTaktTime())
            lot.put(MANUContract.Lot.COL_IS_ON_TIME, true);

        lot.saveEventually();
        return completedLot;
    }
}
