package edu.chapman.manusync.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.R;
import edu.chapman.manusync.dto.LotDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/20/15.
 */
public class VerifyNewLotActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_new_lot);

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
        lotNumber.setText(currentLot.getLotNumberString());
        quantity.setText(currentLot.getQuantityString());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyNewLotActivity.this, TaktTimerActivity.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyNewLotActivity.this.finish();
            }
        });
    }
}
