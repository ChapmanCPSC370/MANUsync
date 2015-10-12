package edu.chapman.manusync;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import javax.inject.Inject;

import edu.chapman.manusync.adapter.MainMenuViewAdapter;

/**
 * Created by Nicholas Corder - corde116@mail.chapman.edu on 10/11/2015.
 */
public class MainMenuActivity extends Activity {

    private GridView applications;

    @Inject
    /* package private */ MainMenuViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //used for field injection
        MANUComponent.Instance.get().inject(this);
        initViews();
    }

    /* Initializes, and fills the grid view with current application options */
    private void initViews() {
        applications = (GridView) findViewById(R.id.main_menu_lv);
        applications.setAdapter(adapter);
    }
}
