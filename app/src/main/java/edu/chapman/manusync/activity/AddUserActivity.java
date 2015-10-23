package edu.chapman.manusync.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.dd.processbutton.iml.ActionProcessButton;

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
 * <p/>
 * An activity which adds a new user to the database.
 */
public class AddUserActivity extends Activity {

    //username can be any combination of upper, lowercase, and number must be between 3-16 characters
    public static String USERNAME_REGEX = "^[A-Za-z0-9]{3,16}";

    //password must contain one uppercase, one lowercase, and must be between 6-20 characters in length
    public static String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(\\d|\\w){6,20})";

    @Inject
    /* package private */ UserDAO userHelper;

    private EditText username, password, firstName, lastName;
    private Spinner productionLine;
    private ActionProcessButton createUser;

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

    private class CreateUserOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            /* set log in button to 'loading' process' */
            AddUserActivity.this.createUser.setProgress(1);

            String username = AddUserActivity.this.username.getText().toString();
            String password = AddUserActivity.this.password.getText().toString();
            String firstName = AddUserActivity.this.firstName.getText().toString();
            String lastName = AddUserActivity.this.lastName.getText().toString();

            Random r = new Random();
            if (verifyUsername(username) && verifyPassword(password)) {
                UserDTO newUser = null;
                try {
                    //TODO: get the spinner to display available production lines.
                    newUser = new UserDTO(username,
                            Hasher.SHA1(password),
                            firstName,
                            lastName,
                            r.nextInt(10) + 1);
                    newUser = userHelper.createUser(newUser);
                } catch (Exception e) {
                    newUser = null;
                    e.printStackTrace();
                }

                if (newUser == null)
                    AddUserActivity.this.createUser.setProgress(-1);
                else
                    AddUserActivity.this.finish();
            } else
                AddUserActivity.this.createUser.setProgress(-1);
        }

        private boolean verifyUsername(String username) {
            if (!username.matches(AddUserActivity.USERNAME_REGEX)) {
                AddUserActivity.this.username.setTextColor(getResources().getColor(R.color.color_alizarin));
                return false;
            }
            return true;
        }

        private boolean verifyPassword(String password) {
            //
            if (!password.matches(AddUserActivity.PASSWORD_REGEX)) {
                AddUserActivity.this.password.setTextColor(getResources().getColor(R.color.color_alizarin));
                return false;
            }
            return true;
        }

    }
}
