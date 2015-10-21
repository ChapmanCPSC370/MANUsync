package edu.chapman.manusync.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.filippudak.ProgressPieView.ProgressPieView;

import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.R;
import edu.chapman.manusync.dto.LotDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/20/15.
 *
 * The main activity the user will be interacting with, contains the current lot, and controls the
 *  timer for the current lot.
 */
public class TaktTimerActivity extends Activity {
    private static String TAG = TaktTimerActivity.class.getSimpleName();

    private LotDTO currentLot;
    private ProgressPieView taktTimer;
    private TextView partNumber, lotNumber, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takt_timer);

        currentLot = PasserSingleton.getInstance().getCurrentLot();
        initViews();
    }

    private void initViews() {
        partNumber = (TextView) findViewById(R.id.takt_part_number);
        lotNumber = (TextView) findViewById(R.id.takt_lot_number);
        quantity = (TextView) findViewById(R.id.takt_lot_quantity);
        taktTimer = (ProgressPieView) findViewById(R.id.takt_progress_timer);

        partNumber.setText(currentLot.getPartNumberString());
        lotNumber.setText(currentLot.getLotNumberString());
        quantity.setText(currentLot.getQuantityString());

        /* silly showcase example */
        final Handler mHandler = new Handler();
        new Thread(new Runnable() {

            int mProgressStatus = 0;
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus++;
                    try {
                        Thread.sleep(500);
                    } catch( Exception e){

                    }
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            taktTimer.setProgress(mProgressStatus);
                            taktTimer.setText(mProgressStatus + "%");
                        }
                    });
                }
            }
        }).start();

    }

}
