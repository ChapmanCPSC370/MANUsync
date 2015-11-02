package edu.chapman.manusync.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

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

    /*
     * In the line below, we tell dagger two that this object is a dependency that we would like it to
     * inject for us. This means that we don't have to actually build our object ourself. Not only does this
     * save us time when we are coding, but this also saves us time if we were to change any parameters needed
     * to build these objects. For example, lets build out UserDAO object right here without dependency injection.
     *
     *  UserDAO userDAO = new UserDAO(this, new DatabaseHelper(this));
     *
     * Long story short, dagger does that for us. Makes our lives easier ( especially if you have an object
     * that recursively uses more dependencies... for example new BufferedReader(new FileReader(new InputStreamReader(...)));
     * As a coder you want to avoid this, because if we change the constructor to add/delete dependencies we need to manually
     * make these fixes. It gets even worse if you change DatabaseHelper's constructor, or a constructor DatabaseHelper depends
     * on! As you can see it can really get messy, really fast. Dagger two abstracts this away from us, and makes our lives
     * much easier by doing this for us. Even if we make a change to the constructor of UserDAO we dont need to change dagger two
     * ...( unless we add a dependency, then we need to add one more providers method!)
     */
    @Inject
    /* package private */ UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initViews();

        /*
         * This method tells dagger two to inject any dependencies in this activity, as explained in the
         * MANUComponent interface. This basically runs Dagger-2 for this activity.
         */
        MANUComponent.Instance.get().inject(this);

        //TODO: Please use this on the first run to fill the database with dummy data.
        //tmpDBCreator tmp = new tmpDBCreator(userDAO);
        //tmp.initPhoneyTables();

        /* used to pre-fill EditTexts with a dummy user account for faster logging in */
        setForTesting();
    }

    private void setForTesting() {
        username.setText("niccorder0");
        password.setText("Password1");
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
                UserDTO user;
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
                    PasserSingleton.getInstance().setCurrentUser(user);
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
