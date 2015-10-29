package edu.chapman.manusync;

import edu.chapman.manusync.dto.LotDTO;
import edu.chapman.manusync.dto.UserDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/20/15.
 *
 * A Singleton class used to pass data between activities.
 */
public class PasserSingleton {

    private static PasserSingleton SINGLE_INSTANCE = null;

    private PasserSingleton() {}

    public static PasserSingleton getInstance() {
        if( SINGLE_INSTANCE == null ) SINGLE_INSTANCE = new PasserSingleton();
        return SINGLE_INSTANCE;
    }

    /* Accessors and mutators */

    private LotDTO currentLot = null;

    public void setCurrentLot(LotDTO currentLot) { this.currentLot = currentLot; }
    public LotDTO getCurrentLot() { return this.currentLot; }

    private UserDTO currentUser = null;

    public void setCurrentUser(UserDTO currentUser) { this.currentUser = currentUser; }
    public UserDTO getCurrentUser() { return this.currentUser; }

}
