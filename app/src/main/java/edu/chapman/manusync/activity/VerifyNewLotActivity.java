package edu.chapman.manusync.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.R;
import edu.chapman.manusync.dao.LotDAO;
import edu.chapman.manusync.dto.LotDTO;
import edu.chapman.manusync.dto.PartDTO;
import edu.chapman.manusync.dto.ProductionLineDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/20/15.
 */
public class VerifyNewLotActivity extends Activity {

    private LotDAO lotProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_new_lot);

        lotProvider = new LotDAO(PasserSingleton.getInstance().getCurrentUser());
        initViews();
    }

    private void initViews() {
        TextView productionLine = (TextView) findViewById(R.id.verify_new_lot_production_line);
        TextView workstationNumber = (TextView) findViewById(R.id.verify_new_lot_workstation);
        TextView partNumber = (TextView) findViewById(R.id.verify_new_lot_part_number);
        TextView lotNumber = (TextView) findViewById(R.id.verify_new_lot_lot_number);
        TextView quantity = (TextView) findViewById(R.id.verify_new_lot_quantity);
        Button submit = (Button) findViewById(R.id.verify_new_lot);
        Button cancel = (Button) findViewById(R.id.cancel_new_lot);

        LotDTO currentLot = PasserSingleton.getInstance().getCurrentLot();

        productionLine.setText(currentLot.getProductionLineNumberString());
        workstationNumber.setText(currentLot.getWorkstationNumberString());
        partNumber.setText(currentLot.getPartNumberString());
        lotNumber.setText(currentLot.getLotNumber());
        quantity.setText(currentLot.getQuantityString());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddNewLotTask().execute();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyNewLotActivity.this.finish();
            }
        });
    }

    private class AddNewLotTask extends AsyncTask<Void, Integer, Boolean> {

        private LotDTO lot;
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(VerifyNewLotActivity.this, "One Second...",
                    "Please wait while we save the lot data.", true);
            lot = PasserSingleton.getInstance().getCurrentLot();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            PasserSingleton passer = PasserSingleton.getInstance();
            passer.setCurrentLot(lot);
            Intent intent = new Intent(VerifyNewLotActivity.this, TaktTimerActivity.class);
            startActivity(intent);
            VerifyNewLotActivity.this.finish();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                lot = lotProvider.addNewLot(lot);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
