package edu.chapman.manusync.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import edu.chapman.manusync.Hasher;
import edu.chapman.manusync.MANUComponent;
import edu.chapman.manusync.R;
import edu.chapman.manusync.dao.UserDAO;
import edu.chapman.manusync.db.DatabaseHelper;
import edu.chapman.manusync.dto.UserDTO;
import edu.chapman.manusync.listener.EditProgressButtonListener;

public class LogInActivity extends AppCompatActivity {
    private static String TAG = LogInActivity.class.getSimpleName();

    private EditText username, password;
    private ActionProcessButton logIn;

    @Inject
    /* package private */ UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initViews();

        MANUComponent.Instance.get().inject(this);

    }

    /* Initialzing views, and attaching appropriate listeners */
    private void initViews() {
        username = (EditText) findViewById(R.id.username_et);
        password = (EditText) findViewById(R.id.password_et);
        logIn = (ActionProcessButton) findViewById(R.id.login_btn);

        logIn.setMode(ActionProcessButton.Mode.ENDLESS);
        username.addTextChangedListener(new EditProgressButtonListener(logIn));
        password.addTextChangedListener(new EditProgressButtonListener(logIn));

        //TODO: create custom LogInOnClickListener to check against db for credentials.
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn.setProgress(1);
                UserDTO user = null;
                try {
                    user = new UserDTO(username.getText().toString(),
                            Hasher.SHA1(password.getText().toString()));
                    user = userDAO.logInUser(user);
                } catch (Exception e) {
                    user = null;
                }
                if (user == null) {
                    logIn.setProgress(-1);
                } else {
                    Intent intent = new Intent(LogInActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_user_action:
                Intent intent = new Intent(this, AddUserActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
