package edu.chapman.manusync.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import javax.inject.Inject;

import edu.chapman.manusync.MANUComponent;
import edu.chapman.manusync.R;
import edu.chapman.manusync.dto.LotDTO;
import edu.chapman.manusync.provider.NewLotDataProvider;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 */
public class NewLotActivity extends Activity {

    private ArrayAdapter<Integer> productionLineAdapter, workstationNumberAdapter, partNumberAdapter;
    private Spinner productionLineNumbers, workstationNumbers, partNumbers;
    private EditText lotNumber, quantity;
    private Button startLot;

    @Inject
    /* package private */ NewLotDataProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lot);

        MANUComponent.Instance.get().inject(this);
        productionLineAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                provider.getProductionLineNumbers());
        workstationNumberAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                provider.getWorkstationNumbers());
        partNumberAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                provider.getPartNumbers());

        initViews();
    }

    /* initializes views, and attaches adapters */
    private void initViews() {
        //spinners
        productionLineNumbers = (Spinner) findViewById(R.id.production_line_spnr);
        productionLineNumbers.setAdapter(productionLineAdapter);
        workstationNumbers = (Spinner) findViewById(R.id.workstation_number_spnr);
        workstationNumbers.setAdapter(workstationNumberAdapter);
        partNumbers = (Spinner) findViewById(R.id.part_number_spnr);
        partNumbers.setAdapter(partNumberAdapter);

        //EditTexts - setting keyboard exclusively to numbers
        lotNumber = (EditText) findViewById(R.id.lot_number_et);
        lotNumber.setRawInputType(Configuration.KEYBOARD_12KEY);
        quantity = (EditText) findViewById(R.id.quantity_et);
        quantity.setRawInputType(Configuration.KEYBOARD_12KEY);

        startLot = (Button) findViewById(R.id.start_lot_btn);
        startLot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyData(v)) {
                    //go to new activity.
                    LotDTO lot = new LotDTO(Integer.parseInt(productionLineNumbers.getSelectedItem().toString()),
                            Integer.parseInt(workstationNumbers.getSelectedItem().toString()),
                            Integer.parseInt(partNumbers.getSelectedItem().toString()),
                            Integer.parseInt(lotNumber.getText().toString()),
                            Integer.parseInt(quantity.getText().toString()));
                }
            }

            /* verifies integrity of user inputted data */
            public boolean verifyData(View v){
                String lotNumber = NewLotActivity.this.lotNumber.getText().toString(),
                        quantity = NewLotActivity.this.quantity.getText().toString();

                /* creates dialog in case of error */
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                        .setTitle(R.string.bad_lot_error)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                /* checking for bad input, changing dialog message accordingly. */
                if(lotNumber.equals("") &&
                        !quantity.equals("") && !quantity.equals("0")) {
                    builder.setMessage("You must enter a valid lot number.")
                            .show();
                    return false;
                } else if (!lotNumber.equals("") &&
                        quantity.equals("") || quantity.equals("0")) {
                    builder.setMessage("You must enter a valid quantity.")
                            .show();
                    return false;
                } else if(lotNumber.equals("") &&
                        (quantity.equals("") || quantity.equals("0"))) {
                    builder.setMessage("You must enter a valid lot number, and quantity.")
                            .show();
                    return false;
                }
                return true;
            }
        });
    }
}
