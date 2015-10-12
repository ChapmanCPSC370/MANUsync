package edu.chapman.manusync.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import edu.chapman.manusync.NewLotActivity;
import edu.chapman.manusync.R;
import edu.chapman.manusync.dto.MainMenuItemDTO;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 *
 * On click listener for main menu item. Will redirect to appropriate activity
 * depending on if the user has a takt-timer in progress or not.
 */
public class MainMenuItemOnClickListener implements View.OnClickListener {
    private static String TAG = MainMenuItemOnClickListener.class.getSimpleName();

    private MainMenuItemDTO item;

    public MainMenuItemOnClickListener(MainMenuItemDTO item) {
        this.item = item;
    }

    @Override
    public void onClick(View v) {
        handleActivity(v.getContext(), item.getResourceId());
    }

    //TODO: switch to activity dependant on weither a current takt-timer is active or not.
    /* handles selected activity, also please note the FLAG_ACTIVITY_NEW_TASK is
     * required because the injection sets the context of the grid view to application context.
     */
    private void handleActivity(Context context, int itemResource){
        switch (itemResource){
            case R.drawable.takt_tracking:
                Intent intent = new Intent(context, NewLotActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            default:
                Log.d(TAG, "We could not handle item resource");
                break;
        }
    }
}
