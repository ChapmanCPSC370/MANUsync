package edu.chapman.manusync.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseException;

import java.util.Arrays;

import edu.chapman.manusync.PasserSingleton;
import edu.chapman.manusync.R;
import edu.chapman.manusync.activity.LogInActivity;
import edu.chapman.manusync.activity.TaktTimerActivity;
import edu.chapman.manusync.adapter.TaktMenuListAdapter;
import edu.chapman.manusync.dao.LotDAO;
import edu.chapman.manusync.dto.CompletedLotDTO;
import edu.chapman.manusync.dto.LotDTO;

/**
 * Created by niccorder - corde116@mail.chapman.edu on 11/21/15.
 */
public class MenuDialog extends Dialog {
    private static final String TAG = MenuDialog.class.getSimpleName();

    private String[] items = new String[] {
            "Log Out"
    };
    private TaktMenuListAdapter adapter;
    private CompletedLotDTO lot;
    private LotDAO lotProvider;
    private Context context;

    public MenuDialog(final Context context, CompletedLotDTO lot) {
        super(context);
        this.context = context;
        this.lot = lot;
        lotProvider = new LotDAO(PasserSingleton.getInstance().getCurrentUser());

        setContentView(R.layout.dialog_issue);

        adapter = new TaktMenuListAdapter(context, R.layout.item_spinner, Arrays.asList(items));

        ListView listView = (ListView) findViewById(R.id.issue_lv);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                Log.d(TAG, item);
                if(item.equals(items[0])){
                    try {
                        lotProvider.finishLot(MenuDialog.this.lot);

                        PasserSingleton.getInstance().setCurrentUser(null);
                        Intent intent = new Intent(MenuDialog.this.context, LogInActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        MenuDialog.this.dismiss();
                        MenuDialog.this.context.startActivity(intent);
                        ((TaktTimerActivity) MenuDialog.this.context).finish();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
