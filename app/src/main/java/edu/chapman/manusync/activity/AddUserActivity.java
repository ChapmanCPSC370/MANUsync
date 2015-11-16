package edu.chapman.manusync.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import edu.chapman.manusync.Hasher;
import edu.chapman.manusync.R;
import edu.chapman.manusync.adapter.ProductionLineAdapter;
import edu.chapman.manusync.dao.ProductionLineDAO;
import edu.chapman.manusync.dao.UserDAO;
import edu.chapman.manusync.dto.ProductionLineDTO;
import edu.chapman.manusync.dto.UserDTO;
import edu.chapman.manusync.listener.EditProgressButtonListener;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/23/15.
 * <p>
 * An activity which adds a new user to the database.
 */
public class AddUserActivity extends Activity {
    private static final String TAG = AddUserActivity.class.getSimpleName();

    private UserDAO userProvider;

    private EditText username, password, firstName, lastName;
    private Spinner productionLine;
    private ActionProcessButton createUser;
    private CreateUserTask createUserTask;

    private ProductionLineDAO productionLineProvider;
    private ProductionLineAdapter adapter;
    private ProductionLineDTO selectedProductionLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        productionLineProvider = new ProductionLineDAO();
        userProvider = new UserDAO();

        new InitializeActivityTask().execute();
    }

    private void initViews() {
        username = (EditText) findViewById(R.id.add_user_username_et);
        password = (EditText) findViewById(R.id.add_user_password_et);
        firstName = (EditText) findViewById(R.id.add_user_firstname_et);
        lastName = (EditText) findViewById(R.id.add_user_lastname_et);
        productionLine = (Spinner) findViewById(R.id.add_user_production_line_spnr);
        createUser = (ActionProcessButton) findViewById(R.id.add_user_btn);

        createUser.setMode(ActionProcessButton.Mode.ENDLESS);
        createUser.setOnClickListener(new CreateUserOnClickListener());
        username.addTextChangedListener(new EditProgressButtonListener(createUser, username, this));
        password.addTextChangedListener(new EditProgressButtonListener(createUser, password, this));
        productionLine.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        createUserTask.cancel(true);
        super.onDestroy();
    }

    private class CreateUserOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            createUserTask = new CreateUserTask();
            createUserTask.execute();
        }
    }

    private class CreateUserTask extends AsyncTask<Void, Integer, Boolean> {

        private String username, password, firstName, lastName;

        @Override
        protected void onPreExecute() {
            AddUserActivity.this.createUser.setProgress(1);

            username = AddUserActivity.this.username.getText().toString();
            password = AddUserActivity.this.password.getText().toString();
            firstName = AddUserActivity.this.firstName.getText().toString();
            lastName = AddUserActivity.this.lastName.getText().toString();
            selectedProductionLine = (ProductionLineDTO) productionLine.getSelectedItem();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            createUser.setProgress(0);
            finish();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                /* saving to cloud */
                userProvider.createUser(new UserDTO(username, Hasher.SHA1(password), firstName, lastName,
                        selectedProductionLine.getParseId(), selectedProductionLine.getProductionLineId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    private class InitializeActivityTask extends AsyncTask<Void, Integer, List<ProductionLineDTO>> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(AddUserActivity.this, "One Second...",
                    "Please wait while we load some data.", true);
        }

        @Override
        protected void onPostExecute(List<ProductionLineDTO> productionLineDTOs) {
            adapter = new ProductionLineAdapter(AddUserActivity.this,
                    R.layout.item_spinner, productionLineDTOs);
            initViews();
            progress.dismiss();
        }

        @Override
        protected List<ProductionLineDTO> doInBackground(Void... params) {
            List<ProductionLineDTO> allLines = new ArrayList<>();
            try {
                allLines = productionLineProvider.getAllProductionLines();
            } catch (ParseException e) {
                Log.d(TAG, "Error Code: " + e.getCode());
            }
            return allLines;
        }
    }
}
