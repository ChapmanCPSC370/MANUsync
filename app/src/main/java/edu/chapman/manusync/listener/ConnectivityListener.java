package edu.chapman.manusync.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import edu.chapman.manusync.BaseApplication;
import edu.chapman.manusync.PasserSingleton;

/**
 * @author Nicholas Corder - corde116@mail.chapman.edu - 11/15/15
 *
 * Preforms a background task that keeps track of network connectivity.
 */
public class ConnectivityListener {

    private Context context;
    BroadcastReceiver networkStateReceiver;

    public ConnectivityListener(Context context){
        this.context = context;
    }

    /* used to check if you are connected, or not connected to wifi/service */
    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    /*referenced from stackoverflow question 3307237
     *used to listen for changes in connectivity state.
     */
    public void start(){
        networkStateReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if(isConnected()){
                    PasserSingleton.getInstance().setConnected(true);
                    ((BaseApplication) context).downloadCloudInformation();
                } else {
                    PasserSingleton.getInstance().setConnected(false);
                }
            }
        };

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkStateReceiver, filter);
    }

    public void stop() {
        context.unregisterReceiver(networkStateReceiver);
    }
}