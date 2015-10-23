package edu.chapman.manusync.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class LogInActivity extends AppCompatActivity {
    private static String TAG = LogInActivity.class.getSimpleName();

    private Button logIn;
    private EditText username, password;

    @Inject
    /* package private */ UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initViews();

        MANUComponent.Instance.get().inject(this);

        /*try {
            userDAO.createUser(new UserDTO("niccorder",
                    Hasher.SHA1("apple"),
                    "Nicholas",
                    "Corder",
                    10));
        } catch( Exception e ){
            e.printStackTrace();
        }*/
    }

    /* Initialzing views, and attaching appropriate listeners */
    private void initViews() {
        username = (EditText) findViewById(R.id.username_et);
        password = (EditText) findViewById(R.id.password_et);
        logIn = (Button) findViewById(R.id.login_btn);
        //TODO: create custom LogInOnClickListener to check against db for credentials.
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UserDTO user = new UserDTO(username.getText().toString(),
                            Hasher.SHA1(password.getText().toString()));
                    user = userDAO.logInUser(user);
                    Log.i(TAG, "Logged in user " + user.getUsername());
                } catch (Exception e ) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(LogInActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

    }

}
