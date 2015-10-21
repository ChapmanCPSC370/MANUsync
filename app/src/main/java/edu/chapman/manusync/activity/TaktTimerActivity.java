package edu.chapman.manusync.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.filippudak.ProgressPieView.ProgressPieView;

import java.util.Timer;
import java.util.TimerTask;

import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.R;
import edu.chapman.manusync.dto.CompletedLotDTO;
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
    private Timer timer;
    private int numCompletedItems;
    private long totalTime, currentItemTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takt_timer);

        currentLot = PasserSingleton.getInstance().getCurrentLot();
        numCompletedItems = 1;
        initViews();
    }

    private void initViews() {
        partNumber = (TextView) findViewById(R.id.takt_part_number);
        lotNumber = (TextView) findViewById(R.id.takt_lot_number);
        quantity = (TextView) findViewById(R.id.takt_lot_quantity);
        taktTimer = (ProgressPieView) findViewById(R.id.takt_progress_timer);

        partNumber.setText(currentLot.getPartNumberString());
        lotNumber.setText(currentLot.getLotNumberString());
        quantity.setText(numCompletedItems + " of " +  currentLot.getQuantityString());
        taktTimer.setOnClickListener(new CompleteItemListener());

        startTimer();
    }

    private void startTimer() {
        final Handler handler = new Handler();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                //TODO: set progress depending on maximum time allowed.
                TaktTimerActivity.this.currentItemTime += 100;
                handler.post(new Runnable() {
                    public void run() {
                        taktTimer.setText(Double.toString(TaktTimerActivity.this.currentItemTime / 1000.0));
                    }
                });
            }
        }, 1, 100);
    }

    /* Added as an inner class because it will need to touch TaktTimer variables. */
    public class CompleteItemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            totalTime += currentItemTime;

            /* we have finished our current lot, now we will stop the timer and log the data */
            if( numCompletedItems == currentLot.getQuantity()) {
                timer.cancel();
                timer.purge();
                //TODO: log this data in a database.
                CompletedLotDTO completedLot = new CompletedLotDTO(currentLot.getProductionLineNumber(),
                        currentLot.getWorkstationNumber(), currentLot.getPartNumber(),
                        currentLot.getLotNumber(), currentLot.getQuantity(), totalTime);
                taktTimer.setText("Total Time: " + completedLot.getTotalTime() + "s"
                        + "\nAverage Time: " + completedLot.getAverageTime() + "s");
            } else {
                quantity.setText( (++numCompletedItems) + " of " + currentLot.getQuantity() );
            }
        }
    }
}
