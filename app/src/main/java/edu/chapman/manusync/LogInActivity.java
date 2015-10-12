package edu.chapman.manusync;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity {

    private Button logIn;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
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
                //move to new view.
            }
        });

    }
}
