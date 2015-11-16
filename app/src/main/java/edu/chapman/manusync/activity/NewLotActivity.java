package edu.chapman.manusync.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.R;
import edu.chapman.manusync.adapter.PartListAdapter;
import edu.chapman.manusync.adapter.ProductionLineAdapter;
import edu.chapman.manusync.dao.PartDAO;
import edu.chapman.manusync.dao.ProductionLineDAO;
import edu.chapman.manusync.dto.LotDTO;
import edu.chapman.manusync.dto.PartDTO;
import edu.chapman.manusync.dto.ProductionLineDTO;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 */
public class NewLotActivity extends Activity {
    private static final String TAG = NewLotActivity.class.getSimpleName();

    /* Necessary for views */
    private Spinner productionLineNumbers, workstationNumbers, partNumberSpinner;
    private EditText lotNumber, quantity;
    private Button startLot;
    private ProductionLineDAO productionLineProvider;
    private PartDAO partProvider;
    private ProductionLineAdapter productionLineAdapter;
    private PartListAdapter partListAdapter;
    private ArrayAdapter<String> workstationNumberAdapter;
    private List<String> workstationNumberList;
    private List<ProductionLineDTO> productionLineIds;
    private List<PartDTO> partNumbers;

    /* Necessary for submitting data */
    private ProductionLineDTO selectedProductionLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lot);

        productionLineProvider = new ProductionLineDAO();
        partProvider = new PartDAO();

        new GetLotInfoTask().execute();
    }

    /* initializes views, and attaches adapters */
    private void initViews() {
        //spinners
        productionLineNumbers = (Spinner) findViewById(R.id.production_line_spnr);
        productionLineNumbers.setAdapter(productionLineAdapter);
        workstationNumbers = (Spinner) findViewById(R.id.workstation_number_spnr);
        workstationNumbers.setAdapter(workstationNumberAdapter);
        partNumberSpinner = (Spinner) findViewById(R.id.part_number_spnr);
        partNumberSpinner.setAdapter(partListAdapter);

        /* updates visible workstation numbers depending on how many workstations are available to
         * the production line.
         */
        productionLineNumbers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProductionLine = productionLineAdapter.getItem(position);
                workstationNumberList = new ArrayList<>();
                for( int i = 1; i <= selectedProductionLine.getNumWorkstations(); ++i) {
                    workstationNumberList.add(Integer.toString(i));
                }
                workstationNumberAdapter = new ArrayAdapter<>(NewLotActivity.this,
                        R.layout.item_spinner, workstationNumberList);
                workstationNumbers.setAdapter(workstationNumberAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedProductionLine = null;
            }
        });

        /* EditTexts - setting keyboard exclusively to numbers */
        lotNumber = (EditText) findViewById(R.id.lot_number_et);
        lotNumber.setRawInputType(Configuration.KEYBOARD_12KEY);
        quantity = (EditText) findViewById(R.id.quantity_et);
        quantity.setRawInputType(Configuration.KEYBOARD_12KEY);

        startLot = (Button) findViewById(R.id.start_lot_btn);
        startLot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyData(v)) {
                    LotDTO lot = new LotDTO((ProductionLineDTO)productionLineNumbers.getSelectedItem(),
                            Integer.parseInt(workstationNumbers.getSelectedItem().toString()),
                            (PartDTO) partNumberSpinner.getSelectedItem(),
                            lotNumber.getText().toString(),
                            Integer.parseInt(quantity.getText().toString()));

                    PasserSingleton passer = PasserSingleton.getInstance();
                    passer.setCurrentLot(lot);
                    Intent intent = new Intent(NewLotActivity.this, VerifyNewLotActivity.class);
                    startActivity(intent);
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

    private class GetLotInfoTask extends AsyncTask<Void, Integer, Boolean> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(NewLotActivity.this, "Loading...",
                    "Please wait while we load some data.", true);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            productionLineAdapter = new ProductionLineAdapter(NewLotActivity.this,
                    R.layout.item_spinner, productionLineIds);
            workstationNumberAdapter = new ArrayAdapter<>(NewLotActivity.this,
                    R.layout.item_spinner, workstationNumberList);
            partListAdapter = new PartListAdapter(NewLotActivity.this,
                    R.layout.item_spinner, partNumbers);
            initViews();
            progress.dismiss();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            productionLineIds = new ArrayList<>();
            workstationNumberList = new ArrayList<>();
            partNumbers = new ArrayList<>();

            try {
                productionLineIds = productionLineProvider.getAllProductionLines();
                if(productionLineIds.size() != 0) {
                    for (int i = 1; i <= productionLineIds.get(0).getNumWorkstations(); ++i) {
                        workstationNumberList.add(Integer.toString(i));
                    }
                }
                partNumbers = partProvider.getAllParts();
            } catch (ParseException e) {
                Log.d(TAG, "error code: " + e.getCode());
            }

            return null;
        }
    }
}
