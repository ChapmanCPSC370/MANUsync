package edu.chapman.manusync.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import edu.chapman.manusync.Hasher;
import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.db.DatabaseHelper;
import edu.chapman.manusync.db.MANUContract;
import edu.chapman.manusync.dto.ProductionLineDTO;
import edu.chapman.manusync.dto.UserDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/22/15.
 *
 *  A DAO class that is used for communicating to the User table in the local SQLite database.
 *  You would use this class if you are trying to preform inserts/updates/queries pertaining to
 *  a user.
 */
public class UserDAO {
    private static final String TAG = UserDAO.class.getSimpleName();

    private ProductionLineDAO productionLineProvider;

    public UserDAO() {
        this.productionLineProvider = new ProductionLineDAO();
    }

    public UserDTO createUser(UserDTO userDTO) throws Exception {
        ParseObject user = new ParseObject("User");
        user.put("Username", userDTO.getUsername());
        user.put("Password", userDTO.getPassword());
        user.put("FirstName", userDTO.getFirstName());
        user.put("LastName", userDTO.getLastName());
        user.put("ProductionLineID", userDTO.getParseProductionLineId());
        user.pinInBackground();
        user.saveEventually();

        return userDTO;
    }

    /**
     * Tries to log in user. If there is internet connectivity it tries the user against the cloud DB
     * if there is no internet connection it tries the user against the local parse DB.
     *
     *
     * @param userDTO requires username and SHA1 encrypted password.
     * @return returns a filled UserDTO object with user information.
     * @throws Exception
     */
    public UserDTO logInUser(UserDTO userDTO) throws Exception {
        Log.d(TAG, "Loggin in user");
        ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("User");
        Log.d(TAG, userDTO.getUsername() + " " + userDTO.getPassword());
        userQuery.whereEqualTo("Username", userDTO.getUsername());
        userQuery.whereEqualTo("Password", userDTO.getPassword());
        if(!PasserSingleton.getInstance().isConnected())
            userQuery.fromLocalDatastore();
        List<ParseObject> foundUser = userQuery.find();
        if(foundUser.size() != 0){
            for(ParseObject user : foundUser){
                /* necessary to get production line number */
                ProductionLineDTO productionLine =
                        productionLineProvider.getProductionLine(user.getString("ProductionLineID"));

                /* Now we have all the information so we return all the user information */
                return new UserDTO(user.getObjectId(), user.getString("Username"),
                        user.getString("Password"), user.getString("FirstName"),
                        user.getString("LastName"), user.getString("ProductionLineID"),
                        productionLine.getProductionLineId());
            }
        }
        return null;
    }
}
