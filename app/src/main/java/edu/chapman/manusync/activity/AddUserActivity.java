package edu.chapman.manusync.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.inject.Inject;

import edu.chapman.manusync.Hasher;
import edu.chapman.manusync.MANUComponent;
import edu.chapman.manusync.R;
import edu.chapman.manusync.dao.UserDAO;
import edu.chapman.manusync.dto.UserDTO;
import edu.chapman.manusync.listener.EditProgressButtonListener;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 10/23/15.
 * <p>
 * An activity which adds a new user to the database.
 */
public class AddUserActivity extends Activity {

    @Inject
    /* package private */ UserDAO userHelper;

    private EditText username, password, firstName, lastName;
    private Spinner productionLine;
    private ActionProcessButton createUser;
    private CreateUserTask createUserTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        MANUComponent.Instance.get().inject(this);

        initViews();
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

    private class CreateUserTask extends AsyncTask<Void, Integer, ParseException> {

        private String username, password, firstName, lastName;

        @Override
        protected void onPreExecute() {
            AddUserActivity.this.createUser.setProgress(1);

            username = AddUserActivity.this.username.getText().toString();
            password = AddUserActivity.this.password.getText().toString();
            firstName = AddUserActivity.this.firstName.getText().toString();
            lastName = AddUserActivity.this.lastName.getText().toString();
        }

        @Override
        protected void onPostExecute(ParseException e) {
            if (!isCancelled()) {
                if (e == null)
                    AddUserActivity.this.finish();
                else {
                    AddUserActivity.this.createUser.setProgress(-1);

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddUserActivity.this);
                    builder.setTitle("Something went wrong!");
                    String errorMessage = "";
                    switch (e.getCode()) {
                        case ParseException.USERNAME_MISSING:
                            errorMessage = "You did not enter a username!";
                            break;
                        case ParseException.PASSWORD_MISSING:
                            errorMessage = "You did not enter a password!";
                            break;
                        case ParseException.USERNAME_TAKEN:
                            errorMessage = "That username is already taken.";
                            break;
                        default:
                            errorMessage = "Please make sure you have internet connectivity.";
                            break;
                    }
                    builder.setMessage(errorMessage)
                            .show();
                }
            }
        }

        @Override
        protected ParseException doInBackground(Void... params) {

            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(password);
            user.put("FirstName", firstName);
            user.put("LastName", lastName);
            Random r = new Random();
            user.put("ProductionLineId", r.nextInt(10) + 1);

            ParseException error = null;
            try {
                user.signUp();
            } catch (ParseException e) {
                e.printStackTrace();
                error = e;
            }

            UserDTO newUser = null;
            if (error == null) {
                try {
                    //TODO: get the spinner to display available production lines.
                    newUser = new UserDTO(username,
                            Hasher.SHA1(password),
                            firstName,
                            lastName,
                            r.nextInt(10) + 1);
                    userHelper.createUser(newUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return error;
        }
    }
}
