package edu.chapman.manusync.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.filippudak.ProgressPieView.ProgressPieView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.R;
import edu.chapman.manusync.dialog.IssueDialog;
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

    /* Views */
    private ProgressPieView taktTimer;
    private TextView quantity;
    private View pause, menu;

    private LotDTO currentLot;
    private Timer timer;
    private int numCompletedItems;
    private long totalTime, taktTime, currentItemTime = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takt_timer);

        currentLot = PasserSingleton.getInstance().getCurrentLot();
        numCompletedItems = 1;

        taktTime = (long)(currentLot.getPart().getTaktTime() * 1000.0);

        initViews();
    }

    private void initViews() {
        TextView partNumber = (TextView) findViewById(R.id.takt_part_number);
        TextView lotNumber = (TextView) findViewById(R.id.takt_lot_number);
        quantity = (TextView) findViewById(R.id.takt_lot_quantity);
        taktTimer = (ProgressPieView) findViewById(R.id.takt_progress_timer);
        taktTimer.setStartAngle(0);
        taktTimer.setProgress(0);
        taktTimer.setProgressColor(ContextCompat.getColor(this, R.color.color_belize_hole));

        pause = findViewById(R.id.takt_pause_btn);
        menu = findViewById(R.id.takt_menu_btn);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        partNumber.setText(currentLot.getPartNumberString());
        lotNumber.setText(currentLot.getLotNumber());

        quantity.setText(numCompletedItems + " of " + currentLot.getQuantityString());

        startTimer();
    }

    private void startTimer() {
        taktTimer.setTextSize(60);
        taktTimer.setOnClickListener(new CompleteItemListener());
        final Handler handler = new Handler();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                TaktTimerActivity.this.currentItemTime += 100;
                handler.post(new Runnable() {
                    public void run() {
                        /* finding percent needed to show progress */
                        int progress = (int)((currentItemTime * 1.0)/taktTime * 100);
                        if(progress > 100)
                            progress = 100;

                        taktTimer.setProgress(progress);

                        /* Displaying appropriate color */
                        if(progress < 80)
                            taktTimer.setProgressColor(
                                    ContextCompat.getColor(TaktTimerActivity.this, R.color.color_belize_hole));
                        else if( progress < 90)
                            taktTimer.setProgressColor(
                                    ContextCompat.getColor(TaktTimerActivity.this, R.color.color_pumpkin));
                        else
                            taktTimer.setProgressColor(
                                    ContextCompat.getColor(TaktTimerActivity.this, R.color.color_alizarin));

                        /* Displaying current time */
                        taktTimer.setText(String.format("%d m %d s",
                                TimeUnit.MILLISECONDS.toMinutes(currentItemTime),
                                TimeUnit.MILLISECONDS.toSeconds(currentItemTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentItemTime))));
                    }
                });
            }
        }, 1, 100);
    }

    private void pauseTimer() {
        timer.cancel();
        taktTimer.setBackgroundColor(ContextCompat.getColor(this, R.color.color_concrete));
        taktTimer.setTextSize(20);
        taktTimer.setText(getResources().getString(R.string.takt_pause_message));
        taktTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taktTimer.setBackgroundColor(ContextCompat.getColor(TaktTimerActivity.this, R.color.color_peter_river));
                startTimer();
            }
        });
    }

    /* Added as an inner class because it will need to touch TaktTimer variables. */
    public class CompleteItemListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            totalTime += currentItemTime;

            /* check if time is greater than takt-time + 10% */
            if(((currentItemTime * 1.0)/taktTime) >= 1.10){
                new IssueDialog(TaktTimerActivity.this).show();
            }

            /* we have finished our current lot, now we will stop the timer and log the data */
            if( numCompletedItems == currentLot.getQuantity()) {
                timer.cancel();
                timer.purge();
                //TODO: log this data in a database.
//                CompletedLotDTO completedLot = new CompletedLotDTO(currentLot.getProductionLineNumber(),
//                        currentLot.getWorkstationNumber(), currentLot.getPartNumber(),
//                        currentLot.getLotNumber(), currentLot.getQuantity(), totalTime);
//                taktTimer.setText("Total Time: " + completedLot.getTotalTime() + "s"
//                        + "\nAverage Time: " + completedLot.getAverageTime() + "s");
            } else {
                quantity.setText( (++numCompletedItems) + " of " + currentLot.getQuantity() );
            }
        }
    }
}
