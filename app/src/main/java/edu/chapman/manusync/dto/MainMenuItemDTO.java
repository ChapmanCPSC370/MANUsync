package edu.chapman.manusync.dto;

import android.util.Log;

import edu.chapman.manusync.R;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 *
 * DTO class used to transfer main menu item data between objects.
 */
public class MainMenuItemDTO {
    private static String TAG = MainMenuItemDTO.class.getSimpleName();
    private String name;
    private int resourceId;

    public MainMenuItemDTO(String name){
        this.name = name;
        this.resourceId = findResourceFromString(this.name);
    }

    private int findResourceFromString(String name){
        /* unfortunately needs to be updated with new items that are added */
        switch (name){
            case "Takt Tracking":
                return R.drawable.takt_tracking;
            default:
                Log.d(TAG, "We could not find the resource from the name");
                return -1;
        }
    }

    /* accessors and mutators */
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
    public void setResourceId(int resourceId) { this.resourceId = resourceId; }
    public int getResourceId() { return this.resourceId; }
}
