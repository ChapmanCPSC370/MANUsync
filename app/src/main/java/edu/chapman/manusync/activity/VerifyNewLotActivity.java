package edu.chapman.manusync.activity;

import android.app.Activity;
import android.os.Bundle;
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

        LotDTO currentLot = PasserSingleton.getInstance().getCurrentLot();

        productionLine.setText(currentLot.getProductionLineNumberString());
        workstationNumber.setText(currentLot.getWorkstationNumberString());
        partNumber.setText(currentLot.getPartNumberString());
        lotNumber.setText(currentLot.getLotNumberString());
        quantity.setText(currentLot.getQuantityString());
    }
}
