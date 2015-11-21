package edu.chapman.manusync.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.ParseObject;

import javax.inject.Inject;

import edu.chapman.manusync.Hasher;
import edu.chapman.manusync.MANUComponent;
import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.R;
import edu.chapman.manusync.dao.UserDAO;
import edu.chapman.manusync.dto.UserDTO;
import edu.chapman.manusync.listener.EditProgressButtonListener;

public class LogInActivity extends AppCompatActivity {
    private static String TAG = LogInActivity.class.getSimpleName();

    private EditText username, password;
    private ActionProcessButton logIn;
    private UserDAO provider;
    private UserDTO user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        provider = new UserDAO();

        initViews();

        /* used to pre-fill EditTexts with a dummy user account for faster logging in */
        //setForTesting();
    }

    private void setForTesting() {
        username.setText("niccorder");
        password.setText("Password");
    }

    /* Initialzing views, and attaching appropriate listeners */
    private void initViews() {
        username = (EditText) findViewById(R.id.username_et);
        password = (EditText) findViewById(R.id.password_et);
        logIn = (ActionProcessButton) findViewById(R.id.login_btn);

        logIn.setMode(ActionProcessButton.Mode.ENDLESS);
        username.addTextChangedListener(new EditProgressButtonListener(logIn));
        password.addTextChangedListener(new EditProgressButtonListener(logIn));

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogInUserTask().execute();
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

    private class LogInUserTask extends AsyncTask<Void, Integer, UserDTO> {

        @Override
        protected void onPreExecute() {
            logIn.setProgress(1);
            try {
                user = new UserDTO(username.getText().toString(),
                        Hasher.SHA1(password.getText().toString()));
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(UserDTO userDTO) {
            if (user == null) {
                logIn.setProgress(-1);
            } else {
                logIn.setProgress(0);
                PasserSingleton.getInstance().setCurrentUser(user);
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        protected UserDTO doInBackground(Void... params) {
            try {
                user = provider.logInUser(user);
            } catch (Exception e) {
                user = null;
                e.printStackTrace();
            }
            return user;
        }
    }
}
